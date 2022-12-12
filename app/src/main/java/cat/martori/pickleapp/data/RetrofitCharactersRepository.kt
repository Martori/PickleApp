package cat.martori.pickleapp.data

import cat.martori.pickleapp.domain.Character
import cat.martori.pickleapp.domain.CharactersRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class RetrofitCharactersRepository(private val characterApiService: CharacterApiService) : CharactersRepository {

    private val characters = MutableStateFlow(Result.success(emptyList<Character>()))

    override fun getAllCharactersList(): Flow<Result<List<Character>>> = characters

    override suspend fun requestCharacters() {
        delay(1000)
        characters.value = characterApiService.getAllCharacters().map { response -> response.results.map { it.toDomain() } }
    }
}