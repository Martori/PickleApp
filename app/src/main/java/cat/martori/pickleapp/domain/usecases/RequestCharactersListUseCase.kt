package cat.martori.pickleapp.domain.usecases

import cat.martori.pickleapp.domain.repositories.CharactersRepository

class RequestCharactersListUseCase(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke(currentAmount: Int) {
        charactersRepository.requestCharacters(currentAmount)
    }

}
