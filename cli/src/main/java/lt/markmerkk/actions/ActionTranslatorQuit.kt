package lt.markmerkk.actions

import lt.markmerkk.actions.system.ActionSystemQuit
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.Action
import lt.markmerkk.durak.actions.ActionTranslator
import java.util.regex.Pattern

/**
 * Responsible for parsing player actions when trying to quit game
 */
class ActionTranslatorQuit(
        private val players: List<Player> = emptyList()
): ActionTranslator {

    private val playerPatterns: Map<Player, Pattern> = players
            .map { Pair(it, Pattern.compile("${it.name} quit"))  }
            .toMap()

    override fun translateAction(
            actionAsString: String
    ): Action {
        if (actionAsString == "quit") {
            return ActionSystemQuit(actionIssuer = null)
        }
        playerPatterns.forEach { (player, pattern) ->
            val matcher = pattern.matcher(actionAsString)
            if (matcher.find()) {
                return ActionSystemQuit(actionIssuer = player)
            }
        }
        return ActionIllegalCannotTranslate(actionAsString)
    }
}
