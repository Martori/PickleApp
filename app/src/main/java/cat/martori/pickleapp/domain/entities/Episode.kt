package cat.martori.pickleapp.domain.entities

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
)


data class EpisodeSummary(val id: Int?, val url: String)
