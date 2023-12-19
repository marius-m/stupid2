package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object CardFilterSameRankSpek: Spek({
    describe("filtering works properly") {
        context("no items in the filter") {
            val cardsToFilter: List<Card> = emptyList()
            val resultOfFilter = cardsToFilter.filterSameRank(CardRank.JACK)
            it("should have nothing to filter out") {
                assertThat(resultOfFilter).isEmpty()
            }
        }

        context("one filterable item") {
            val cardsToFilter: List<Card> = listOf(
                    Card(CardSuite.SPADE, CardRank.ACE),
                    Card(CardSuite.SPADE, CardRank.KING),
                    Card(CardSuite.SPADE, CardRank.QUEEN),
                    Card(CardSuite.SPADE, CardRank.JACK)
            )
            val resultOfFilter = cardsToFilter.filterSameRank(CardRank.JACK)
            it("should have nothing to filter out") {
                assertThat(resultOfFilter).containsExactly(Card(CardSuite.SPADE, CardRank.JACK))
            }
        }

        context("no filterable items") {
            val cardsToFilter: List<Card> = listOf(
                    Card(CardSuite.SPADE, CardRank.ACE),
                    Card(CardSuite.SPADE, CardRank.KING),
                    Card(CardSuite.SPADE, CardRank.QUEEN)
            )
            val resultOfFilter = cardsToFilter.filterSameRank(CardRank.JACK)
            it("should have nothing to filter out") {
                assertThat(resultOfFilter).isEmpty()
            }
        }
    }
})

