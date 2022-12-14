package cat.martori.pickleapp.data.services

import cat.martori.pickleapp.data.responses.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeApiService {

    @GET("/api/episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): Result<EpisodeResponse>

}