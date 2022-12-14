package cat.martori.pickleapp.data.repositories

import cat.martori.pickleapp.domain.entities.Location
import cat.martori.pickleapp.domain.repositories.LocationRepository

class RetrofitLocationRepository : LocationRepository {
    override suspend fun getLocation(id: Int?): Result<Location> = Result.success(Location(id, "named: $id", "fake"))
}