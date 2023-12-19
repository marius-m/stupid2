package lt.markmerkk

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.CardSuite

/**
 * Responsible for drawing cards
 * Source: https://www.asciiart.eu/miscellaneous/playing-cards
 * Author: ejm98
 */
class CliCardDrawer {

    fun drawCards(vararg cards: Card) = drawCards(cards.toList())

    fun drawCards(cards: List<Card>): String {
        val sb = StringBuilder()
        val cardCount = cards.size
        val cardTemplates = cards.map { CardTemplate.from(it) }
        for (representationLineIndex in CardTemplate.representationLineRange) {
            for (cardIndex in 0 until cardCount) {
                val cardDrawing = when (representationLineIndex) {
                    CardTemplate.LINE_RANK_LABEL_FIRST -> cardTemplates[cardIndex]
                            .representationLines[representationLineIndex].format(CardTemplate.cardDisplayUpper(cards[cardIndex]))
                    CardTemplate.LINE_RANK_LABEL_LAST -> cardTemplates[cardIndex]
                            .representationLines[representationLineIndex].format(CardTemplate.cardDisplayLower(cards[cardIndex]))
                    else -> cardTemplates[cardIndex].representationLines[representationLineIndex]
                }
                sb.append(cardDrawing)
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    /**
     * Represents a display for a card in CLI form
     * Upper half will display card rank (will always form as a 2 symbol)
     * Lower half will display card rank + suite as a 3 symbol template (will always for as 3 symbol)
     */
    enum class CardTemplate(
            val representationLines: List<String>
    ) {
        SPADE(listOf(
                " _______ ",
                "|%s .   |",
                "|  /.\\  |",
                "| (_._) |",
                "|   |   |",
                "|____%s|"
        )),
        DIAMOND(listOf(
                " _______ ",
                "|%s ^   |",
                "|  / \\  |",
                "|  \\ /  |",
                "|   .   |",
                "|____%s|"
        )),
        CLUB(listOf(
                " _______ ",
                "|%s _   |",
                "|  ( )  |",
                "| (_'_) |",
                "|   |   |",
                "|____%s|"
        )),
        HEART(listOf(
                " _______ ",
                "|%s_ _  |",
                "| ( v ) |",
                "|  \\ /  |",
                "|   .   |",
                "|____%s|"
        ))
        ;

        companion object {
            const val MAX_LINES = 6
            const val LINE_FIRST = 0
            const val LINE_LAST = MAX_LINES - 1
            const val LINE_RANK_LABEL_FIRST = 1
            const val LINE_RANK_LABEL_LAST = MAX_LINES - 1
            const val LINE_MAX_COLUMNS = 8

            val representationLineRange = 0 until MAX_LINES

            fun from(card: Card): CardTemplate = when(card.suite) {
                CardSuite.SPADE -> SPADE
                CardSuite.DIAMOND -> DIAMOND
                CardSuite.HEART -> HEART
                CardSuite.CLUB -> CLUB
            }

            /**
             * Formats upper card rank display
             * Will format display as a 2 symbol part.
             * ex: 9 == "9 "; 10 == "10"
             */
            internal fun cardDisplayUpper(card: Card): String {
                if (card.rank.out.count() > 1) {
                    return card.rank.out
                }
                return "${card.rank.out} "
            }

            /**
             * Formats lower card suite and rank verbally
             * Will format display as a 3 symbol part.
             * ex: SPADE NINE == "_S9"; HEART TEN == "H10"
             */
            internal fun cardDisplayLower(card: Card): String {
                val displayLower = "${card.suite.out}${card.rank.out}"
                if (displayLower.count() < 3) {
                    return "_$displayLower"
                }
                return displayLower
            }

        }

    }

}