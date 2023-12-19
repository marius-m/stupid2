package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*

object PlayerRefillSpek: Spek({
    describe("deck has enough cards") {
        lateinit var refillingDeck: RefillingDeck
        beforeEachTest {
            refillingDeck = RefillingDeck(
                    cards = ArrayDeque<Card>(
                            listOf<Card>(
                                    Card(CardSuite.SPADE, CardRank.ACE),
                                    Card(CardSuite.SPADE, CardRank.KING),
                                    Card(CardSuite.SPADE, CardRank.QUEEN),
                                    Card(CardSuite.SPADE, CardRank.JACK),
                                    Card(CardSuite.SPADE, CardRank.TEN),
                                    Card(CardSuite.SPADE, CardRank.NINE),
                                    Card(CardSuite.SPADE, CardRank.EIGHT)
                            )
                    )
            )
        }

        context("empty hand") {
            val player = Player(
                    name = "valid_player",
                    cardsInHand = emptyList()
            )
            player.refill(refillingDeck = refillingDeck)
            it("player should have refilled hand") {
                assertThat(player.cardsInHand().size).isEqualTo(6)
            }
            it("refilling deck have cards removed") {
                assertThat(refillingDeck.cards.size).isEqualTo(1)
            }
        }

        context("already have cards") {
            val player = Player(
                    name = "valid_player",
                    cardsInHand = listOf(
                            Card(CardSuite.CLUB, CardRank.ACE),
                            Card(CardSuite.CLUB, CardRank.KING),
                            Card(CardSuite.CLUB, CardRank.QUEEN)
                    )
            )
            player.refill(refillingDeck = refillingDeck)
            it("player should have refilled hand") {
                assertThat(player.cardsInHand().size).isEqualTo(6)
            }
            it("refilling deck have cards removed") {
                assertThat(refillingDeck.cards.size).isEqualTo(4)
            }
        }

        context("already have enough cards") {
            val player = Player(
                    name = "valid_player",
                    cardsInHand = listOf(
                            Card(CardSuite.CLUB, CardRank.ACE),
                            Card(CardSuite.CLUB, CardRank.KING),
                            Card(CardSuite.CLUB, CardRank.QUEEN),
                            Card(CardSuite.CLUB, CardRank.JACK),
                            Card(CardSuite.CLUB, CardRank.TEN),
                            Card(CardSuite.CLUB, CardRank.EIGHT)
                    )
            )
            player.refill(refillingDeck = refillingDeck)
            it("player should have refilled hand") {
                assertThat(player.cardsInHand().size).isEqualTo(6)
            }
            it("refilling deck have cards removed") {
                assertThat(refillingDeck.cards.size).isEqualTo(7)
            }
        }

        context("already have too many cards") {
            val player = Player(
                    name = "valid_player",
                    cardsInHand = listOf(
                            Card(CardSuite.CLUB, CardRank.ACE),
                            Card(CardSuite.CLUB, CardRank.KING),
                            Card(CardSuite.CLUB, CardRank.QUEEN),
                            Card(CardSuite.CLUB, CardRank.JACK),
                            Card(CardSuite.CLUB, CardRank.TEN),
                            Card(CardSuite.CLUB, CardRank.EIGHT),
                            Card(CardSuite.CLUB, CardRank.SEVEN),
                            Card(CardSuite.CLUB, CardRank.SIX),
                            Card(CardSuite.CLUB, CardRank.FIVE),
                            Card(CardSuite.CLUB, CardRank.FOUR)
                    )
            )
            player.refill(refillingDeck = refillingDeck)
            it("player should have refilled hand") {
                assertThat(player.cardsInHand().size).isEqualTo(10)
            }
            it("refilling deck have cards removed") {
                assertThat(refillingDeck.cards.size).isEqualTo(7)
            }
        }
    }

    describe("not enough cards in deck") {
        lateinit var refillingDeck: RefillingDeck
        beforeEachTest {
            refillingDeck = RefillingDeck(
                    cards = ArrayDeque<Card>(
                            listOf<Card>(
                                    Card(CardSuite.SPADE, CardRank.ACE),
                                    Card(CardSuite.SPADE, CardRank.KING),
                                    Card(CardSuite.SPADE, CardRank.QUEEN)
                            )
                    )
            )
        }

        context("player has no cards") {
            val player = Player(
                    name = "valid_player",
                    cardsInHand = emptyList()
            )
            player.refill(refillingDeck = refillingDeck)
            it("player should have refilled hand") {
                assertThat(player.cardsInHand().size).isEqualTo(3)
            }
            it("refilling deck have cards removed") {
                assertThat(refillingDeck.cards.size).isEqualTo(0)
            }
        }

        context("player has a few cards") {
            val player = Player(
                    name = "valid_player",
                    cardsInHand = listOf(
                            Card(CardSuite.CLUB, CardRank.ACE),
                            Card(CardSuite.CLUB, CardRank.KING),
                            Card(CardSuite.CLUB, CardRank.QUEEN)
                    )
            )
            player.refill(refillingDeck = refillingDeck)
            it("player should have refilled hand") {
                assertThat(player.cardsInHand().size).isEqualTo(6)
            }
            it("refilling deck have cards removed") {
                assertThat(refillingDeck.cards.size).isEqualTo(0)
            }
        }

        context("player has enough cards") {
            val player = Player(
                    name = "valid_player",
                    cardsInHand = listOf(
                            Card(CardSuite.CLUB, CardRank.ACE),
                            Card(CardSuite.CLUB, CardRank.KING),
                            Card(CardSuite.CLUB, CardRank.QUEEN),
                            Card(CardSuite.CLUB, CardRank.JACK),
                            Card(CardSuite.CLUB, CardRank.TEN),
                            Card(CardSuite.CLUB, CardRank.EIGHT)
                    )
            )
            player.refill(refillingDeck = refillingDeck)
            it("player should have refilled hand") {
                assertThat(player.cardsInHand().size).isEqualTo(6)
            }
            it("refilling deck have cards removed") {
                assertThat(refillingDeck.cards.size).isEqualTo(3)
            }
        }
    }

})

