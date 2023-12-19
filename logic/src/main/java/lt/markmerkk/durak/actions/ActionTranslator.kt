package lt.markmerkk.durak.actions

interface ActionTranslator {
    fun translateAction(
            actionAsString: String
    ): Action
}