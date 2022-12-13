package cat.martori.pickleapp.ui.models

import cat.martori.pickleapp.domain.entities.CharacterDetails

data class CharacterDetailsModel(
    val id: Int,
    val name: String,
)

fun CharacterDetails.toDetailsModel() = CharacterDetailsModel(
    id,
    name
)