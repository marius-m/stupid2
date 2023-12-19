package lt.markmerkk.durak

import lt.markmerkk.durak.actions.*
import org.slf4j.LoggerFactory
import java.util.*

class Game(
        cards: List<Card>,
        val cardSuiteTrump: CardSuite = cards.trumpOrThrow(),
        val players: List<Player>,
        val turnsManager: TurnsManager,
        val refillingDeck: RefillingDeck = RefillingDeck(cards = ArrayDeque<Card>(cards.toMutableList().shuffled())),
        val playingTable: PlayingTable = PlayingTable(cards = emptyList()),
        private val attackingActionsFilter: PossibleAttackingActionsFilter = PossibleAttackingActionsFilter(playingTable),
        private val defendingActionsFilter: PossibleDefendingActionsFilter = PossibleDefendingActionsFilter()
) {

    var isGameOver = false

    fun resetGame() {
        players.forEach { it.reset() }
    }

    fun refillPlayerCards() {
        players.forEach { player ->
            player.refill(refillingDeck)
            player.sortByWeight()
        }
    }

    fun availablePlayerActions(player: Player): List<ActionGame> {
        return when {
            turnsManager.isAttacking(player) -> attackingActionsFilter.filterActions(
                    attackingPlayer = player,
                    attackingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable,
                    defensivePlayerCardSizeInHand = turnsManager.defendingPlayer.cardsInHandSize()
            )
            turnsManager.isDefending(player) -> defendingActionsFilter.filterActions(
                    defendingPlayer = player,
                    defendingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable
            )
            else -> emptyList()
        }
    }

    fun finishRound(actionFinishRound: ActionFinishRound) {
        val player = actionFinishRound.actionIssuer
        if (!turnsManager.isAttacking(player)) {
            logger.info("${player.name} cannot finish round!\n")
            logger.info(printAvailablePlayerActions(player))
            return
        }
        val availableActions = attackingActionsFilter.filterActions(
                attackingPlayer = player,
                attackingPlayerCardsInHand = player.cardsInHand(),
                playingTable = playingTable,
                defensivePlayerCardSizeInHand = turnsManager.defendingPlayer.cardsInHandSize()
        )
        if (availableActions.contains(actionFinishRound)) {
            playingTable.clearAllCards()
            players.forEach { it.refill(refillingDeck) } // todo: incorrect, it should refill all players starting from finished player
            turnsManager.endRound()
            logger.info("${player.name} finished!\n")
            logger.info("Round ended. ${turnsManager.attackingPlayer.name} is now attacking!\n")
        } else {
            logger.info("${player.name} cannot finish round!\n")
            logger.info(printAvailablePlayerActions(player))
        }
    }

    fun takeAll(actionTakeAllCards: ActionTakeAllCards) {
        val player = actionTakeAllCards.actionIssuer
        if (!turnsManager.isDefending(player)) {
            logger.info("${player.name} cannot take all cards!\n")
            logger.info(printAvailablePlayerActions(player))
            return
        }
        val availableActions = defendingActionsFilter.filterActions(
                defendingPlayer = player,
                defendingPlayerCardsInHand = player.cardsInHand(),
                playingTable = playingTable
        )
        if (availableActions.contains(actionTakeAllCards)) {
            player.addCards(playingTable.allCards())
            playingTable.clearAllCards()
            players.forEach { it.refill(refillingDeck) } // todo: incorrect, it should refill all players starting from finished player
            logger.info("${player.name} takes all cards\n")
            logger.info("Round ended. ${turnsManager.attackingPlayer.name} is now attacking!\n")
        } else {
            logger.info("${player.name} cannot take all cards!\n")
            logger.info(printAvailablePlayerActions(player))
        }
    }

    fun throwCard(actionThrowInCard: ActionThrowInCard) {
        val player = actionThrowInCard.actionIssuer
        if (turnsManager.isAttacking(player)) {
            val availableActions = attackingActionsFilter.filterActions(
                    attackingPlayer = player,
                    attackingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable,
                    defensivePlayerCardSizeInHand = turnsManager.defendingPlayer.cardsInHandSize()
            )
            if (availableActions.isNotEmpty()) {
                try {
                    playingTable.attack(actionThrowInCard.thrownCard)
                    player.removeCard(actionThrowInCard.thrownCard)
                    logger.info("${player.name} throws ${actionThrowInCard.thrownCard}\n")
                } catch (e: IllegalArgumentException) {
                    logger.warn("Cannot perform this operation when trying to attack", e)
                }
            } else {
                logger.info("${player.name} cannot throw in ${actionThrowInCard.thrownCard}!\n")
                logger.info(printAvailablePlayerActions(player))
            }
        } else if (turnsManager.isDefending(player)) {
            val availableActions = defendingActionsFilter.filterActions(
                    defendingPlayer = player,
                    defendingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable
            )
            if (availableActions.isNotEmpty()) {
                try {
                    playingTable.defend(actionThrowInCard.thrownCard)
                    player.removeCard(actionThrowInCard.thrownCard)
                    logger.info("${player.name} throws ${actionThrowInCard.thrownCard}\n")
                } catch (e: IllegalArgumentException) {
                    logger.warn("Illegal action performed trying to defend", e)
                }
            } else {
                logger.info("${player.name} cannot throw in ${actionThrowInCard.thrownCard}!\n")
                logger.info(printAvailablePlayerActions(player))
            }
        } else {
            TODO("Unsupported operation")
        }
    }

    //region Convenience

    /**
     * Forms available actions for a player
     */
    fun printAvailablePlayerActions(
            player: Player
    ): String {
        val availableActions = when {
            turnsManager.isAttacking(player) -> attackingActionsFilter.filterActions(
                    attackingPlayer = player,
                    attackingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable,
                    defensivePlayerCardSizeInHand = turnsManager.defendingPlayer.cardsInHandSize()
            )
            turnsManager.isDefending(player) -> defendingActionsFilter.filterActions(
                    defendingPlayer = player,
                    defendingPlayerCardsInHand = player.cardsInHand(),
                    playingTable = playingTable
            )
            else -> emptyList()
        }
        if (availableActions.isEmpty()) {
            return "${player.name} cannot perform any actions at this moment!\n"
        }
        val availableActionDescriptions = availableActions
                .map { it.actionTrigger }
                .joinToString(separator = ",\n\t", prefix = "\t")
        return "${player.name} can do these actions:\n$availableActionDescriptions\n"
    }

    //endregion

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }

}