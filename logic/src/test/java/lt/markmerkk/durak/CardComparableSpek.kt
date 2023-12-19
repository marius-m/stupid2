package lt.markmerkk.durak

import io.kotest.core.spec.style.DescribeSpec
import org.assertj.core.api.Assertions.assertThat

class CardComparableSpek: DescribeSpec({
    describe("non trump card compare") {
        context("lower card") {
            val compareResult = Card(CardSuite.HEART, CardRank.KING).compareTo(Card(CardSuite.HEART, CardRank.ACE))
            it("king is lower than ace") {
                assertThat(compareResult).isEqualTo(-1)
            }
        }

        context("equal card") {
            val compareResult = Card(CardSuite.HEART, CardRank.KING).compareTo(Card(CardSuite.HEART, CardRank.KING))
            it("king is equal to king") {
                assertThat(compareResult).isEqualTo(0)
            }
        }

        context("higher card") {
            val compareResult = Card(CardSuite.HEART, CardRank.ACE).compareTo(Card(CardSuite.HEART, CardRank.KING))
            it("ace is higher than king") {
                assertThat(compareResult).isEqualTo(1)
            }
        }
    }

    describe("regular with trump compare") {
        context("lower card") {
            val compareResult = Card(CardSuite.HEART, CardRank.KING).compareTo(Card(CardSuite.HEART, CardRank.ACE, isTrump = true))
            it("trump ace is higher than regular king") {
                assertThat(compareResult).isEqualTo(-1)
            }
        }

        context("equal card") {
            val compareResult = Card(CardSuite.HEART, CardRank.KING).compareTo(Card(CardSuite.HEART, CardRank.KING, isTrump = true))
            it("trump king is higher than regular king") {
                assertThat(compareResult).isEqualTo(-1)
            }
        }

        context("higher card") {
            val compareResult = Card(CardSuite.HEART, CardRank.ACE).compareTo(Card(CardSuite.HEART, CardRank.KING, isTrump = true))
            it("trump king is higher than regular ace") {
                assertThat(compareResult).isEqualTo(-1)
            }
        }
    }

    describe("trump with trump compare") {
        context("lower card") {
            val compareResult = Card(CardSuite.HEART, CardRank.KING, isTrump = true).compareTo(Card(CardSuite.HEART, CardRank.ACE, isTrump = true))
            it("trump ace is higher than trump king") {
                assertThat(compareResult).isEqualTo(-1)
            }
        }

        context("equal card") {
            val compareResult = Card(CardSuite.HEART, CardRank.KING, isTrump = true).compareTo(Card(CardSuite.HEART, CardRank.KING, isTrump = true))
            it("trump king is equal to trump king") {
                assertThat(compareResult).isEqualTo(0)
            }
        }

        context("higher card") {
            val compareResult = Card(CardSuite.HEART, CardRank.ACE, isTrump = true).compareTo(Card(CardSuite.HEART, CardRank.KING, isTrump = true))
            it("trump king is lower than trump ace") {
                assertThat(compareResult).isEqualTo(1)
            }
        }
    }
})

