package cat.martori.pickleapp.domain

import com.google.gson.annotations.SerializedName


class Character(
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