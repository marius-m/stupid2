package lt.markmerkk.actions

import io.kotest.core.spec.style.DescribeSpec
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.ActionTranslatorFinishRound
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.ActionFinishRound
import org.assertj.core.api.Assertions.assertThat

class ActionTranslatorFinishRoundSpek : DescribeSpec({

    describe("translator has valid players") {
        val player1 = Player(name = "Marius")
        val player2 = Player(name = "Enrika")
        val translatorValidPlayers = ActionTranslatorFinishRound(players = listOf(player1, player2))
        context("valid action") {
            val resultAction = translatorValidPlayers.translateAction("Marius finish round")
            it("should be valid command") {
                assertThat(resultAction).isEqualTo(ActionFinishRound(actionIssuer = player1))
            }
        }
    }

    describe("translator no players") {
        val translatorValidPlayers = ActionTranslatorFinishRound(players = emptyList())
        context("valid action") {
            val resultAction = translatorValidPlayers.translateAction("Marius take all")
            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius take all"))
            }
        }
    }

})

