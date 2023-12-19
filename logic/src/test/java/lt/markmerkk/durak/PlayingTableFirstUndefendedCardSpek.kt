package lt.markmerkk.durak

import io.kotest.core.spec.style.DescribeSpec
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.DIAMOND
import lt.markmerkk.durak.CardSuite.SPADE
import org.assertj.core.api.Assertions.assertThat

class PlayingTableFirstUndefendedCardSpek: DescribeSpec({

    val playingTable = PlayingTable(emptyList())

    describe("filter properly") {
        context("empty table") {
            playingTable.cards = emptyList()

            val resultRanks = playingTable.firstUndefendedCardOnTable()
            it("no first undefended card") {
                assertThat(resultRanks).isNull()
            }
        }
        context("only first undefended card") {
            playingTable.cards = listOf(PlayingCardPair(
                    attackingCard = Card(SPADE, TEN),
                    defendingCard = null
            ))
            val resultRanks = playingTable.firstUndefendedCardOnTable()

            it("valid selection") {
                assertThat(resultRanks).isEqualTo(Card(SPADE, TEN))
            }
        }
        context("first defended pair") {
            playingTable.cards = listOf(PlayingCardPair(
                    attackingCard = Card(SPADE, TEN),
                    defendingCard = Card(SPADE, JACK)
            ))
            val resultRanks = playingTable.firstUndefendedCardOnTable()

            it("no card selected") {
                assertThat(resultRanks).isNull()
            }
        }
        context("multiple play cards, two undefended") {
            playingTable.cards = listOf(
                    PlayingCardPair(
                            attackingCard = Card(SPADE, TEN),
                            defendingCard = null
                    ),
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, TEN),
                            defendingCard = null
                    )
            )
            val resultRanks = playingTable.firstUndefendedCardOnTable()

            it("valid selection") {
                assertThat(resultRanks).isEqualTo(Card(SPADE, TEN))
            }
        }
        context("multiple play cards, second undefended") {
            playingTable.cards = listOf(
                    PlayingCardPair(
                            attackingCard = Card(SPADE, TEN),
                            defendingCard = Card(DIAMOND, QUEEN)
                    ),
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, TEN),
                            defendingCard = null
                    )
            )
            val resultRanks = playingTable.firstUndefendedCardOnTable()

            it("valid selection") {
                assertThat(resultRanks).isEqualTo(Card(DIAMOND, TEN))
            }
        }
    }
})

