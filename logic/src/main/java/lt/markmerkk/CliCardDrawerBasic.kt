package lt.markmerkk

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.CardSuite

/**
 * Responsible for drawing cards in basic form
 */
class CliCardDrawerBasic(
    override val firstLineBreak: Boolean,
): CliCardDrawer {

    override fun drawCards(
        cards: List<Card>
    ): String {
        val sb = StringBuilder()
        if (firstLineBreak) {
            sb.append("\n")
        }
        val cardsRepresentation = cards.map { card ->
            val representation = CardTemplate.from(card)
                .representationLine
                .format(card.rank.out)
            representation
        }
        sb.append(cardsRepresentation.joinToString(", "))
        return sb.toString()
    }

    /**
     * Represents a display for a card in CLI form
     * Upper half will display card rank (will always form as a 2 symbol)
     * Lower half will display card rank + suite as a 3 symbol template (will always for as 3 symbol)
     */
    enum class CardTemplate(
            val representationLine: String
    ) {
        SPADE("%s♠"),
        DIAMOND("%s♦"),
        CLUB("%s♣"),
        HEART("%s❤")
        ;

        companion object {
            fun from(card: Card): CardTemplate = when(card.suite) {
                CardSuite.SPADE -> SPADE
                CardSuite.DIAMOND -> DIAMOND
                CardSuite.HEART -> HEART
                CardSuite.CLUB -> CLUB
            }
        }
    }

}