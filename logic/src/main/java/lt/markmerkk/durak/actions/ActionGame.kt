package lt.markmerkk.durak.actions

import lt.markmerkk.durak.Player

/**
 * Defines an action to be executed by the game interface
 */
interface ActionGame : Action {
    /**
     * Type of action to trigger
     */
    val actionType: ActionGameType

    /**
     * Bound value with the type
     * For ex.: bound card
     */
    val actionValue: String

    /**
     * Who triggers the action
     */
    val actionIssuer: Player

    /**
     * Action to trigger event
     */
    val actionTrigger: String

    /**
     * Description how to use the action
     */
    val actionUseDescription: String
}

enum class ActionGameType {
    THROW,
    FINISH_ROUND,
    TAKE_ALL,
    ;
}