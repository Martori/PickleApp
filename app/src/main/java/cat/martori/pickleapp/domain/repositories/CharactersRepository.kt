package cat.martori.pickleapp.domain.repositories

import cat.martori.pickleapp.domain.entities.CharacterDetails
import cat.martori.pickleapp.domain.entities.CharacterList
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getAllCharactersList(): Flow<Result<CharacterList>>
    suspend fun requestCharacters(currentAmount: Int)
    fun getCharacter(id: Int): Flow<Result<CharacterDetails>>
}