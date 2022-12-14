package cat.martori.pickleapp.data.repositories

import cat.martori.pickleapp.data.services.EpisodeApiService
import cat.martori.pickleapp.domain.entities.Episode
import cat.martori.pickleapp.domain.repositories.EpisodeRepository

class RetrofitEpisodeRepository(private val episodeApiService: EpisodeApiService) : EpisodeRepository {
    override suspend fun getEpisode(id: Int): Result<Episode> =
        episodeApiService.getEpisode(id).map { it.toEpisode() }
}