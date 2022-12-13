package cat.martori.pickleapp.domain

import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getAllCharactersList(): Flow<Result<CharacterList>>
    suspend fun requestCharacters(currentAmount: Int)
}