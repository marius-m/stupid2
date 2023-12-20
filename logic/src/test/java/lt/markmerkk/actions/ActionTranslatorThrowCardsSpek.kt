package lt.markmerkk.actions

import com.google.common.truth.Truth.assertThat
import io.kotest.core.spec.style.DescribeSpec
import lt.markmerkk.durak.*
import lt.markmerkk.durak.actions.ActionTranslatorThrowCards
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.ActionThrowInCard

class ActionTranslatorThrowCardsSpek : DescribeSpec({

    describe("translator has valid players") {
        val player1 = Player(name = "Marius")
        val player2 = Player(name = "Enrika")
        val translatorValidPlayers = ActionTranslatorThrowCards(players = listOf(player1, player2))
        val allCardValues = CardSuite.values().toList()
                .combine(CardRank.values().toList())
                .map { (suite, rank) -> Card(suite, rank) }
        allCardValues.forEach { card ->
            context("valid throw $card action") {
                val resultAction = translatorValidPlayers.translateAction("Marius throw ${card.valueAsString()}")

                it("should be valid throw command") {
                    assertThat(resultAction).isEqualTo(ActionThrowInCard(actionIssuer = player1, thrownCard = card))
                }
            }
        }

        context("unfinished action") {
            val resultAction = translatorValidPlayers.translateAction("Marius throw ")

            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius throw "))
            }
        }

    }

    describe("translator no players") {
        val translatorValidPlayers = ActionTranslatorThrowCards(players = emptyList())

        context("valid action") {
            val resultAction = translatorValidPlayers.translateAction("Marius throw D9")

            it("should be invalid command") {
                assertThat(resultAction).isEqualTo(ActionIllegalCannotTranslate(inputAsString = "Marius throw D9"))
            }
        }
    }

})

