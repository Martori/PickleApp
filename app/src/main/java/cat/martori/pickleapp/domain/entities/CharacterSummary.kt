package cat.martori.pickleapp.domain.entities

import com.google.gson.annotations.SerializedName


data class CharacterSummary(
    val id: Int,
    val name: String,
    val species: String,
    val imageUrl: String,
    val status: Status
)

enum class Status {
    @SerializedName("Alive")
    Alive,

    @SerializedName("Dead")
    Dead,

    @SerializedName("unknown")
    Unknown,
}