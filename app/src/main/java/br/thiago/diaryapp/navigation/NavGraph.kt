package br.thiago.diaryapp.navigation


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.thiago.diaryapp.data.repository.MongoDB
import br.thiago.diaryapp.model.RequestState
import br.thiago.diaryapp.presentation.components.DisplayAlertDialog
import br.thiago.diaryapp.presentation.screens.auth.AuthenticationScreen
import br.thiago.diaryapp.presentation.screens.auth.AuthenticationViewModel
import br.thiago.diaryapp.presentation.screens.home.HomeScreen
import br.thiago.diaryapp.presentation.screens.home.HomeViewModel
import br.thiago.diaryapp.util.Constants.APP_ID
import br.thiago.diaryapp.util.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    onDataLoaded: () -> Unit
) {
    NavHost(
        startDestination = startDestination,
        navController = navController,

        ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            },
            onDataLoaded = onDataLoaded
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screen.Write.route)
            },
            navigateToWriteWithArgs = {
                navController.navigate(Screen.Write.passDiaryId(diaryId = it))
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screen.Authentication.route)
            },
            onDataLoaded = onDataLoaded
        )
//        writeRoute(
//            navigateBack = {
//                navController.popBackStack()
//            }
//        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit,
    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Authentication.route) {
        val viewModel: AuthenticationViewModel = viewModel()
        val authenticated by viewModel.authenticated
        val loadingState by viewModel.loadingState
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()

        LaunchedEffect(key1 = Unit) {
            onDataLoaded()
        }

        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                viewModel.setLoading(true)
            },
            onSuccessfulFirebaseSignIn = { tokenId ->
                viewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated!")
                        viewModel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        viewModel.setLoading(false)
                    }
                )
            },
            onFailedFirebaseSignIn = {
                messageBarState.addError(it)
                viewModel.setLoading(false)
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                viewModel.setLoading(false)
            },
            navigateToHome = navigateToHome
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToWriteWithArgs: (String) -> Unit,
    navigateToAuth: () -> Unit,
    onDataLoaded: () -> Unit
) {
    composable(route = Screen.Home.route) {
        val viewModel: HomeViewModel = hiltViewModel()
        val diaries by viewModel.diaries
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val context = LocalContext.current
        var signOutDialogOpened by remember { mutableStateOf(false) }
        var deleteAllDialogOpened by remember { mutableStateOf(false) }

        LaunchedEffect(key1 = diaries) {
            if (diaries !is RequestState.Loading) {
                onDataLoaded()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            HomeScreen(
                diaries = diaries,
                drawerState = drawerState,
                onMenuClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                dateIsSelected = viewModel.dateIsSelected,
                onDateSelected = { viewModel.getDiaries(zonedDateTime = it) },
                onDateReset = { viewModel.getDiaries() },
                onSignOutClicked = { signOutDialogOpened = true },
                onDeleteAllClicked = { deleteAllDialogOpened = true },
                navigateToWrite = navigateToWrite,
                navigateToWriteWithArgs = navigateToWriteWithArgs
            )
            
            LaunchedEffect(key1 = Unit){
                MongoDB.configureTheRealm()
            }
            DisplayAlertDialog(
                title = "Sign Out",
                message = "Are you sure you want to Sign Out from your Google Account?",
                dialogOpened = signOutDialogOpened,
                onDialogClosed = { signOutDialogOpened = false },
                onYesClicked = {
                    scope.launch(Dispatchers.IO) {
                        val user = App.create(APP_ID).currentUser
                        if(user != null){
                            user.logOut()
                         withContext(Dispatchers.Main){
                             navigateToAuth()
                         }

                        }
                    }
                })

        }

        fun NavGraphBuilder.writeRoute() {
            composable(
                route = Screen.Write.route,
                arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
            ) {

            }

        }
    }
}


