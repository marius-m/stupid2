package lt.markmerkk.durak

import lt.markmerkk.actions.ActionTranslatorQuit
import lt.markmerkk.actions.system.ActionSystemQuit
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object ActionTranslatorQuitSpek: Spek({
    val player1 = Player(name = "Marius")
    val player2 = Player(name = "Enrika")
    val players = listOf(player1, player2)

    describe("translator has valid players") {
        val translatorValidPlayers = ActionTranslatorQuit(players = players)

        context("system action quit with incorrect player") {
            val resultAction = translatorValidPlayers.translateAction("invalid_player quit")

            it("invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "invalid_player quit"))
            }
        }

        context("system action quit with correct player") {
            val resultAction = translatorValidPlayers.translateAction("Marius quit")

            it("should create quit action") {
                assertThat(resultAction).isEqualTo(ActionSystemQuit(actionIssuer = player1))
            }
        }
    }

    describe("translator has no players") {
        val translatorNoPlayers = ActionTranslatorQuit(players = emptyList())

        context("invalid command") {
            val resultAction = translatorNoPlayers.translateAction("invalid_action")

            it("should return empty action") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate("invalid_action"))
            }
        }

        context("system action quit") {
            val resultAction = translatorNoPlayers.translateAction("quit")

            it("should create quit action") {
                assertThat(resultAction).isEqualTo(ActionSystemQuit())
            }
        }
    }
})

