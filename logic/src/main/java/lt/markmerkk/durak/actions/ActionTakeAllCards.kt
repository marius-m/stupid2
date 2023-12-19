package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Player

/**
 * Action to take all cards in, as the defence was not able to complete
 */
data class ActionTakeAllCards(
        override val actionIssuer: Player
) : ActionGame {
    override val actionValue: String = ""
    override val actionType: ActionGameType = ActionGameType.TAKE_ALL
    override val actionTrigger: String = "${actionIssuer.name} take all"
    override val actionUseDescription: String = "Take all"
}