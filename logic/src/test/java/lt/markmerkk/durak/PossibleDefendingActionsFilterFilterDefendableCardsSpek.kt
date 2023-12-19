package lt.markmerkk.durak

import io.kotest.core.spec.style.DescribeSpec
import lt.markmerkk.durak.actions.PossibleDefendingActionsFilter
import org.assertj.core.api.Assertions.assertThat

class PossibleDefendingActionsFilterFilterDefendableCardsSpek : DescribeSpec({
    val possibleDefendingActionsFilter = PossibleDefendingActionsFilter()

    describe("regular cards") {
        context("valid several options") {
            val resultActions = possibleDefendingActionsFilter.filterDefendableCardsAgainst(
                    attackingCard = Card(CardSuite.HEART, CardRank.JACK),
                    defendableCards = listOf(
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.KING)
                    )
            )

            // Assert
            it("valid options") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        Card(CardSuite.HEART, CardRank.QUEEN),
                        Card(CardSuite.HEART, CardRank.KING)
                )
            }
        }

        context("no options valid") {
            val resultActions = possibleDefendingActionsFilter.filterDefendableCardsAgainst(
                    attackingCard = Card(CardSuite.HEART, CardRank.JACK),
                    defendableCards = listOf(
                            Card(CardSuite.HEART, CardRank.SIX),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.SEVEN)
                    )
            )

            // Assert
            it("no options") {
                assertThat(resultActions).isEmpty()
            }
        }

        context("different suite") {
            val resultActions = possibleDefendingActionsFilter.filterDefendableCardsAgainst(
                    attackingCard = Card(CardSuite.HEART, CardRank.JACK),
                    defendableCards = listOf(
                            Card(CardSuite.SPADE, CardRank.SIX),
                            Card(CardSuite.SPADE, CardRank.KING),
                            Card(CardSuite.SPADE, CardRank.ACE)
                    )
            )

            // Assert
            it("valid options") {
                assertThat(resultActions).isEmpty()
            }
        }
    }

    describe("trump in mix") {
        context("valid several options") {
            val resultActions = possibleDefendingActionsFilter.filterDefendableCardsAgainst(
                    attackingCard = Card(CardSuite.HEART, CardRank.JACK),
                    defendableCards = listOf(
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.KING),
                            Card(CardSuite.SPADE, CardRank.TWO, isTrump = true)
                    )
            )

            // Assert
            it("valid options") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        Card(CardSuite.HEART, CardRank.QUEEN),
                        Card(CardSuite.HEART, CardRank.KING),
                        Card(CardSuite.SPADE, CardRank.TWO, isTrump = true)
                )
            }
        }

        context("low trump against all regulars") {
            val resultActions = possibleDefendingActionsFilter.filterDefendableCardsAgainst(
                    attackingCard = Card(CardSuite.SPADE, CardRank.TWO, isTrump = true),
                    defendableCards = listOf(
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.HEART, CardRank.TEN),
                            Card(CardSuite.HEART, CardRank.KING)
                    )
            )

            // Assert
            it("no options") {
                assertThat(resultActions).isEmpty()
            }
        }

        context("higher trump") {
            val resultActions = possibleDefendingActionsFilter.filterDefendableCardsAgainst(
                    attackingCard = Card(CardSuite.HEART, CardRank.SIX, isTrump = true),
                    defendableCards = listOf(
                            Card(CardSuite.HEART, CardRank.QUEEN),
                            Card(CardSuite.SPADE, CardRank.SEVEN, isTrump = true),
                            Card(CardSuite.SPADE, CardRank.TWO, isTrump = true)
                    )
            )

            // Assert
            it("only trump") {
                assertThat(resultActions).containsExactlyInAnyOrder(
                        Card(CardSuite.SPADE, CardRank.SEVEN, isTrump = true)
                )
            }
        }
    }
})

