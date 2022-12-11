package cat.martori.pickleapp.domain

class RequestCharactersListUseCase(private val charactersRepository: CharactersRepository) {
    suspend operator fun invoke() {
        charactersRepository.requestCharacters()
    }

}
