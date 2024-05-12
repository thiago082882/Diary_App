package br.thiago.diaryapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import br.thiago.diaryapp.data.repository.MongoDB
import br.thiago.diaryapp.navigation.Screen
import br.thiago.diaryapp.navigation.SetupNavGraph
import br.thiago.diaryapp.ui.theme.DiaryAppTheme
import br.thiago.diaryapp.util.Constants.APP_ID
import dagger.hilt.android.AndroidEntryPoint
import io.realm.kotlin.mongodb.App


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var keepSplashOpened = true

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            keepSplashOpened
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        MongoDB.configureTheRealm()
        setContent {
            DiaryAppTheme(dynamicColor = false) {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = getStartDestination(),
                    navController = navController,
                    onDataLoaded = {
                        keepSplashOpened = false
                    }
                )

            }
        }
    }
}


private fun getStartDestination(): String {
    val user = App.create(APP_ID).currentUser
    return if (user != null && user.loggedIn) Screen.Home.route
    else Screen.Authentication.route
}