package cat.martori.pickleapp.domain


class Character(
    val name: String,
    val species: String,
    val imageUrl: String,
    val status: Status
)

enum class Status {
    ALive,
    Dead,
    Unknown,
}