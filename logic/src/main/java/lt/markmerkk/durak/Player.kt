package lt.markmerkk.durak

/**
 * Virtual "holder" of cards
 */
data class Player(
        val name: String,
        val id: String = "",
        private var cardsInHand: List<Card> = emptyList()
) {

    fun reset() {
        cardsInHand = emptyList()
    }

    fun cardsInHandSize(): Int = cardsInHand.size

    fun cardsInHand(): List<Card> = cardsInHand

    @Throws(IllegalArgumentException::class)
    fun removeCard(card: Card) {
        if (!cardsInHand.contains(card)) {
            throw IllegalArgumentException("Player ${this} does not have $card in hand")
        }
        cardsInHand = cardsInHand.minus(card)
    }

    fun addCards(cards: List<Card>) {
        cardsInHand = cardsInHand.plus(cards)
    }

    fun refill(refillingDeck: RefillingDeck) {
        val refilledCards = cardsInHand.toMutableList()
        val cardsToRefill = Consts.MAX_REFILL_HAND - cardsInHand.size
        (1..cardsToRefill).forEach {
            if (refillingDeck.cards.isNotEmpty()) {
                refilledCards.add(refillingDeck.cards.poll())
            }
        }
        cardsInHand = refilledCards.toList()
    }

    override fun toString(): String {
        return "Player(name=$name)"
    }

}