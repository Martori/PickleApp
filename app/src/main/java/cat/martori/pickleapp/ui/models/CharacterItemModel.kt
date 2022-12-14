package cat.martori.pickleapp.ui.models

import cat.martori.pickleapp.domain.entities.CharacterSummary
import cat.martori.pickleapp.domain.entities.Status

data class CharacterItemModel(
    val id: Int,
    val name: String,
    val species: String,
    val avatarUrl: String,
    val status: Status,
) {
}

fun CharacterSummary.toCharacterItemModel() = CharacterItemModel(
    id,
    name,
    species,
    imageUrl,
    status
)

fun List<CharacterSummary>.toCharacterItemModel() = map { it.toCharacterItemModel() }