package lt.markmerkk.durak

import com.nhaarman.mockito_kotlin.*
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import lt.markmerkk.durak.actions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GameFinishRoundTest {

    @Mock lateinit var turnsManager: TurnsManager
    @Mock lateinit var refillingDeck: RefillingDeck
    @Mock lateinit var playingTable: PlayingTable
    @Mock lateinit var attackingActionsFilter: PossibleAttackingActionsFilter
    @Mock lateinit var defendingActionsFilter: PossibleDefendingActionsFilter

    lateinit var game: Game

    private val playerAttacking = spy(Player(
            name = "Marius",
            cardsInHand = listOf(
                    Card(HEART, ACE),
                    Card(HEART, KING),
                    Card(HEART, QUEEN)
            )
    ))
    private val playerDefending = spy(Player(
            name = "Enrika",
            cardsInHand = listOf(
                    Card(SPADE, ACE),
                    Card(SPADE, KING),
                    Card(SPADE, QUEEN)
            )
    ))
    private val players = listOf(playerAttacking, playerDefending)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        game = Game(
                cards = Card.generateDeck(),
                players = players,
                turnsManager = turnsManager,
                refillingDeck = refillingDeck,
                playingTable = playingTable,
                attackingActionsFilter = attackingActionsFilter,
                defendingActionsFilter = defendingActionsFilter
        )
        doReturn(playerAttacking).whenever(turnsManager).attackingPlayer
        doReturn(playerDefending).whenever(turnsManager).defendingPlayer
        doNothing().whenever(playerAttacking).refill(any())
        doNothing().whenever(playerDefending).refill(any())
    }

    @Test
    fun attackingPlayer_finishRound() {
        // Assemble
        val action = ActionFinishRound(
                actionIssuer = playerDefending
        )
        doReturn(true).whenever(turnsManager).isAttacking(any())
        doReturn(listOf(action)).whenever(attackingActionsFilter).filterActions(any(), any(), any(), any())

        // Act
        game.finishRound(action)

        // Assert
        verify(playingTable).clearAllCards()
        verify(turnsManager).endRound()
        players.forEach { verify(it).refill(any()) }
    }

    @Test
    fun attackingPlayer_cannotPerformAction() {
        // Assemble
        val action = ActionFinishRound(
                actionIssuer = playerDefending
        )
        doReturn(true).whenever(turnsManager).isAttacking(any())
        doReturn(emptyList<ActionGame>()).whenever(attackingActionsFilter).filterActions(any(), any(), any(), any()) // cannot perform any action

        // Act
        game.finishRound(action)

        // Assert
        verify(playingTable, never()).clearAllCards()
        verify(turnsManager, never()).endRound()
        players.forEach { verify(it, never()).refill(any()) }
    }

    @Test
    fun notAttackingPlayer() {
        // Assemble
        val action = ActionFinishRound(
                actionIssuer = playerDefending
        )
        doReturn(false).whenever(turnsManager).isAttacking(any()) // not attacking player
        doReturn(listOf(action)).whenever(attackingActionsFilter).filterActions(any(), any(), any(), any())

        // Act
        game.finishRound(action)

        // Assert
        verify(playingTable, never()).clearAllCards()
        verify(turnsManager, never()).endRound()
        players.forEach { verify(it, never()).refill(any()) }
    }

}