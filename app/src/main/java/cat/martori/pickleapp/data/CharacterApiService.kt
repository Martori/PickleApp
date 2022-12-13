package cat.martori.pickleapp.data

import cat.martori.pickleapp.domain.*
import retrofit2.http.GET
import retrofit2.http.Query

data class CharactersResponse(
    val results: List<CharacterData>,
    val info: PaginationInfo,
)

data class PaginationInfo(
    val count: Int,
)

class CharacterData(
    val name: String,
    val species: String,
    val image: String,
    val status: Status
) {
    fun toDomain() = CharacterSummary(
        name, species, image, status
    )
}


interface CharacterApiService {

    @GET("/api/character")
    suspend fun getAllCharacters(@Query("page") page: Int): Result<CharactersResponse>

}