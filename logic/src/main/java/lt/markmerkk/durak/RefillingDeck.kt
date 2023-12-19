package lt.markmerkk.durak

import java.util.*

/**
 * A stack of cards that can replenish player cards after he's out after a round
 */
data class RefillingDeck(
        var cards: Queue<Card>
) {
}