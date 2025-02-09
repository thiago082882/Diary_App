package br.thiago.mongo.database
import androidx.room.Database
import androidx.room.RoomDatabase
import br.thiago.mongo.database.ImageToDeleteDao
import br.thiago.mongo.database.entity.ImageToDelete
import br.thiago.mongo.database.entity.ImageToUpload


@Database(
    entities = [ImageToUpload::class, ImageToDelete::class],
    version = 1,
    exportSchema = false
)
abstract class ImagesDatabase: RoomDatabase() {
    abstract fun imageToUploadDao(): ImageToUploadDao
    abstract fun imageToDeleteDao(): ImageToDeleteDao
}