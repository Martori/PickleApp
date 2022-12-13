package cat.martori.pickleapp.domain.usecases

import cat.martori.pickleapp.domain.repositories.CharactersRepository

class GetCharactersListUseCase(private val charactersRepository: CharactersRepository) {
    operator fun invoke() = charactersRepository.getAllCharactersList()
}