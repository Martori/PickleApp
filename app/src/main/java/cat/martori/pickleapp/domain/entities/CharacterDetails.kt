package cat.martori.pickleapp.domain.entities

data class CharacterDetails(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String?,
    val gender: String,
    val origin: Location?,
    val currentLocation: Location?,
    val firstEpisode: Episode?,
) {
    constructor(summary: CharacterSummary, origin: Location?, currentLocation: Location?, firstEpisode: Episode?) : this(
        summary.id,
        summary.name,
        summary.status,
        summary.species,
        summary.type,
        summary.gender,
        origin,
        currentLocation,
        firstEpisode
    )
}