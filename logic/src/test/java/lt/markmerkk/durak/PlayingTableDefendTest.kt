package lt.markmerkk.durak

import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.MockitoAnnotations

class PlayingTableDefendTest {

    lateinit var playingTable: PlayingTable

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playingTable = PlayingTable(emptyList())
    }

    @Test
    fun valid() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = null
                )
        )

        // Act
        playingTable.defend(Card(SPADE, JACK))

        // Assert
        assertThat(playingTable.cards.first().defendingCard).isEqualTo(Card(SPADE, JACK))
    }

    @Test
    fun lowerCard() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = null
                )
        )

        // Act
        // Assert
        assertThrows<IllegalArgumentException> { playingTable.defend(Card(SPADE, NINE)) }
    }

    @Test
    fun differentSuite() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = null
                )
        )

        // Act
        // Assert
        assertThrows<IllegalArgumentException> {
            playingTable.defend(Card(HEART, JACK))
        }
    }

    @Test
    fun differentSuite_trumpCard() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN),
                        defendingCard = null
                )
        )

        // Act
        playingTable.defend(Card(HEART, JACK, isTrump = true))

        // Assert
        assertThat(playingTable.cards.first().defendingCard).isEqualTo(Card(HEART, JACK, isTrump = true))
    }

    @Test
    fun bothTrumps_higher() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN, isTrump = true),
                        defendingCard = null
                )
        )

        // Act
        playingTable.defend(Card(SPADE, JACK, isTrump = true))

        // Assert
        assertThat(playingTable.cards.first().defendingCard).isEqualTo(Card(SPADE, JACK, isTrump = true))
    }

    @Test
    fun bothTrumps_lower() {
        // Assemble
        playingTable.cards = listOf(
                PlayingCardPair(
                        attackingCard = Card(SPADE, TEN, isTrump = true),
                        defendingCard = null
                )
        )

        // Act
        // Assert
        assertThrows<IllegalArgumentException> {
            playingTable.defend(Card(SPADE, NINE, isTrump = true))
        }
    }

    @Test
    fun nothingToDefendAgainst() {
        // Assemble
        playingTable.cards = emptyList()

        // Act
        // Assert
        assertThrows<IllegalArgumentException> {
            playingTable.defend(Card(SPADE, JACK))
        }
    }

}