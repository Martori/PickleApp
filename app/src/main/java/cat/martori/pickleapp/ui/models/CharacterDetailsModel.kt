package cat.martori.pickleapp.ui.models

import cat.martori.pickleapp.domain.entities.CharacterDetails
import cat.martori.pickleapp.domain.entities.Status

data class CharacterDetailsModel(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: String,
    val origin: String?,
    val currentLocation: String?,
    val firstSeen: String?,
)

fun CharacterDetails.toDetailsModel() = CharacterDetailsModel(
    id,
    name,
    status,
    species + type?.let { " - $it" }.orEmpty(),
    gender,
    origin?.let { origin.name + origin.dimension?.let { " - $it" }.orEmpty() },
    currentLocation?.let { currentLocation.name + currentLocation.dimension?.let { " - $it" }.orEmpty() },
    firstEpisode?.let { "${firstEpisode.name} - ${firstEpisode.airDate}" },
)