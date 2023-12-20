package lt.markmerkk.durak

import com.google.common.truth.Truth.assertThat
import io.kotest.core.spec.style.DescribeSpec
import java.util.*

class PlayerSpek: DescribeSpec({
    describe("sorting cards in hand by weight") {
        context("same suite, no trump cards") {
            val player = Player(
                name = "valid_player",
                cardsInHand = listOf(
                    Card(CardSuite.CLUB, CardRank.ACE),
                    Card(CardSuite.CLUB, CardRank.TWO),
                    Card(CardSuite.CLUB, CardRank.THREE),
                    Card(CardSuite.CLUB, CardRank.FOUR),
                    Card(CardSuite.CLUB, CardRank.FIVE),
                    Card(CardSuite.CLUB, CardRank.SIX),
                )
            )
            player.sortByWeight()
            val resultCards = player.cardsInHand()
            it("player should have refilled hand") {
                assertThat(resultCards).containsExactly(
                    Card(CardSuite.CLUB, CardRank.ACE),
                    Card(CardSuite.CLUB, CardRank.SIX),
                    Card(CardSuite.CLUB, CardRank.FIVE),
                    Card(CardSuite.CLUB, CardRank.FOUR),
                    Card(CardSuite.CLUB, CardRank.THREE),
                    Card(CardSuite.CLUB, CardRank.TWO),
                )
            }
        }

        context("same suite, has trump cards") {
            val player = Player(
                name = "valid_player",
                cardsInHand = listOf(
                    Card(CardSuite.CLUB, CardRank.ACE),
                    Card(CardSuite.CLUB, CardRank.TWO),
                    Card(CardSuite.CLUB, CardRank.THREE),
                    Card(CardSuite.CLUB, CardRank.FOUR, isTrump = true),
                    Card(CardSuite.CLUB, CardRank.FIVE),
                    Card(CardSuite.CLUB, CardRank.SIX),
                )
            )
            player.sortByWeight()
            val resultCards = player.cardsInHand()
            it("player should have refilled hand") {
                assertThat(resultCards).containsExactly(
                    Card(CardSuite.CLUB, CardRank.FOUR, isTrump = true),
                    Card(CardSuite.CLUB, CardRank.ACE),
                    Card(CardSuite.CLUB, CardRank.SIX),
                    Card(CardSuite.CLUB, CardRank.FIVE),
                    Card(CardSuite.CLUB, CardRank.THREE),
                    Card(CardSuite.CLUB, CardRank.TWO),
                )
            }
        }

        context("diff suite, no trump cards") {
            val player = Player(
                name = "valid_player",
                cardsInHand = listOf(
                    Card(CardSuite.CLUB, CardRank.ACE),
                    Card(CardSuite.CLUB, CardRank.TWO),
                    Card(CardSuite.SPADE, CardRank.THREE),
                    Card(CardSuite.CLUB, CardRank.FOUR),
                    Card(CardSuite.CLUB, CardRank.FIVE),
                    Card(CardSuite.SPADE, CardRank.SIX),
                )
            )
            player.sortByWeight()
            val resultCards = player.cardsInHand()
            it("player should have refilled hand") {
                assertThat(resultCards).containsExactly(
                    Card(CardSuite.SPADE, CardRank.SIX),
                    Card(CardSuite.SPADE, CardRank.THREE),
                    Card(CardSuite.CLUB, CardRank.ACE),
                    Card(CardSuite.CLUB, CardRank.FIVE),
                    Card(CardSuite.CLUB, CardRank.FOUR),
                    Card(CardSuite.CLUB, CardRank.TWO),
                )
            }
        }

        context("diff suite, has trump cards") {
            val player = Player(
                name = "valid_player",
                cardsInHand = listOf(
                    Card(CardSuite.CLUB, CardRank.ACE),
                    Card(CardSuite.CLUB, CardRank.TWO),
                    Card(CardSuite.SPADE, CardRank.THREE),
                    Card(CardSuite.CLUB, CardRank.FOUR),
                    Card(CardSuite.CLUB, CardRank.FIVE),
                    Card(CardSuite.SPADE, CardRank.SIX),
                    Card(CardSuite.HEART, CardRank.TWO, isTrump = true),
                    Card(CardSuite.HEART, CardRank.THREE, isTrump = true),
                )
            )
            player.sortByWeight()
            val resultCards = player.cardsInHand()
            it("player should have refilled hand") {
                assertThat(resultCards).containsExactly(
                    Card(CardSuite.HEART, CardRank.THREE, isTrump = true),
                    Card(CardSuite.HEART, CardRank.TWO, isTrump = true),
                    Card(CardSuite.SPADE, CardRank.SIX),
                    Card(CardSuite.SPADE, CardRank.THREE),
                    Card(CardSuite.CLUB, CardRank.ACE),
                    Card(CardSuite.CLUB, CardRank.FIVE),
                    Card(CardSuite.CLUB, CardRank.FOUR),
                    Card(CardSuite.CLUB, CardRank.TWO),
                )
            }
        }
    }
})

