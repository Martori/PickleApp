package cat.martori.pickleapp.domain.repositories

import cat.martori.pickleapp.domain.entities.Location

interface LocationRepository {
    suspend fun getLocation(id: Int?): Result<Location>
}