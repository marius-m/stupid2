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

    /**
     * Sorts cards by its weight
     * - Trump suites go first
     * - Higher cards go first
     */
    fun sortByWeight() {
        val mutableCards = mutableListOf<Card>()
        val trumpCardSuite = cardsInHand
            .firstOrNull { it.isTrump }
        val cardSuites: List<CardSuite> = if (trumpCardSuite != null) {
            CardSuite.valuesWithFirstAsTrump(trumpSuite = trumpCardSuite.suite)
        } else {
            CardSuite.values().toList()
        }
        cardSuites.forEach { cardSuite ->
            val cardsOfSuite = cardsInHand
                .filter { it.suite == cardSuite }
                .sortedByDescending { it.weight() }
            mutableCards.addAll(cardsOfSuite)
        }
        cardsInHand = mutableCards.toList()
    }

    override fun toString(): String {
        return "Player(name=$name)"
    }

}