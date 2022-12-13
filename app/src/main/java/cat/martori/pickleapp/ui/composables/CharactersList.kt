package cat.martori.pickleapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.martori.pickleapp.R
import cat.martori.pickleapp.domain.entities.CharacterList
import cat.martori.pickleapp.ui.models.CharacterItemModel
import cat.martori.pickleapp.ui.models.toCharacterItemModel
import cat.martori.pickleapp.ui.theme.PickleAppTheme
import cat.martori.pickleapp.ui.viewModels.CharacterListAction
import cat.martori.pickleapp.ui.viewModels.CharactersListViewModel
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterListScreen(viewModel: CharactersListViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    CharacterListScreen(state,
        { viewModel.act(CharacterListAction.RequestMoreCharters(it)) },
        { viewModel.act(CharacterListAction.DismissError) },
        { viewModel.act(CharacterListAction.OpenCharacterDetails(it)) })
}

data class CharactersListState(
    val characters: List<CharacterItemModel>,
    val error: Throwable?,
    val loading: Boolean
) {
    companion object {
        val DEFAULT = CharactersListState(emptyList(), null, true)
    }

    fun applyResult(result: Result<CharacterList>) = copy(
        characters = result.map { it.characters.toCharacterItemModel() }.getOrElse { characters },
        error = result.exceptionOrNull(),
        loading = result.map { !it.isComplete }.getOrElse { true }
    )
}

@Composable
fun CharacterListScreen(state: CharactersListState, requestMoreCharacters: (currentAmount: Int) -> Unit, dismissError: () -> Unit, openCharacterDetails: (CharacterItemModel) -> Unit) {
    Scaffold(topBar = {
        TopAppBar {
            Image(modifier = Modifier.padding(8.dp), painter = painterResource(R.drawable.main_logo), contentDescription = stringResource(R.string.mainLogoDescription))
        }
    }) {
        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            itemsIndexed(state.characters) { index, item ->
                if (index == state.characters.lastIndex && state.loading) {
                    LaunchedEffect(state, index) {
                        requestMoreCharacters(state.characters.size)
                    }
                }
                CharacterItem(item) { openCharacterDetails(item) }
            }
            if (state.loading) {
                item {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
        state.error?.let {
            ErrorDialog(stringResource(R.string.defaultErrorMessage), dismissError)
        }

    }
}

@Composable
private fun CharacterItem(model: CharacterItemModel, onClick: () -> Unit) {
    Surface(elevation = 4.dp, shape = MaterialTheme.shapes.medium, modifier = Modifier.clickable { onClick() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
        ) {
            AvatarWithState(model.avatarUrl, model.statusColor)
            Spacer(modifier = Modifier.size(12.dp))
            Column {
                Text(model.name, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.h6)
                Text(model.species, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
private fun AvatarWithState(imageUrl: String, statusColor: Color) {
    Box {
        AsyncImage(
            imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.avatar_placeholder),
            error = painterResource(id = R.drawable.avatar_placeholder)
        )
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(statusColor)
                .align(BiasAlignment(0.85f, 0.85f))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarWithStatePreview() {
    PickleAppTheme {
        AvatarWithState("https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterItemPreview() {
    PickleAppTheme {
        CharacterItem(
            CharacterItemModel(1, "Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red)
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterListPreview() {
    PickleAppTheme {
        CharacterListScreen(
            CharactersListState(
                listOf(
                    CharacterItemModel(1, "Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                    CharacterItemModel(1, "Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                    CharacterItemModel(1, "Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                    CharacterItemModel(1, "Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                ),
                null,
                false
            ), { }, {}, {})
    }
}