package lt.markmerkk.actions

import lt.markmerkk.actions.system.ActionSystemHelp
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.Game
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.Action
import lt.markmerkk.durak.actions.ActionTranslator
import java.util.regex.Pattern

/**
 * Responsible for parsing card throwing actions
 */
class ActionTranslatorHelp(
        private val players: List<Player> = emptyList(),
        private val game: Game
) : ActionTranslator {

    private val playerPatterns: Map<Player, Pattern> = players
            .map { Pair(it, Pattern.compile("${it.name} \\?")) }
            .toMap()

    override fun translateAction(actionAsString: String): Action {
        if (actionAsString == "?") {
            val sb = StringBuilder()
            players.forEach { sb.append(game.printAvailablePlayerActions(it)) }
            return ActionSystemHelp(helpMessage = sb.toString())
        }
        playerPatterns.forEach { (player, pattern) ->
            val matcher = pattern.matcher(actionAsString)
            if (matcher.find()) {
                return ActionSystemHelp(game.printAvailablePlayerActions(player))
            }
        }
        return ActionIllegalCannotTranslate(actionAsString)
    }

}