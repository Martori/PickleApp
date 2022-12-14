package cat.martori.pickleapp.domain.usecases

import cat.martori.pickleapp.domain.entities.CharacterDetails
import cat.martori.pickleapp.domain.repositories.CharactersRepository
import cat.martori.pickleapp.domain.repositories.EpisodeRepository
import cat.martori.pickleapp.domain.repositories.LocationRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacterDetailsUseCase(
    private val charactersRepository: CharactersRepository,
    private val locationRepository: LocationRepository,
    private val episodeRepository: EpisodeRepository,
) {
    operator fun invoke(id: Int): Flow<Result<CharacterDetails>> =
        flow {
            coroutineScope {
                charactersRepository.getCharacter(id).map { summary ->
                    val origin = async { locationRepository.getLocation(summary.origin.id).getOrNull() }
                    val current = async { locationRepository.getLocation(summary.currentLocation.id).getOrNull() }
                    val episode = async { summary.firstEpisode.id?.let { episodeRepository.getEpisode(it).getOrNull() } }

                    CharacterDetails(summary, origin.await(), current.await(), episode.await())
                }.also {
                    emit(it)
                }
            }
        }

}