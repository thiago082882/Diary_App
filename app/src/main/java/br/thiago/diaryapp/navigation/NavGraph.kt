package br.thiago.diaryapp.navigation


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.thiago.diaryapp.presentation.screens.auth.AuthenticationScreen
import br.thiago.diaryapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.MessageBarState
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute()
        homeRoute()
        writeRoute()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.authenticationRoute() {
    composable(route = Screen.Authentication.route) {

        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        AuthenticationScreen(
            authenticated = false,
            loadingState =oneTapState.opened,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                              oneTapState.open()
            },
            onSuccessfulFirebaseSignIn = {},
            onFailedFirebaseSignIn = {},
            onDialogDismissed = {}
        ) {
            
        }
    }
}

    fun NavGraphBuilder.homeRoute() {
        composable(route = Screen.Home.route) {

        }

    }

    fun NavGraphBuilder.writeRoute() {
        composable(
            route = Screen.Write.route,
            arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY){
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) {

        }

    }


