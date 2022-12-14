package cat.martori.pickleapp.data.responses

import cat.martori.pickleapp.domain.entities.LocationSummary

data class EmbeddedLocationResponse(
    val name: String,
    val url: String,
) {
    fun toLocationSummary() = with(IdExtracter) { LocationSummary(url.extractId(), name, url) }
}