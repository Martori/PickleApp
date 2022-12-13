package cat.martori.pickleapp.domain

data class CharacterList(
    val characters: List<CharacterSummary>,
    val totalAmount: Int
) {
    private val currentAmount = characters.size
    val isComplete = currentAmount == totalAmount
}