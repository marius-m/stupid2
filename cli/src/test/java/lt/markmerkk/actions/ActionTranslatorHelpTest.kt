package lt.markmerkk.actions

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import lt.markmerkk.actions.system.ActionSystemHelp
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.Game
import lt.markmerkk.durak.Player
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ActionTranslatorHelpTest {

    @Mock lateinit var game: Game

    lateinit var actionTranslatorHelp: ActionTranslatorHelp

    private val playerName1 = "Marius"
    private val playerName2 = "Enrika"
    private val players = listOf(Player(playerName1), Player(playerName2))

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        actionTranslatorHelp = ActionTranslatorHelp(
                players,
                game
        )
        doReturn("actions for $playerName1").whenever(game).printAvailablePlayerActions(players[0])
        doReturn("actions for $playerName2").whenever(game).printAvailablePlayerActions(players[1])
    }

    @Test
    fun valid_generic() {
        // Assemble
        // Act
        val resultActions = actionTranslatorHelp.translateAction("?")

        // Assert
        assertThat(resultActions).isEqualTo(ActionSystemHelp(
                helpMessage = "actions for Mariusactions for Enrika"
        ))
    }

    @Test
    fun valid_specific() {
        // Assemble
        // Act
        val resultActions = actionTranslatorHelp.translateAction("$playerName2 ?")

        // Assert
        assertThat(resultActions).isEqualTo(ActionSystemHelp(
                helpMessage = "actions for $playerName2"
        ))
    }

    @Test
    fun invalidAction() {
        // Assemble
        // Act
        val resultActions = actionTranslatorHelp.translateAction("")

        // Assert
        assertThat(resultActions).isInstanceOf(ActionIllegalCannotTranslate::class.java)
    }

}