package lt.markmerkk.durak.actions

import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.Player
import java.util.regex.Pattern

/**
 * Responsible for parsing card throwing actions
 */
class ActionTranslatorFinishRound(
        private val players: List<Player> = emptyList()
) : ActionTranslator {

    private val playerPatterns: Map<Player, Pattern> = players
            .map { Pair(it, Pattern.compile("(${it.name}) finish round")) }
            .toMap()

    override fun translateAction(actionAsString: String): Action {
        playerPatterns.forEach { (player, pattern) ->
            val matcher = pattern.matcher(actionAsString)
            if (matcher.find()) {
                return ActionFinishRound(actionIssuer = player)
            }
        }
        return ActionIllegalCannotTranslate(actionAsString)
    }

}