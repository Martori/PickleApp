package cat.martori.pickleapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.martori.pickleapp.R
import cat.martori.pickleapp.ui.models.CharacterItemModel
import cat.martori.pickleapp.ui.theme.PickleAppTheme
import cat.martori.pickleapp.ui.viewModels.CharactersListViewModel
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterList(viewModel: CharactersListViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    CharacterList(state)
}

data class CharactersListState(
    val characters: List<CharacterItemModel>,
    val error: Throwable?,
    val loading: Boolean
) {
    companion object {
        val DEFAULT = CharactersListState(emptyList(), null, true)
    }
}

@Composable
fun CharacterList(state: CharactersListState) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        items(state.characters) {
            CharacterItem(it)
        }
        if (state.loading) {
            item {
                Text(text = "Loading...")
            }
        }
    }
}

@Composable
private fun CharacterItem(model: CharacterItemModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        AvatarWithStatus(model.avatarUrl, model.statusColor)
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            Text(model.name, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.h6)
            Text(model.species, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
private fun AvatarWithStatus(imageUrl: String, statusColor: Color) {
    Box {
        AsyncImage(
            imageUrl,
            contentDescription = null,
            modifier = androidx.compose.ui.Modifier
                .size(64.dp)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.avatar_placeholder),
            error = painterResource(id = R.drawable.avatar_placeholder)
        )
        Box(
            modifier = androidx.compose.ui.Modifier
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
        AvatarWithStatus("https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red)
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterItemPreview() {
    PickleAppTheme {
        CharacterItem(
            CharacterItemModel("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterListPreview() {
    PickleAppTheme {
        CharacterList(
            CharactersListState(
                listOf(
                    CharacterItemModel("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                    CharacterItemModel("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                    CharacterItemModel("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                    CharacterItemModel("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                ),
                null,
                false
            )
        )
    }
}