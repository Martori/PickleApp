package cat.martori.pickleapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val details: CharacterDetailsModel?
) {
    companion object {
        val DEFAULT = CharacterDetailsState(null)
    }

    fun applyResult(result: Result<CharacterDetails>) = copy(
        details = result.map { it.toDetailsModel() }.getOrNull()
    )

}

@Composable
fun CharacterDetailsScreen(id: Int, viewModel: CharacterDetailsViewModel = koinViewModel { parametersOf(id) }) {
    val state by viewModel.state.collectAsState()
    CharacterDetailsScreen(state,
        { viewModel.act(CharacterDetailsAction.GoBack) }
    )

}

@Composable
private fun CharacterDetailsScreen(state: CharacterDetailsState, goBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(onClick = { goBack() }) {
                    Icon(painter = painterResource(R.drawable.ic_arrow_back), contentDescription = stringResource(R.string.goBackDescription))
                }
                state.details?.let { character ->
                    Text(character.name)
                }
            }
        }
    ) {
        state.details?.let { character ->
            Box(Modifier.padding(it)) {
                Text("Character id = ${character.id}")
            }
        }
    }
}

