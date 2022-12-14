package cat.martori.pickleapp.data.repositories

import cat.martori.pickleapp.domain.entities.Episode
import cat.martori.pickleapp.domain.repositories.EpisodeRepository

class RetrofitEpisodeRepository : EpisodeRepository {
    override suspend fun getEpisode(id: Int): Result<Episode> = Result.success(Episode(1, "Episode 1", "never"))
}