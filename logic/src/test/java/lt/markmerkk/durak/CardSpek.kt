package lt.markmerkk.durak

import io.kotest.core.spec.style.DescribeSpec
import org.assertj.core.api.Assertions.assertThat
import kotlin.random.Random

class CardSpek: DescribeSpec({
    describe("deck initialized") {
        val cards = Card.generateDeck(cardTypeTrump = CardSuite.CLUB)

        context("init") {
            it("should have size 52") {
                assertThat(cards.size).isEqualTo(52)
            }

            val onlyClubs = cards.filter { it.suite == CardSuite.CLUB }
            it("should contain 13 clubs") {
                assertThat(onlyClubs.size).isEqualTo(13)
            }

            val onlyDiamonds = cards.filter { it.suite == CardSuite.DIAMOND }
            it("should contain 13 diamonds") {
                assertThat(onlyDiamonds.size).isEqualTo(13)
            }

            val onlyHearts = cards.filter { it.suite == CardSuite.HEART }
            it("should contain 13 hearts") {
                assertThat(onlyHearts.size).isEqualTo(13)
            }

            val onlySpades = cards.filter { it.suite == CardSuite.SPADE }
            it("should contain 13 spades") {
                assertThat(onlySpades.size).isEqualTo(13)
            }
        }

        context("trump card generation") {
            it("cards should be trump") {
                val trumpCards = cards
                    .filter { it.isTrump }
                assertThat(trumpCards.size).isEqualTo(13)
            }
        }
    }

    describe("small deck initialization") {
        val cards = Card.generateDeckSmall(cardTypeTrump = CardSuite.CLUB)

        context("init") {
            it("should have right size") {
                assertThat(cards.size).isEqualTo(24)
            }

            val onlyClubs = cards.filter { it.suite == CardSuite.CLUB }
            it("should contain 6 clubs") {
                assertThat(onlyClubs.size).isEqualTo(6)
            }

            val onlyDiamonds = cards.filter { it.suite == CardSuite.DIAMOND }
            it("should contain 6 diamonds") {
                assertThat(onlyDiamonds.size).isEqualTo(6)
            }

            val onlyHearts = cards.filter { it.suite == CardSuite.HEART }
            it("should contain 6 hearts") {
                assertThat(onlyHearts.size).isEqualTo(6)
            }

            val onlySpades = cards.filter { it.suite == CardSuite.SPADE }
            it("should contain 6 spades") {
                assertThat(onlySpades.size).isEqualTo(6)
            }
        }

        context("trump card generation") {
            it("cards should be trump") {
                val trumpCards = cards
                    .filter { it.isTrump }
                assertThat(trumpCards.size).isEqualTo(6)
            }
        }
    }

    describe("random suite generation") {
        context("generate suite with seed 1") {
            val random = Random(1234L)
            val resultSuite = Card.randomSuite(random)
            it("cards should be trump") {
                assertThat(resultSuite).isEqualTo(CardSuite.SPADE)
            }
        }

        context("generate suite with seed 2") {
            val random = Random(1235L)
            val resultSuite = Card.randomSuite(random)
            it("cards should be trump") {
                assertThat(resultSuite).isEqualTo(CardSuite.DIAMOND)
            }
        }
    }
})

