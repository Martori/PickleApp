package cat.martori.pickleapp.data.services

import cat.martori.pickleapp.data.responses.CharactersResponse
import cat.martori.pickleapp.domain.*
import retrofit2.http.GET
import retrofit2.http.Query


interface CharacterApiService {

    @GET("/api/character")
    suspend fun getAllCharacters(@Query("page") page: Int): Result<CharactersResponse>

}