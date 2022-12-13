package cat.martori.pickleapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import cat.martori.pickleapp.R
import cat.martori.pickleapp.ui.models.CharacterDetailsModel
import cat.martori.pickleapp.ui.viewModels.CharacterDetailsAction
import cat.martori.pickleapp.ui.viewModels.CharacterDetailsViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

data class CharacterDetailsState(
    val details: CharacterDetailsModel
)

@Composable
fun CharacterDetailsScreen(id: Int, viewModel: CharacterDetailsViewModel = koinViewModel { parametersOf(id) }) {
    CharacterDetailsScreen(id,
        { viewModel.act(CharacterDetailsAction.GoBack) }
    )
}

@Composable
private fun CharacterDetailsScreen(id: Int, goBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(onClick = { goBack() }) {
                    Icon(painter = painterResource(R.drawable.ic_arrow_back), contentDescription = stringResource(R.string.goBackDescription))
                }
                Text("Character id = $id")
            }
        }
    ) {
        Box(Modifier.padding(it)) {
            Text("Character id = $id")
        }
    }
}

