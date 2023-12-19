package lt.markmerkk.actions.system

data class ActionSystemHelp(
        val helpMessage: String
) : ActionSystem {
    override val actionType: ActionSystemType = ActionSystemType.HELP
}