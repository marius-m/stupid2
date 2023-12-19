package lt.markmerkk.durak

import io.kotest.core.spec.style.DescribeSpec
import org.assertj.core.api.Assertions.assertThat

class PlayingTableFilterRanksOnTableSpek: DescribeSpec({
    describe("filter works properly") {
        context("empty table") {
            val resultRanks = PlayingTable(
                    cards = emptyList()
            ).filterRanksOnTable()

            it("nothing to filter") {
                assertThat(resultRanks).isEmpty()
            }
        }

        context("one card on table") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.ACE),
                                    defendingCard = null
                            )
                    )
            ).filterRanksOnTable()

            it("has valid ranks") {
                assertThat(resultRanks).containsExactlyInAnyOrder(CardRank.ACE)
            }
        }

        context("one defended card on table") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.KING),
                                    defendingCard = Card(CardSuite.SPADE, CardRank.ACE)
                            )
                    )
            ).filterRanksOnTable()

            it("has valid ranks") {
                assertThat(resultRanks).containsExactlyInAnyOrder(CardRank.ACE, CardRank.KING)
            }
        }


        context("more than one defended card on table") {
            val resultRanks = PlayingTable(
                    cards = listOf(
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.SPADE, CardRank.JACK),
                                    defendingCard = Card(CardSuite.SPADE, CardRank.KING)
                            ),
                            PlayingCardPair(
                                    attackingCard = Card(CardSuite.DIAMOND, CardRank.KING),
                                    defendingCard = Card(CardSuite.DIAMOND, CardRank.ACE)
                            )
                    )
            ).filterRanksOnTable()

            it("has valid ranks") {
                assertThat(resultRanks).containsExactlyInAnyOrder(
                        CardRank.ACE,
                        CardRank.KING,
                        CardRank.JACK
                )
            }
        }
    }
})

