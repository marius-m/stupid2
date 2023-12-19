package lt.markmerkk.durak

import lt.markmerkk.Mocks
import org.junit.jupiter.api.BeforeEach
import org.mockito.MockitoAnnotations

abstract class AbsTurnsManagerTest {

    val player1: Player = Mocks.createPlayer("player_1")
    val player2: Player = Mocks.createPlayer("player_2")
    val player3: Player = Mocks.createPlayer("player_3")
    val player4: Player = Mocks.createPlayer("player_4")
    lateinit var turnsManager2Player: TurnsManager
    lateinit var turnsManager3Player: TurnsManager
    lateinit var turnsManager4Player: TurnsManager

    @BeforeEach
    open fun setUp() {
        MockitoAnnotations.initMocks(this)
        turnsManager2Player = TurnsManager(
                listOf(
                        player1,
                        player2
                )
        )
        turnsManager3Player = TurnsManager(
                listOf(
                        player1,
                        player2,
                        player3
                )
        )
        turnsManager4Player = TurnsManager(
                listOf(
                        player1,
                        player2,
                        player3,
                        player4
                )
        )
    }
}