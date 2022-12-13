package cat.martori.pickleapp.domain.entities

data class CharacterList(
    val characters: List<CharacterSummary>,
    val totalAmount: Int
) {
    private val currentAmount = characters.size
    val isComplete = currentAmount == totalAmount

    companion object {
        val EMPTY = CharacterList(emptyList(), 0)
    }
}