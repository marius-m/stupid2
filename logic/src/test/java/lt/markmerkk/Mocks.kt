package lt.markmerkk

import lt.markmerkk.durak.*

object Mocks {

    fun createCards(vararg cards: Card): List<Card> {
        return cards.toList()
    }

    fun createPlayer(
            name: String = "valid_player",
            cardsInHand: List<Card> = emptyList()
    ): Player {
        return Player(
                name = name,
                cardsInHand = cardsInHand
        )
    }

    fun createPlayingTable(
            cardsOnTable: List<PlayingCardPair> = emptyList()
    ): PlayingTable {
        return PlayingTable(cardsOnTable)
    }

    fun createTurnsManager(
            attackingPlayer: Player = createPlayer(name = "attacking_player"),
            defendingPlayer: Player = createPlayer(name = "defending_player"),
            sidelinePlayer: Player = createPlayer(name = "sideline_player")
    ): TurnsManager {
        return TurnsManager(
                listOf(
                        attackingPlayer,
                        defendingPlayer,
                        sidelinePlayer
                )
        )
    }

}