package lt.markmerkk.durak

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import io.mockk.verify
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import lt.markmerkk.durak.actions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameFinishRoundTest {

    @RelaxedMockK lateinit var turnsManager: TurnsManager
    @RelaxedMockK lateinit var refillingDeck: RefillingDeck
    @RelaxedMockK lateinit var playingTable: PlayingTable
    @RelaxedMockK lateinit var attackingActionsFilter: PossibleAttackingActionsFilter
    @RelaxedMockK lateinit var defendingActionsFilter: PossibleDefendingActionsFilter

    lateinit var game: Game

    private val playerAttacking = spyk(Player(
            name = "Marius",
            cardsInHand = listOf(
                    Card(HEART, ACE),
                    Card(HEART, KING),
                    Card(HEART, QUEEN)
            )
    ))
    private val playerDefending = spyk(Player(
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
        MockKAnnotations.init(this)
        game = Game(
                cards = Card.generateDeck(cardTypeTrump = CLUB),
                players = players,
                turnsManager = turnsManager,
                refillingDeck = refillingDeck,
                playingTable = playingTable,
                attackingActionsFilter = attackingActionsFilter,
                defendingActionsFilter = defendingActionsFilter
        )
        every { turnsManager.attackingPlayer } returns playerAttacking
        every { turnsManager.defendingPlayer } returns playerDefending
        every { playerAttacking.refill(any()) } returns Unit
        every { playerDefending.refill(any()) } returns Unit
    }

    @Test
    fun attackingPlayer_finishRound() {
        // Assemble
        val action = ActionFinishRound(
                actionIssuer = playerDefending
        )
        every { turnsManager.isAttacking(any()) } returns true
        every { attackingActionsFilter.filterActions(any(), any(), any(), any()) } returns listOf(action)

        // Act
        game.finishRound(action)

        // Assert
        verify { playingTable.clearAllCards() }
        verify { turnsManager.endRound() }
        players.forEach {
            verify { it.refill(any()) }
        }
    }

    @Test
    fun attackingPlayer_cannotPerformAction() {
        // Assemble
        val action = ActionFinishRound(
                actionIssuer = playerDefending
        )
        every { turnsManager.isAttacking(any()) } returns true
        every { attackingActionsFilter.filterActions(any(), any(), any(), any()) } returns emptyList() // cannot perform any action

        // Act
        game.finishRound(action)

        // Assert
        verify(exactly = 0) { playingTable.clearAllCards() }
        verify(exactly = 0) { turnsManager.endRound() }
        players.forEach {
            verify(exactly = 0) { it.refill(any()) }
        }
    }

    @Test
    fun notAttackingPlayer() {
        // Assemble
        val action = ActionFinishRound(
                actionIssuer = playerDefending
        )
        every { turnsManager.isAttacking(any()) } returns false // not attacking player
        every { attackingActionsFilter.filterActions(any(), any(), any(), any()) } returns listOf(action)

        // Act
        game.finishRound(action)

        // Assert
        verify(exactly = 0) { playingTable.clearAllCards() }
        verify(exactly = 0) { turnsManager.endRound() }
        players.forEach {
            verify(exactly = 0) { it.refill(any()) }
        }
    }

}