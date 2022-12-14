package cat.martori.pickleapp.domain.entities

import com.google.gson.annotations.SerializedName


data class CharacterSummary(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val status: Status,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: LocationSummary,
    val currentLocation: LocationSummary,
    val firstEpisode: EpisodeSummary,
)

enum class Status {
    @SerializedName("Alive")
    Alive,

    @SerializedName("Dead")
    Dead,

    @SerializedName("unknown")
    Unknown,
}