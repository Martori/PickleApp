package cat.martori.pickleapp.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.martori.pickleapp.R
import cat.martori.pickleapp.domain.entities.CharacterDetails
import cat.martori.pickleapp.domain.entities.Status
import cat.martori.pickleapp.ui.models.CharacterDetailsModel
import cat.martori.pickleapp.ui.models.toDetailsModel
import cat.martori.pickleapp.ui.theme.PickleAppTheme
import cat.martori.pickleapp.ui.viewModels.CharacterDetailsAction
import cat.martori.pickleapp.ui.viewModels.CharacterDetailsViewModel
import coil.compose.AsyncImage
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
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = character.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            placeholder = painterResource(id = R.drawable.main_logo),
            contentScale = ContentScale.Crop
        )

        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

            Text(character.name, style = MaterialTheme.typography.h3)

            CharacterStatusCard(character)

            character.origin?.let { origin ->
                CharacterInfoSection(R.string.FromOrigin, origin)
            }

            character.currentLocation?.let { currentLocation ->
                CharacterInfoSection(R.string.LastSeen, currentLocation)
            }

            character.firstSeen?.let { episode ->
                CharacterInfoSection(R.string.FirstEpisode, episode)
            }
        }
    }
}

@Composable
private fun ColumnScope.CharacterInfoSection(title: Int, text: String) {
    Text(stringResource(title), style = MaterialTheme.typography.h6)
    Text(text, style = MaterialTheme.typography.subtitle1)
}

@Composable
private fun CharacterStatusCard(character: CharacterDetailsModel, modifier: Modifier = Modifier) {
    Card(
        modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant),
        backgroundColor = MaterialTheme.colors.primarySurface
    ) {
        Row(Modifier.padding(4.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            StatusWithColor(character)
            VerticalCharacterDivider()
            Text(character.species)
            VerticalCharacterDivider()
            Text(character.gender)
        }
    }
}

@Composable
private fun StatusWithColor(character: CharacterDetailsModel) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        StatusCircle(character.status, Modifier.align(CenterVertically))
        Text(character.status.name)
    }
}

@Composable
private fun VerticalCharacterDivider() {
    Divider(
        Modifier
            .width(1.dp)
            .fillMaxHeight(),
        color = MaterialTheme.colors.primaryVariant
    )
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

@Preview(showBackground = true)
@Composable
private fun DetailsBodyPreview() {
    PickleAppTheme {
        DetailsBody(character = CharacterDetailsModel(0, "name", "fakeUrl", Status.Alive, "species", "gender", null, null, null))
    }
}

