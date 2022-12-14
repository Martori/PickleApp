package cat.martori.pickleapp.domain.repositories

import cat.martori.pickleapp.domain.entities.CharacterList
import cat.martori.pickleapp.domain.entities.CharacterSummary
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getAllCharactersList(): Flow<Result<CharacterList>>
    suspend fun requestCharacters(currentAmount: Int)
    suspend fun getCharacter(id: Int): Result<CharacterSummary>
}