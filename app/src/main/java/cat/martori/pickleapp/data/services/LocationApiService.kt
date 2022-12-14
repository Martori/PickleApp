package cat.martori.pickleapp.data.services

import cat.martori.pickleapp.data.responses.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationApiService {


    @GET("/api/location/{id}")
    suspend fun getLocation(@Path("id") id: Int): Result<LocationResponse>

}