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

class GameTakeAllCardsTest {

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
    fun defendingPlayer_takeAll() {
        // Assemble
        val cardsOnTable = listOf(
                Card(SPADE, TEN),
                Card(SPADE, NINE),
                Card(SPADE, EIGHT)
        )
        val action = ActionTakeAllCards(
                actionIssuer = playerDefending
        )
        every { turnsManager.isDefending(any()) } returns true
        every { defendingActionsFilter.filterActions(any(), any(), any()) } returns listOf(action)
        every { playingTable.allCards() } returns cardsOnTable

        // Act
        game.takeAll(action)

        // Assert
        verify { playingTable.clearAllCards() }
        verify { playerDefending.addCards(cardsOnTable) }
        verify(exactly = 0) { turnsManager.endRound() }
        players.forEach {
            verify { it.refill(refillingDeck) }
        }
    }

    @Test
    fun defendingPlayer_actionNotAvailable() {
        // Assemble
        val cardsOnTable = listOf(
                Card(SPADE, TEN),
                Card(SPADE, NINE),
                Card(SPADE, EIGHT)
        )
        val action = ActionTakeAllCards(
                actionIssuer = playerDefending
        )
//        doReturn(true).whenever(turnsManager).isDefending(any())
        every { turnsManager.isDefending(any()) } returns true
//        doReturn(emptyList<ActionGame>()).whenever(defendingActionsFilter).filterActions(any(), any(), any()) // action not available
        every { defendingActionsFilter.filterActions(any(), any(), any()) } returns emptyList<ActionGame>() // action not available
//        doReturn(cardsOnTable).whenever(playingTable).allCards()
        every { playingTable.allCards() } returns cardsOnTable

        // Act
        game.takeAll(action)

        // Assert
//        verify(playingTable, never()).clearAllCards()
        verify(exactly = 0) { playingTable.clearAllCards() }
//        verify(playerDefending, never()).addCards(cardsOnTable)
        verify(exactly = 0) { playerDefending.addCards(cardsOnTable) }
//        verify(turnsManager, never()).endRound()
        verify(exactly = 0) { turnsManager.endRound() }
    }

    @Test
    fun notDefendingPlayer() {
        // Assemble
        val cardsOnTable = listOf(
                Card(SPADE, TEN),
                Card(SPADE, NINE),
                Card(SPADE, EIGHT)
        )
        val action = ActionTakeAllCards(
                actionIssuer = playerAttacking
        )
//        doReturn(false).whenever(turnsManager).isDefending(any()) // player is not defending
        every { turnsManager.isDefending(any()) } returns false // player is not defending
//        doReturn(listOf(action)).whenever(defendingActionsFilter).filterActions(any(), any(), any())
        every { defendingActionsFilter.filterActions(any(), any(), any()) } returns listOf(action)
//        doReturn(cardsOnTable).whenever(playingTable).allCards()
        every { playingTable.allCards() } returns cardsOnTable

        // Act
        game.takeAll(action)

        // Assert
//        verify(playingTable, never()).clearAllCards()
        verify(exactly = 0) { playingTable.clearAllCards() }
//        verify(playerDefending, never()).addCards(cardsOnTable)
        verify(exactly = 0) { playerDefending.addCards(cardsOnTable) }
    }
}