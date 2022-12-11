package cat.martori.pickleapp.ui.models

import androidx.compose.ui.graphics.Color
import cat.martori.pickleapp.domain.Character
import cat.martori.pickleapp.domain.Status

data class CharacterItemModel(
    val name: String,
    val species: String,
    val avatarUrl: String,
    val statusColor: Color,
)

fun Character.toCharacterItemModel() = CharacterItemModel(
    name,
    species,
    imageUrl,
    when (status) {
        Status.ALive -> Color.Green
        Status.Dead -> Color.Red
        Status.Unknown -> Color.LightGray
    }
)