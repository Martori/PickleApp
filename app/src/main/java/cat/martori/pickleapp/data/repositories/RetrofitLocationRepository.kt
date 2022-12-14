package cat.martori.pickleapp.data.repositories

import cat.martori.pickleapp.data.services.LocationApiService
import cat.martori.pickleapp.domain.entities.Location
import cat.martori.pickleapp.domain.repositories.LocationRepository

class RetrofitLocationRepository(
    private val locationApiService: LocationApiService
) : LocationRepository {
    override suspend fun getLocation(id: Int?): Result<Location> =
        id?.let {
            locationApiService.getLocation(it).map { it.toLocation() }
        } ?: Result.success(Location(null, "unknown", null))
}