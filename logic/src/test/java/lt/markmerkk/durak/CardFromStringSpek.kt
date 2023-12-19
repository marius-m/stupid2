package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object CardFromStringSpek: Spek({
    val allCardValues = CardSuite.values().toList()
            .combine(CardRank.values().toList())
            .map { (suite, rank) -> Card(suite, rank) }

    describe("card is translated properly") {
        context("invalid card") {
            val resultCard = Card.fromString("invalid_card")

            it("should not parse out card") {
                assertThat(resultCard).isNull()
            }
        }

        allCardValues.forEach { card ->
            context("valid $card translation") {
                val translatedCard = Card.fromString(card.valueAsString())
                it("should translate to $card properly") {
                    assertThat(translatedCard).isEqualTo(card)
                }
            }
        }

    }
})

