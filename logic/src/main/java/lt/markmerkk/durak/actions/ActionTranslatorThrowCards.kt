package lt.markmerkk.durak.actions

import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.Card
import lt.markmerkk.durak.Player
import java.util.regex.Pattern

/**
 * Responsible for parsing card throwing actions
 */
class ActionTranslatorThrowCards(
        private val players: List<Player> = emptyList()
) : ActionTranslator {

    private val playerPatterns: Map<Player, Pattern> = players
            .map { Pair(it, Pattern.compile("(${it.name}) throw (${Card.regexPattern})")) }
            .toMap()

    override fun translateAction(actionAsString: String): Action {
        playerPatterns.forEach { (player, pattern) ->
            val matcher = pattern.matcher(actionAsString)
            if (matcher.find()) {
                val cardAsString = matcher.group(2)
                val card = Card.fromString(cardAsString)
                if (card == null) {
                    return ActionIllegalCannotTranslate(
                            inputAsString = actionAsString,
                            detailDescription = "Cannot translate card from $cardAsString"
                    )
                }
                return ActionThrowInCard(actionIssuer = player, thrownCard = card)
            }
        }
        return ActionIllegalCannotTranslate(actionAsString)
    }

}