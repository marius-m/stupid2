package lt.markmerkk

import lt.markmerkk.durak.Card

interface CliCardDrawer {
    val firstLineBreak: Boolean

    fun drawCards(
        vararg cards: Card
    ) = drawCards(cards.toList())

    fun drawCards(
        cards: List<Card>,
    ): String
}