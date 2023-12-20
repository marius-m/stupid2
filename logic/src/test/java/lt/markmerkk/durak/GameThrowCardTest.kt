package lt.markmerkk.durak

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import io.mockk.verify
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import lt.markmerkk.durak.actions.ActionGame
import lt.markmerkk.durak.actions.ActionThrowInCard
import lt.markmerkk.durak.actions.PossibleAttackingActionsFilter
import lt.markmerkk.durak.actions.PossibleDefendingActionsFilter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameThrowCardTest {

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
    }

    @Test
    fun attack_validAction() {
        // Assemble
        val card = Card(HEART, KING)
        val action = ActionThrowInCard(
                actionIssuer = playerAttacking,
                thrownCard = card
        )
        every { turnsManager.isAttacking(any()) } returns true
        every { attackingActionsFilter.filterActions(any(), any(), any(), any()) } returns listOf(action)

        // Act
        game.throwCard(action)

        // Assert
        verify { playingTable.attack(card) }
        verify { playerAttacking.removeCard(card) }
    }

    @Test
    fun attack_invalid_action() {
        // Assemble
        val card = Card(HEART, KING)
        val action = ActionThrowInCard(
                actionIssuer = playerAttacking,
                thrownCard = card
        )
        every { turnsManager.isAttacking(any()) } returns true
        every { attackingActionsFilter.filterActions(any(), any(), any(), any()) } returns emptyList<ActionGame>() // Action cannot be performed

        // Act
        game.throwCard(action)

        // Assert
        verify(exactly = 0) { playingTable.attack(card) }
        verify(exactly = 0) { playerAttacking.removeCard(card) }
    }

    @Test
    fun attack_throws() {
        // Assemble
        val card = Card(HEART, KING)
        val action = ActionThrowInCard(
                actionIssuer = playerAttacking,
                thrownCard = card
        )
        every { turnsManager.isAttacking(any()) } returns true
        every { attackingActionsFilter.filterActions(any(), any(), any(), any()) } returns listOf(action)
        every { playingTable.attack(any()) } throws IllegalArgumentException()

        // Act
        game.throwCard(action)

        // Assert
        verify(exactly = 0) { playerAttacking.removeCard(card) }
    }

    @Test
    fun defend_validAction() {
        // Assemble
        val card = Card(SPADE, KING)
        val action = ActionThrowInCard(
                actionIssuer = playerDefending,
                thrownCard = card
        )
        every { turnsManager.isDefending(any()) } returns true
        every { defendingActionsFilter.filterActions(any(), any(), any()) } returns listOf(action)

        // Act
        game.throwCard(action)

        // Assert
        verify { playingTable.defend(card) }
        verify { playerDefending.removeCard(card) }
    }

    @Test
    fun defend_invalidAction() {
        // Assemble
        val card = Card(SPADE, KING)
        val action = ActionThrowInCard(
                actionIssuer = playerDefending,
                thrownCard = card
        )
        every { turnsManager.isDefending(any()) } returns true
        every { defendingActionsFilter.filterActions(any(), any(), any()) } returns emptyList<ActionGame>()

        // Act
        game.throwCard(action)

        // Assert
        verify(exactly = 0) { playingTable.defend(card) }
        verify(exactly = 0) { playerDefending.removeCard(card) }
    }

    @Test
    fun defend_throws() {
        // Assemble
        val card = Card(SPADE, KING)
        val action = ActionThrowInCard(
                actionIssuer = playerDefending,
                thrownCard = card
        )
        every { turnsManager.isDefending(any()) } returns true
        every { defendingActionsFilter.filterActions(any(), any(), any()) } returns listOf(action)
        every { playingTable.defend(any()) } throws IllegalArgumentException()

        // Act
        game.throwCard(action)

        // Assert
        verify(exactly = 0) { playerDefending.removeCard(card) }
    }

}