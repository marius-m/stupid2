package lt.markmerkk.actions.system

import io.kotest.core.spec.style.DescribeSpec
import lt.markmerkk.Mocks
import lt.markmerkk.durak.actions.ActionTakeAllCards
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import org.assertj.core.api.Assertions.assertThat

class ActionIllegalCannotTranslateTest: DescribeSpec({
    describe("object equality works properly") {

        context("same instance") {
            val action1 = ActionIllegalCannotTranslate()
            val action2 = ActionIllegalCannotTranslate()
            val resultEquality = action1.equals(action2)
            it("should be equal") {
                assertThat(resultEquality).isTrue()
            }
        }

        context("different inputs") {
            val action1 = ActionIllegalCannotTranslate(inputAsString = "input1")
            val action2 = ActionIllegalCannotTranslate(inputAsString = "input2")
            val resultEquality = action1.equals(action2)
            it("should be equal") {
                assertThat(resultEquality).isFalse()
            }
        }

        context("different actions") {
            val action1 = ActionIllegalCannotTranslate(inputAsString = "input1")
            val action2 = ActionTakeAllCards(actionIssuer = Mocks.createPlayer())
            val resultEquality = action1.equals(action2)
            it("should not be equal") {
                assertThat(resultEquality).isFalse()
            }
        }

        context("no action") {
            val action1: ActionIllegalCannotTranslate = ActionIllegalCannotTranslate(inputAsString = "input1")
            val action2: ActionIllegalCannotTranslate? = null
            val resultEquality = action1.equals(action2)
            it("should not be equal") {
                assertThat(resultEquality).isFalse()
            }
        }
    }
})