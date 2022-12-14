package cat.martori.pickleapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cat.martori.pickleapp.R
import cat.martori.pickleapp.domain.entities.CharacterDetails
import cat.martori.pickleapp.ui.models.CharacterDetailsModel
import cat.martori.pickleapp.ui.models.toDetailsModel
import cat.martori.pickleapp.ui.viewModels.CharacterDetailsAction
import cat.martori.pickleapp.ui.viewModels.CharacterDetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

data class CharacterDetailsState(
    val details: CharacterDetailsModel?,
    val error: Throwable?,
) {

    val loading = details == null && error == null

    companion object {
        val DEFAULT = CharacterDetailsState(null, null)
    }

    fun applyResult(result: Result<CharacterDetails>) = copy(
        details = result.map { it.toDetailsModel() }.getOrNull(),
        error = result.exceptionOrNull()
    )

}

@Composable
fun CharacterDetailsScreen(id: Int, viewModel: CharacterDetailsViewModel = koinViewModel { parametersOf(id) }) {
    val state by viewModel.state.collectAsState()
    CharacterDetailsScreen(state) { viewModel.act(it) }

}

@Composable
private fun CharacterDetailsScreen(state: CharacterDetailsState, sendAction: (CharacterDetailsAction) -> Unit) {
    Scaffold(
        topBar = { DetailsTopBar(sendAction, state) }
    ) { paddings ->
        state.details?.let { character ->
            DetailsBody(character, Modifier.padding(paddings))
        }
        state.error?.let {
            ErrorBody(sendAction)
        }
        if (state.loading) {
            LoadingBody(Modifier.padding(paddings))
        }
    }
}

@Composable
private fun LoadingBody(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
private fun ErrorBody(sendAction: (CharacterDetailsAction) -> Unit) {
    ErrorDialog(stringResource(R.string.defaultErrorMessage)) {
        sendAction(CharacterDetailsAction.GoBack)
    }
}

@Composable
private fun DetailsBody(character: CharacterDetailsModel, modifier: Modifier = Modifier) {
    Box(modifier) {
        Text(
            character.toString()
        )
    }
}

@Composable
private fun DetailsTopBar(sendAction: (CharacterDetailsAction) -> Unit, state: CharacterDetailsState) {
    TopAppBar {
        IconButton(onClick = { sendAction(CharacterDetailsAction.GoBack) }) {
            Icon(painter = painterResource(R.drawable.ic_arrow_back), contentDescription = stringResource(R.string.goBackDescription))
        }
        state.details?.let { character ->
            Text(character.name)
        }
    }
}

