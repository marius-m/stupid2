package lt.markmerkk.durak.actions

import lt.markmerkk.durak.*

/**
 * Defines possible actions for attacking player
 */
class PossibleAttackingActionsFilter(
        private val playingTable: PlayingTable
) {

    /**
     * Wrapper for [filterActions] by using [ActionGame] instance
     */
    // todo is this really needed ?
    fun availableActions(
            actionGame: ActionGame,
            defensivePlayerCardSizeInHand: Int
    ): List<ActionGame> {
        return filterActions(
                actionGame.actionIssuer,
                actionGame.actionIssuer.cardsInHand(),
                playingTable,
                defensivePlayerCardSizeInHand
        )
    }

    /**
     * Filters all available actions
     */
    fun filterActions(
            attackingPlayer: Player,
            attackingPlayerCardsInHand: List<Card>,
            playingTable: PlayingTable,
            defensivePlayerCardSizeInHand: Int
    ): List<ActionGame> {
        val resultCards: MutableList<ActionGame> = mutableListOf()
        val throwInCardsActions = handleThrowInCardsActions(
                attackingPlayer,
                attackingPlayerCardsInHand,
                playingTable,
                defensivePlayerCardSizeInHand
        )
        resultCards.addAll(throwInCardsActions)
        resultCards.addAll(handleFinishRoundActions(attackingPlayer, playingTable))
        return resultCards.toList()
    }

    private fun handleFinishRoundActions(
            attackingPlayer: Player,
            playingTable: PlayingTable
    ): List<ActionGame> {
        if (playingTable.cards.isEmpty()) {
            return emptyList()
        }
        if (playingTable.undefendedCardsOnTable().isEmpty()) {
            return listOf(ActionFinishRound(actionIssuer = attackingPlayer))
        }
        return emptyList()
    }

    private fun handleThrowInCardsActions(
            attackingPlayer: Player,
            attackingPlayerCardsInHand: List<Card>,
            playingTable: PlayingTable,
            defensivePlayerCardSizeInHand: Int
    ): List<ActionGame> {
        if (playingTable.cards.isEmpty()) {
            return attackingPlayerCardsInHand.map { ActionThrowInCard(attackingPlayer, it) }
        }
        val undefendedCardsOnTable = playingTable.undefendedCardsOnTable()
        if (undefendedCardsOnTable.size >= defensivePlayerCardSizeInHand) {
            return emptyList()
        }
        if (playingTable.cards.size == Consts.MAX_PAIRS_ON_TABLE) {
            return emptyList()
        }
        return playingTable
                .filterRanksOnTable()
                .flatMap { attackingPlayerCardsInHand.filterSameRank(it) }
                .map { ActionThrowInCard(attackingPlayer, it) }
    }

}