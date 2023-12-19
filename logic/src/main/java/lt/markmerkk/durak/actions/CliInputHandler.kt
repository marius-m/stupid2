package lt.markmerkk.durak.actions

import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.system.ActionIllegalMultipleActions

class CliInputHandler(
        private val actionTranslators: List<ActionTranslator>
) {
    fun handleInput(
            inputAsString: String
    ): Action {
        val possibleActions = actionTranslators.map { it.translateAction(inputAsString) }
                .filter { it !is ActionIllegalCannotTranslate }
        if (possibleActions.size > 1) {
            return ActionIllegalMultipleActions(inputString = inputAsString, translatedActions = possibleActions)
        }
        if (possibleActions.isEmpty()) {
            return ActionIllegalCannotTranslate(inputAsString)
        }
        return possibleActions.first()
    }
}