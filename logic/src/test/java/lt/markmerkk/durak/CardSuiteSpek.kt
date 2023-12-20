package lt.markmerkk.durak

import com.google.common.truth.Truth.assertThat
import io.kotest.core.spec.style.DescribeSpec

class CardSuiteSpek: DescribeSpec({
    describe("card suite sorting") {
        context("suite with trump HEART") {
            val result = CardSuite.valuesWithFirstAsTrump(trumpSuite = CardSuite.HEART)
            it("should have first suite HEARD") {
                assertThat(result).containsExactly(
                    CardSuite.HEART,
                    CardSuite.SPADE,
                    CardSuite.DIAMOND,
                    CardSuite.CLUB,
                )
            }
        }

        context("regular suite order") {
            val result = CardSuite.values().toList()
            it("should have regular order") {
                assertThat(result).containsExactly(
                    CardSuite.SPADE,
                    CardSuite.HEART,
                    CardSuite.DIAMOND,
                    CardSuite.CLUB,
                )
            }
        }

        context("suite with trump CLUB") {
            val result = CardSuite.valuesWithFirstAsTrump(trumpSuite = CardSuite.CLUB)
            it("should have first suite CLUB") {
                assertThat(result).containsExactly(
                    CardSuite.CLUB,
                    CardSuite.SPADE,
                    CardSuite.HEART,
                    CardSuite.DIAMOND,
                )
            }
        }
    }
})

