package cat.martori.pickleapp.data.responses

import cat.martori.pickleapp.domain.entities.CharacterSummary
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
    val image: String,
    val status: Status
) {
    fun toDomain() = CharacterSummary(
        id, name, species, image, status
    )
}