package lt.markmerkk.actions.system

import lt.markmerkk.durak.actions.Action

/**
 * Action that must be executed by the system
 */
interface ActionSystem : Action {
    val actionType: ActionSystemType
}

enum class ActionSystemType {
    HELP,
    QUIT,
    ;
}

