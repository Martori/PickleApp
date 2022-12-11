package cat.martori.pickleapp.characterList.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.martori.pickleapp.R
import cat.martori.pickleapp.characterList.ui.models.CharacterItemModel
import cat.martori.pickleapp.characterList.ui.viewModels.CharactersListViewModel
import cat.martori.pickleapp.ui.theme.PickleAppTheme
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterList(viewModel: CharactersListViewModel = koinViewModel()) {
    CharacterList(characters = viewModel.characters)
}

@Composable
fun CharacterList(characters: List<CharacterItemModel>) {
    LazyColumn(
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        items(characters) {
            CharacterItem(it)
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
        AvatarWithState(model.avatarUrl, model.statusColor)
        Spacer(modifier = androidx.compose.ui.Modifier.size(12.dp))
        Column {
            Text(model.name, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.h6)
            Text(model.species, color = MaterialTheme.colors.onSurface, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
private fun AvatarWithState(imageUrl: String, statusColor: Color) {
    Box {
        AsyncImage(
            imageUrl,
            contentDescription = null,
            modifier = androidx.compose.ui.Modifier
                .size(64.dp)
                .clip(CircleShape),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_foreground)
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
        AvatarWithState("https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red)
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
            listOf(
                CharacterItemModel("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                CharacterItemModel("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                CharacterItemModel("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
                CharacterItemModel("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Color.Red),
            )
        )
    }
}