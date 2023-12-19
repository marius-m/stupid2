package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.PlayingTable

/**
 * Defines possible actions for defending player
 */
class PossibleDefendingActionsFilter {

    /**
     * Filters out possible action to be taken for defensive player
     */
    fun filterActions(
            defendingPlayer: Player,
            defendingPlayerCardsInHand: List<Card>,
            playingTable: PlayingTable
    ): List<ActionGame> {
        val availableActions = mutableListOf<ActionGame>()
        if (playingTable.cards.isEmpty()) {
            return emptyList()
        }
        availableActions.add(ActionTakeAllCards(actionIssuer = defendingPlayer))
        val firstUndefendedCardOnTable = playingTable.firstUndefendedCardOnTable()
        if (firstUndefendedCardOnTable != null) {
            val defendableActions = filterDefendableCardsAgainst(
                    firstUndefendedCardOnTable,
                    defendingPlayerCardsInHand
            ).map { ActionThrowInCard(defendingPlayer, it) }
            availableActions.addAll(defendableActions)
        }
        return availableActions
    }

    /**
     * Filters out only possible higher cards pitted agains [attackingCard]
     */
    fun filterDefendableCardsAgainst(
            attackingCard: Card,
            defendableCards: List<Card>
    ): List<Card> {
        val regularDefendableCards = defendableCards
                .filter { !it.isTrump }
                .filter { attackingCard.suite == it.suite }
                .filter { attackingCard < it }
        val trumpDefendableCards = defendableCards
                .filter { it.isTrump }
                .filter { attackingCard < it }
        return regularDefendableCards.plus(trumpDefendableCards)
    }

}