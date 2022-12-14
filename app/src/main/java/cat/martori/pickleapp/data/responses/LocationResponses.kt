package cat.martori.pickleapp.data.responses

import cat.martori.pickleapp.domain.entities.Location
import cat.martori.pickleapp.domain.entities.LocationSummary

data class EmbeddedLocationResponse(
    val name: String,
    val url: String,
) {
    fun toLocationSummary() = with(IdExtracter) { LocationSummary(url.extractId(), name, url) }
}

data class LocationResponse(
    val id: Int,
    val name: String,
    val dimension: String,
) {
    fun toLocation() = Location(
        id,
        name,
        dimension
    )
}