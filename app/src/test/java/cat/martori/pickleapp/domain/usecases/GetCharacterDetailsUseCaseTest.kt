package cat.martori.pickleapp.domain.usecases

import cat.martori.pickleapp.domain.entities.*
import cat.martori.pickleapp.domain.repositories.CharactersRepository
import cat.martori.pickleapp.domain.repositories.EpisodeRepository
import cat.martori.pickleapp.domain.repositories.LocationRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class GetCharacterDetailsUseCaseTest {

    private val charactersRepository = mockk<CharactersRepository>()
    private val locationRepository = mockk<LocationRepository> {
        coEvery { getLocation(any()) } returns Result.failure(Error("Test"))
    }
    private val episodeRepository = mockk<EpisodeRepository>() {
        coEvery { getEpisode(any()) } returns Result.failure(Error("Test"))
    }

    private val sut = GetCharacterDetailsUseCase(charactersRepository, locationRepository, episodeRepository)

    private val locationSummary = LocationSummary(1, "fake", "fake/1")
    private val secondLocationSummary = LocationSummary(2, "fake", "fake/2")
    private val episodeSummary = EpisodeSummary(1, "fake/1")
    private val testCharacter = CharacterSummary(
        id = 0,
        name = "fake",
        imageUrl = "fake",
        status = Status.Alive,
        species = "fake",
        type = "fake",
        gender = "fake",
        origin = locationSummary,
        currentLocation = secondLocationSummary,
        firstEpisode = episodeSummary,
    )

    @Test
    fun `if characterRepository fails it should return a failure`() = runTest {
        coEvery { charactersRepository.getCharacter(any()) } returns Result.failure(Error("test error"))

        val result = sut(1).first()

        assert(result.isFailure)
    }

    @Test
    fun `if character repository fails it should avoid calling the other repositories`() = runTest {
        coEvery { charactersRepository.getCharacter(any()) } returns Result.failure(Error("test error"))

        val result = sut(1)

        verify { locationRepository wasNot Called }
        verify { episodeRepository wasNot Called }
    }

    @Test
    fun `if character repository responds it should return the same character`() = runTest {
        coEvery { charactersRepository.getCharacter(any()) } returns Result.success(testCharacter)

        val result = sut(1).first()

        assert(result.isSuccess)
        assertEquals(testCharacter.id, result.getOrThrow().id)
    }

    @Test
    fun `if episode request fails episode should be null`() = runTest {
        coEvery { charactersRepository.getCharacter(any()) } returns Result.success(testCharacter)
        coEvery { episodeRepository.getEpisode(any()) } returns Result.failure(Error("test error"))

        val result = sut(1).first()

        assert(result.isSuccess)
        assertNull(result.getOrThrow().firstEpisode)
    }

    @Test
    fun `if locations requests fail episode should be null`() = runTest {
        coEvery { charactersRepository.getCharacter(any()) } returns Result.success(testCharacter)
        coEvery { locationRepository.getLocation(any()) } returns Result.failure(Error("test error"))

        val result = sut(1).first()

        assert(result.isSuccess)
        assertNull(result.getOrThrow().origin)
        assertNull(result.getOrThrow().currentLocation)
    }

    @Test
    fun `if location request only fails for origin only that one should be null`() = runTest {
        coEvery { charactersRepository.getCharacter(any()) } returns Result.success(testCharacter)
        coEvery { locationRepository.getLocation(1) } returns Result.failure(Error("test error"))
        coEvery { locationRepository.getLocation(2) } returns Result.success(Location(2, "second", "place"))

        val result = sut(1).first()

        assert(result.isSuccess)
        assertNull(result.getOrThrow().origin)
        assertNotNull(result.getOrThrow().currentLocation)
    }

    @Test
    fun `if location request only fails for current location only that one should be null`() = runTest {
        coEvery { charactersRepository.getCharacter(any()) } returns Result.success(testCharacter)
        coEvery { locationRepository.getLocation(1) } returns Result.success(Location(1, "first", "place"))
        coEvery { locationRepository.getLocation(2) } returns Result.failure(Error("test error"))

        val result = sut(1).first()

        assert(result.isSuccess)
        assertNotNull(result.getOrThrow().origin)
        assertNull(result.getOrThrow().currentLocation)
    }
}