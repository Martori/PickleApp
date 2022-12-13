package cat.martori.pickleapp.data.repositories

import cat.martori.pickleapp.data.responses.CharacterData
import cat.martori.pickleapp.data.responses.CharactersResponse
import cat.martori.pickleapp.data.responses.PaginationInfo
import cat.martori.pickleapp.data.services.CharacterApiService
import cat.martori.pickleapp.domain.entities.CharacterList
import cat.martori.pickleapp.domain.entities.Status
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class RetrofitCharactersRepositoryTest {

    private val characterApiService = mockk<CharacterApiService> {
        coEvery { getAllCharacters(any()) } returns Result.failure(Error("Test Error"))
        coEvery { getCharactersDetails(any()) } returns Result.failure(Error("Test Error"))
    }

    private val sut = RetrofitCharactersRepository(characterApiService)

    private val testCharacterData = CharacterData(0, "test", "human", "fakeUrl", Status.Alive)
    private val successfulListResponse = CharactersResponse(
        listOf(testCharacterData),
        PaginationInfo(1)
    )

    @Test
    fun `if current amount is less than 20 should request the first page`() = runTest {
        sut.requestCharacters(0)

        coVerify {
            characterApiService.getAllCharacters(1)
        }
    }

    @Test
    fun `if current amount is between 20 and 39 should request second page`() = runTest {
        sut.requestCharacters(20)

        coVerify {
            characterApiService.getAllCharacters(2)
        }
    }

    @Test
    fun `get character list should return an empty list if no characters have been requested`() = runTest {

        val result = sut.getAllCharactersList().value

        assertEquals(Result.success(CharacterList.EMPTY), result)

    }

    @Test
    fun `get character list should return a failure if the latest request failed`() = runTest {

        sut.requestCharacters(0)

        val result = sut.getAllCharactersList().value

        assert(result.isFailure)
    }

    @Test
    fun `get character list should return the list from the response if the request is successful`() = runTest {
        coEvery { characterApiService.getAllCharacters(any()) } returns Result.success(successfulListResponse)
        sut.requestCharacters(0)

        val result = sut.getAllCharactersList().value

        assert(result.isSuccess)

        assertEquals(successfulListResponse.results.size, result.getOrThrow().characters.size)
        assertContentEquals(successfulListResponse.results.map { it.toCharacterSummary() }, result.getOrThrow().characters)
        assertEquals(successfulListResponse.info.count, result.getOrThrow().totalAmount)

    }

    @Test
    fun `get character details should return failure if the request fails`() = runTest {

        val result = sut.getCharacter(1).last()

        assert(result.isFailure)
    }

    @Test
    fun `get character details should return the same character as the service`() = runTest {
        coEvery { characterApiService.getCharactersDetails(any()) } returns Result.success(testCharacterData)

        val result = sut.getCharacter(1).last()

        assert(result.isSuccess)
        assertEquals(testCharacterData.toCharacterDetails(), result.getOrThrow())
    }
}