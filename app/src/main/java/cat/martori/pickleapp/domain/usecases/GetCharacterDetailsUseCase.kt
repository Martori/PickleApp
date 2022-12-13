package cat.martori.pickleapp.domain.usecases

import cat.martori.pickleapp.domain.repositories.CharactersRepository

class GetCharacterDetailsUseCase(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(id: Int) = charactersRepository.getCharacter(id)

}