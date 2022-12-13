package cat.martori.pickleapp.data

import cat.martori.pickleapp.domain.CharacterList
import cat.martori.pickleapp.domain.CharactersRepository
import cat.martori.pickleapp.domain.flatMap
import kotlinx.coroutines.flow.MutableStateFlow

private const val NETWORK_PAGE_SIZE = 20

class RetrofitCharactersRepository(private val characterApiService: CharacterApiService) : CharactersRepository {

    private val characters = MutableStateFlow(Result.success(CharacterList(emptyList(), 0)))

    override fun getAllCharactersList() = characters

    override suspend fun requestCharacters(currentAmount: Int) {
        val nextPage = computeNextPage(currentAmount)
        characters.value = characters.value.flatMap { current ->
            characterApiService.getAllCharacters(nextPage).map { response ->
                current.applyResponse(response)
            }
        }
    }

    private fun CharacterList.applyResponse(response: CharactersResponse) = copy(
        characters = characters + response.results.map { it.toDomain() },
        totalAmount = response.info.count
    )

    private fun computeNextPage(currentAmount: Int) = (currentAmount / NETWORK_PAGE_SIZE) + 1

}
