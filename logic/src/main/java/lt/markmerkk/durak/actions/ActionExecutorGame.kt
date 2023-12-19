package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Consts
import lt.markmerkk.durak.Game
import lt.markmerkk.durak.Player
import org.slf4j.LoggerFactory

class ActionExecutorGame(
        private val players: List<Player>,
        private val game: Game
) {
    fun execute(action: ActionGame) {
        return when (action) {
            is ActionThrowInCard -> game.throwCard(action)
            is ActionTakeAllCards -> game.takeAll(action)
            is ActionFinishRound -> game.finishRound(action)
            else -> { logger.warn("Action cannot be defined") }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }

}