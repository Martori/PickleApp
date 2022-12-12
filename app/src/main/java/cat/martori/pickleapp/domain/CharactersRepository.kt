package cat.martori.pickleapp.domain

import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getAllCharactersList(): Flow<Result<List<CharacterSummary>>>
    suspend fun requestCharacters(currentAmount: Int)
}