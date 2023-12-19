package lt.markmerkk.actions

import io.kotest.core.spec.style.DescribeSpec
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.ActionTranslatorTakeAll
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.ActionTakeAllCards
import org.assertj.core.api.Assertions.assertThat

class ActionTranslatorTakeAllSpek : DescribeSpec({

    describe("translator has valid players") {
        val player1 = Player(name = "Marius")
        val player2 = Player(name = "Enrika")
        val translatorValidPlayers = ActionTranslatorTakeAll(players = listOf(player1, player2))
        context("valid take all action") {
            val resultAction = translatorValidPlayers.translateAction("Marius take all")

            it("should be valid take all command") {
                assertThat(resultAction).isEqualTo(ActionTakeAllCards(actionIssuer = player1))
            }
        }

        context("unfinished action") {
            val resultAction = translatorValidPlayers.translateAction("Marius take")

            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius take"))
            }
        }
    }

    describe("translator no players") {
        val translatorValidPlayers = ActionTranslatorTakeAll(players = emptyList())

        context("valid action") {
            val resultAction = translatorValidPlayers.translateAction("Marius take all")

            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius take all"))
            }
        }
    }

})

