package lt.markmerkk.durak

/**
 * Defines playing table, where attacking player tries to get rid of his cards
 */
class PlayingTable(
        var cards: List<PlayingCardPair>
) {

    fun allAttackingCards(): List<Card> {
        return cards.map { it.attackingCard }
    }

    fun allDefendingCards(): List<Card> = cards
            .mapNotNull { it.defendingCard }

    fun allCards(): List<Card> = allAttackingCards()
            .plus(allDefendingCards())

    /**
     * Clears all cards from the table
     */
    fun clearAllCards() {
        cards = emptyList()
    }

    /**
     * Place attacking [Card] on the [PlayingTable]
     * Attacking [Card] must be of the same [CardRank] as any card on the [PlayingTable]
     * Attacking card pairs cannot exceed [Consts.MAX_PAIRS_ON_TABLE]
     */
    @Throws(IllegalArgumentException::class)
    fun attack(card: Card) {
        if (cards.size >= Consts.MAX_PAIRS_ON_TABLE) {
            throw IllegalArgumentException("Table already has max card pairs (${Consts.MAX_PAIRS_ON_TABLE})")
        }
        val ranksOnTable = filterRanksOnTable()
        val cardRankIsOnTable = ranksOnTable.contains(card.rank)
        if (cards.isNotEmpty() && !cardRankIsOnTable) {
            throw IllegalArgumentException("No such rank on the table (throwing in $card, $ranksOnTable on table )")
        }
        cards = cards.plus(
                PlayingCardPair(
                        attackingCard = card,
                        defendingCard = null
                )
        )
    }

    /**
     * Place a defending [Card] on [PlayingTable]
     * Defending card must be same [CardRank] and higher than the attacking card
     * @throws IllegalArgumentException whenever there is nothing to defend against
     * @throws IllegalArgumentException whenever card is not the same [CardRank] as undefended card
     * @throws IllegalArgumentException whenever card is lower than the undefended card
     */
    @Throws(IllegalArgumentException::class)
    fun defend(card: Card) {
        val mutableCardPairs = cards.toMutableList()
        val firstUndefendedCard = firstUndefendedCardOnTable() ?: throw IllegalArgumentException("Nothing to defend against")
        if (firstUndefendedCard.suite != card.suite
                && !card.isTrump) {
            throw IllegalArgumentException("Incorrect card being thrown")
        }
        if (firstUndefendedCard.weight() > card.weight()) {
            throw IllegalArgumentException("Card too low")
        }
        val firstUndefendedCardPairIndex = firstUndefendedCardPairIndex()
        mutableCardPairs[firstUndefendedCardPairIndex] = PlayingCardPair(
                attackingCard = firstUndefendedCard,
                defendingCard = card
        )
        cards = mutableCardPairs.toList()
    }

    /**
     * Filters out all the ranks on the table
     */
    fun filterRanksOnTable(): Set<CardRank> {
        val attackingCardRanks = cards.map { it.attackingCard.rank }.toSet()
        val defendingCardRanks = cards
                .filter { it.defendingCard != null }
                .map { it.defendingCard!!.rank }
                .toSet()
        return attackingCardRanks.plus(defendingCardRanks)
    }

    /**
     * Filters out all undefended cards on the table
     */
    fun undefendedCardsOnTable(): List<Card> = cards
            .filter { it.defendingCard == null }
            .map { it.attackingCard }

    fun firstUndefendedCardOnTable(): Card? {
        return cards
                .find { it.defendingCard == null }
                ?.attackingCard
    }

    /**
     * @return first undefended card pair index
     * Will return -1 if no such pair found
     */
    fun firstUndefendedCardPairIndex(): Int {
        cards.forEachIndexed { index, playingCardPair ->
            if (playingCardPair.defendingCard == null)
                return index
        }
        return -1
    }

    override fun toString(): String {
        val cardsOnTable = cards
                .map { "${it.attackingCard}/${it.defendingCard}" }
                .joinToString(",")
        return "PlayingTable:($cardsOnTable)"
    }
}

data class PlayingCardPair(
        val attackingCard: Card,
        val defendingCard: Card?
)