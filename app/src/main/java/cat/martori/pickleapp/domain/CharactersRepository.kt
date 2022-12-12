package cat.martori.pickleapp.domain

import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getAllCharactersList(): Flow<Result<List<Character>>>
    suspend fun requestCharacters(currentAmount: Int)
}