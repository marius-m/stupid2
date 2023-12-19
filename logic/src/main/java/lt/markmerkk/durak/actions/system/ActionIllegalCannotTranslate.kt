package lt.markmerkk.durak.actions.system

import lt.markmerkk.durak.actions.Action

/**
 * When action cannot be executed
 */
data class ActionIllegalCannotTranslate(
        private val inputAsString: String = "",
        private val detailDescription: String = ""
) : Action