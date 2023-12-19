package lt.markmerkk.durak.actions.system

import lt.markmerkk.durak.actions.Action

/**
 * Defines an action that was translated incorrectly.
 * Input was parsed into multiple actions, not sure which path should it take.
 */
data class ActionIllegalMultipleActions(
        private val inputString: String = "",
        private val detailDescription: String = "",
        private val translatedActions: List<Action> = emptyList()
) : Action