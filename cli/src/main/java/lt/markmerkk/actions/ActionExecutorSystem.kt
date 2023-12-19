package lt.markmerkk.actions

import lt.markmerkk.Consts
import lt.markmerkk.actions.system.ActionSystem
import lt.markmerkk.actions.system.ActionSystemHelp
import lt.markmerkk.actions.system.ActionSystemQuit
import org.slf4j.LoggerFactory

class ActionExecutorSystem {
    fun execute(action: ActionSystem) {
        return when (action) {
            is ActionSystemQuit -> {
                if (action.actionIssuer != null) {
                    logger.info("${action.actionIssuer.name} has quit the game!\n")
                } else {
                    logger.info("Exit was issued!\n")
                }
                System.exit(0)
            }
            is ActionSystemHelp -> logger.info(action.helpMessage)
            else -> {
                logger.warn("Cannot figure action\n")
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }

}