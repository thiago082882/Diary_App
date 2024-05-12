package br.thiago.diaryapp.data.repository

import android.os.Build
import android.security.keystore.UserNotAuthenticatedException
import androidx.annotation.RequiresApi
import br.thiago.diaryapp.model.Diary
import br.thiago.diaryapp.model.RequestState
import br.thiago.diaryapp.util.Constants.APP_ID
import br.thiago.diaryapp.util.toInstant
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import java.time.ZoneId
import java.time.ZonedDateTime

object MongoDB : MongoRepository {

    private lateinit var realm: Realm
    private val app = App.Companion.create(APP_ID)
    private val user = app.currentUser

    init {
        configureTheRealm()
    }
    override fun configureTheRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(user, setOf(Diary::class))
                .initialSubscriptions { sub ->
                    add(
                        query = sub.query<Diary>(query="ownerId == $0", user.identity),
                        name = "User's Diaries"
                    )

                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getAllDiaries(): Flow<Diaries> {
        return if (user != null) {
            try {
                realm.query<Diary>(query = "ownerId == $0", user.id)
                    .sort(property = "date", sortOrder = Sort.DESCENDING)
                    .asFlow()
                    .map { result ->
                        RequestState.Success(
                            data = result.list.groupBy {
                                it.date.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                            }
                        )
                    }
            } catch (e: Exception) {
                flow { emit(RequestState.Error(e)) }
            }
        } else {
            flow { emit(RequestState.Error(UserNotAuthenticatedException())) }
        }
    }

    override fun getFilteredDiaries(zonedDateTime: ZonedDateTime): Flow<Diaries> {
        TODO("Not yet implemented")
    }

    override fun getSelectedDiary(diaryId: ObjectId): Flow<RequestState<Diary>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertDiary(diary: Diary): RequestState<Diary> {
        TODO("Not yet implemented")
    }

    override suspend fun updateDiary(diary: Diary): RequestState<Diary> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDiary(id: ObjectId): RequestState<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllDiaries(): RequestState<Boolean> {
        TODO("Not yet implemented")
    }
    private class UserNotAuthenticatedException : Exception("User is not Logged in.")
}