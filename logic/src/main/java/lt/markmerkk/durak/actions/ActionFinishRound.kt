package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Player

/**
 * Action to finish round as the defence was successful
 */
data class ActionFinishRound(
        override val actionIssuer: Player
) : ActionGame {
    override val actionValue: String = ""
    override val actionType: ActionGameType = ActionGameType.FINISH_ROUND
    override val actionTrigger: String = "${actionIssuer.name} finish round"
    override val actionUseDescription: String = "Finish round"
}