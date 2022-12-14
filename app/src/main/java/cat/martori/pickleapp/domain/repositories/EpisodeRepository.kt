package cat.martori.pickleapp.domain.repositories

import cat.martori.pickleapp.domain.entities.Episode

interface EpisodeRepository {
    suspend fun getEpisode(id: Int): Result<Episode>
}