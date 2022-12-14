package cat.martori.pickleapp.data.responses

import cat.martori.pickleapp.domain.entities.CharacterSummary
import cat.martori.pickleapp.domain.entities.EpisodeSummary
import cat.martori.pickleapp.domain.entities.Status

data class CharactersResponse(
    val results: List<CharacterData>,
    val info: PaginationInfo,
)

data class PaginationInfo(
    val count: Int,
)

class CharacterData(
    val id: Int,
    val name: String,
    val species: String,
    val type: String,
    val image: String,
    val status: Status,
    val gender: String,
    val origin: EmbeddedLocationResponse,
    val location: EmbeddedLocationResponse,
    val episode: List<String>
) {
    fun toCharacterSummary() = with(IdExtracter) {
        CharacterSummary(
            id,
            name,
            image,
            status,
            species,
            type.takeIf { it.isNotEmpty() },
            gender,
            origin.toLocationSummary(),
            location.toLocationSummary(),
            episode.first().let { EpisodeSummary(it.extractId(), it) }
        )
    }
}

