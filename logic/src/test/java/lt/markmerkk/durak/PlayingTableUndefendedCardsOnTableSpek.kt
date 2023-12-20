package lt.markmerkk.durak

import com.google.common.truth.Truth.assertThat
import io.kotest.core.spec.style.DescribeSpec

class PlayingTableUndefendedCardsOnTableSpek: DescribeSpec({
    describe("filter works properly") {
        context("empty table") {
            val resultRanks = PlayingTable(
                    cards = emptyList()
            ).undefendedCardsOnTable()

            it("nothing to filter") {
                assertThat(resultRanks).isEmpty()
            }
        }

        context("undefended cards") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.ACE),
                                    defendingCard = null
                            ),
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.KING),
                                    defendingCard = null
                            )
                    )
            ).undefendedCardsOnTable()

            it("filters undefended cards") {
                assertThat(resultRanks).containsExactly(
                        Card(CardSuite.SPADE, CardRank.ACE),
                        Card(CardSuite.SPADE, CardRank.KING)
                )
            }
        }

        context("has already defended cards") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.ACE),
                                    defendingCard = null
                            ),
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.KING),
                                    defendingCard = Card(CardSuite.DIAMOND, CardRank.NINE)
                            )
                    )
            ).undefendedCardsOnTable()

            it("filters only undefended cards") {
                assertThat(resultRanks).containsExactly(
                        Card(CardSuite.SPADE, CardRank.ACE)
                )
            }
        }
    }
})

