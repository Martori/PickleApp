package cat.martori.pickleapp.ui.models

import androidx.compose.ui.graphics.Color
import cat.martori.pickleapp.domain.entities.CharacterSummary
import cat.martori.pickleapp.domain.entities.Status

data class CharacterItemModel(
    val id: Int,
    val name: String,
    val species: String,
    val avatarUrl: String,
    val statusColor: Color,
) {
}

fun CharacterSummary.toCharacterItemModel() = CharacterItemModel(
    id,
    name,
    species,
    imageUrl,
    when (status) {
        Status.Alive -> Color.Green
        Status.Dead -> Color.Red
        Status.Unknown -> Color.LightGray
    }
)

fun List<CharacterSummary>.toCharacterItemModel() = map { it.toCharacterItemModel() }