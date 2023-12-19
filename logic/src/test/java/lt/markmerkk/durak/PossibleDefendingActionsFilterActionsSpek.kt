package lt.markmerkk.durak

import lt.markmerkk.Mocks
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import lt.markmerkk.durak.actions.ActionTakeAllCards
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleDefendingActionsFilter
import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object PossibleDefendingActionsFilterActionsSpek : Spek({
    val possibleDefendingActionsFilter = PossibleDefendingActionsFilter()
    val defendingPlayer = Player(name = "Marius")

    describe("no cards at the table") {
        context("nothing to defend against") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, QUEEN),
                            Card(SPADE, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable()
            )

            // Assert
            it("nothing to defend against") {
                assertThat(resultActions).isEmpty()
            }
        }
    }

    describe("one undefended card on table") {
        context("valid options to defend") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, QUEEN),
                            Card(SPADE, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, TEN),
                                            defendingCard = null
                                    )
                            )
                    )
            )
            val resultThrownActions = resultActions.filterIsInstance(ActionThrowInCard::class.java)

            it("multiple variations to defend") {
                assertThat(resultThrownActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, ACE)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, KING)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, QUEEN)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, JACK))
                )
            }
        }

        context("multiple actions higher") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, QUEEN),
                                            defendingCard = null
                                    )
                            )
                    )
            )
            val resultThrownActions = resultActions.filterIsInstance(ActionThrowInCard::class.java)

            it("multiple variations to defend") {
                assertThat(resultThrownActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, ACE)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, KING))
                )
            }
        }

        context("multiple cards and trump card is higher") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, JACK),
                            Card(DIAMOND, TWO, isTrump = true)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, QUEEN),
                                            defendingCard = null
                                    )
                            )
                    )
            )
            val resultThrownActions = resultActions.filterIsInstance(ActionThrowInCard::class.java)

            it("multiple variations to defend") {
                assertThat(resultThrownActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, ACE)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, KING)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(DIAMOND, TWO, isTrump = true))
                )
            }
        }
        context("multiple cards and trump card is higher, on multiple undefended cards") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, JACK),
                            Card(DIAMOND, TWO, isTrump = true)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, QUEEN),
                                            defendingCard = null
                                    ),
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, NINE),
                                            defendingCard = null
                                    )
                            )
                    )
            )
            val resultThrownActions = resultActions.filterIsInstance(ActionThrowInCard::class.java)

            it("only first first undefended card can be thrown") {
                assertThat(resultThrownActions).containsExactlyInAnyOrder(
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, ACE)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(SPADE, KING)),
                        ActionThrowInCard(actionIssuer = defendingPlayer, thrownCard = Card(DIAMOND, TWO, isTrump = true))
                )
            }
        }
    }

    describe("logic is correct of taking all cards") {
        context("no cards at the table") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = emptyList()
                    )
            )
            it("cannot take all the cards"){
                assertThat(resultActions).doesNotContain(
                        ActionTakeAllCards(actionIssuer = defendingPlayer)
                )
            }
        }

        context("undefended card on table") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, KING),
                            Card(SPADE, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, QUEEN),
                                            defendingCard = null
                                    )
                            )
                    )
            )
            it("can take all cards"){
                assertThat(resultActions).contains(
                        ActionTakeAllCards(actionIssuer = defendingPlayer)
                )
            }
        }

        context("all defended cards") {
            val resultActions = possibleDefendingActionsFilter.filterActions(
                    defendingPlayer = defendingPlayer,
                    defendingPlayerCardsInHand = Mocks.createCards(
                            Card(SPADE, ACE),
                            Card(SPADE, JACK)
                    ),
                    playingTable = Mocks.createPlayingTable(
                            cardsOnTable = listOf(
                                    PlayingCardPair(
                                            attackingCard = Card(SPADE, QUEEN),
                                            defendingCard = Card(SPADE, KING)
                                    )
                            )
                    )
            )
            it("can take all cards"){
                assertThat(resultActions).contains(
                        ActionTakeAllCards(actionIssuer = defendingPlayer)
                )
            }
        }
    }
})

