package cat.martori.pickleapp.data.responses

import cat.martori.pickleapp.domain.entities.Episode

data class EpisodeResponse(
    val id: Int,
    val name: String,
    val air_date: String,
) {
    fun toEpisode() = Episode(
        id,
        name,
        air_date
    )
}