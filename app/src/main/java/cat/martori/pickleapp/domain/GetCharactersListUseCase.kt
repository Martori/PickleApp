package cat.martori.pickleapp.domain

class GetCharactersListUseCase(private val charactersRepository: CharactersRepository) {
    operator fun invoke() = charactersRepository.getAllCharactersList()
}