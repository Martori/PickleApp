package cat.martori.pickleapp.data

import cat.martori.pickleapp.domain.Character
import cat.martori.pickleapp.domain.CharactersRepository
import cat.martori.pickleapp.domain.flatMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

private const val NETWORK_PAGE_SIZE = 20

class RetrofitCharactersRepository(private val characterApiService: CharacterApiService) : CharactersRepository {

    private val characters = MutableStateFlow(Result.success(emptyList<Character>()))

    override fun getAllCharactersList(): Flow<Result<List<Character>>> = characters

    override suspend fun requestCharacters(currentAmount: Int) {
        val nextPage = computeNextPage(currentAmount)

        characters.value = characters.value.flatMap { current ->
            characterApiService.getAllCharacters(nextPage).map { response ->
                current + response.results.map { it.toDomain() }
            }
        }
    }

    private fun computeNextPage(currentAmount: Int) = (currentAmount / NETWORK_PAGE_SIZE) + 1

}
