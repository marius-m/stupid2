package lt.markmerkk.actions

import io.kotest.core.spec.style.DescribeSpec
import lt.markmerkk.durak.Player
import lt.markmerkk.durak.actions.CliInputHandler
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.system.ActionIllegalMultipleActions
import org.assertj.core.api.Assertions.assertThat

class CliInputHandlerSpek : DescribeSpec({
    val players = listOf(Player(name = "Marius"), Player(name = "Enrika"))
    describe("input handler works properly") {

        context("empty translator") {
            val inputHandler = CliInputHandler(actionTranslators = emptyList())
            it("should return empty action") {
                val resultAction = inputHandler.handleInput("valid_input")
                assertThat(resultAction).isInstanceOf(ActionIllegalCannotTranslate::class.java)
            }
        }

        context("proper translator convert") {
            val inputHandler = CliInputHandler(
                    actionTranslators = listOf(
                            FakeTranslatorValid(FakeAction1())
                    )
            )
            it("should return valid action") {
                val resultAction = inputHandler.handleInput("valid_input")
                assertThat(resultAction).isInstanceOf(FakeAction1::class.java)
            }
        }

        context("on multiple valid translations") {
            val inputHandler = CliInputHandler(
                    actionTranslators = listOf(
                            FakeTranslatorValid(FakeAction1()),
                            FakeTranslatorValid(FakeAction2())
                    )
            )
            it("should return as invalidly translated") {
                val resultAction = inputHandler.handleInput("valid_input")
                assertThat(resultAction).isInstanceOf(ActionIllegalMultipleActions::class.java)
            }
        }
    }
})

