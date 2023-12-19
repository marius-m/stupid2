package lt.markmerkk.durak

import com.nhaarman.mockito_kotlin.*
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import lt.markmerkk.durak.actions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GameTakeAllCardsTest {

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
        doReturn(true).whenever(turnsManager).isDefending(any())
        doReturn(listOf(action)).whenever(defendingActionsFilter).filterActions(any(), any(), any())
        doReturn(cardsOnTable).whenever(playingTable).allCards()

        // Act
        game.takeAll(action)

        // Assert
        verify(playingTable).clearAllCards()
        verify(playerDefending).addCards(cardsOnTable)
        verify(turnsManager, never()).endRound()
        players.forEach { verify(it).refill(refillingDeck) }
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
        doReturn(true).whenever(turnsManager).isDefending(any())
        doReturn(emptyList<ActionGame>()).whenever(defendingActionsFilter).filterActions(any(), any(), any()) // action not available
        doReturn(cardsOnTable).whenever(playingTable).allCards()

        // Act
        game.takeAll(action)

        // Assert
        verify(playingTable, never()).clearAllCards()
        verify(playerDefending, never()).addCards(cardsOnTable)
        verify(turnsManager, never()).endRound()
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
        doReturn(false).whenever(turnsManager).isDefending(any()) // player is not defending
        doReturn(listOf(action)).whenever(defendingActionsFilter).filterActions(any(), any(), any())
        doReturn(cardsOnTable).whenever(playingTable).allCards()

        // Act
        game.takeAll(action)

        // Assert
        verify(playingTable, never()).clearAllCards()
        verify(playerDefending, never()).addCards(cardsOnTable)
    }
}