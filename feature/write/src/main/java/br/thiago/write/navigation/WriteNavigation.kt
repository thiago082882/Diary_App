package br.thiago.write.navigation


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.thiago.util.Constants
import br.thiago.util.Screen
import br.thiago.util.model.Mood
import br.thiago.write.WriteScreen
import br.thiago.write.WriteViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.writeRoute(navigateBack: () -> Unit) {
    composable(
        route = Screen.Write.route,
        arguments = listOf(navArgument(name = Constants.WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {
        val viewModel: WriteViewModel = hiltViewModel()
        val context = LocalContext.current
        val uiState = viewModel.uiState
        val galleryState = viewModel.galleryState
        val pagerState = rememberPagerState(pageCount = { Mood.entries.size })
        val pageNumber by remember { derivedStateOf { pagerState.currentPage } }

        WriteScreen(
            uiState = uiState,
            pagerState = pagerState,
            galleryState = galleryState,
            moodName = { Mood.entries[pageNumber].name },
            onTitleChanged = { viewModel.setTitle(title = it) },
            onDescriptionChanged = { viewModel.setDescription(description = it) },
            onDateTimeUpdated = { viewModel.updateDateTime(zonedDateTime = it) },
            onBackPressed = navigateBack,
            onDeleteConfirmed = {
                viewModel.deleteDiary(
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateBack()
                    },
                    onError = { message ->
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            },
            onSaveClicked = {
                viewModel.upsertDiary(
                    diary = it.apply { mood = Mood.entries[pageNumber].name },
                    onSuccess = navigateBack,
                    onError = { message ->
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            },
            onImageSelect = {
                val type = context.contentResolver.getType(it)?.split("/")?.last() ?: "jpg"
                viewModel.addImage(image = it, imageType = type)
            },
            onImageDeleteClicked = { galleryState.removeImage(it) }
        )
    }
}