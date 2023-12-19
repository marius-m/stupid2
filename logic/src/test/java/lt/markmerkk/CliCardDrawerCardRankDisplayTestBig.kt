package lt.markmerkk

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSpek.*
import lt.markmerkk.durak.CardSuite
import lt.markmerkk.durak.CardSuite.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CliCardDrawerCardRankDisplayTestBig {

    private val drawer = CliCardDrawerBig(firstLineBreak = false)

    @Test
    fun upper_oneSymbol() {
        // Assemble
        // Act
        val resultRank = CliCardDrawerBig.CardTemplate.cardDisplayUpper(Card(SPADE, NINE))

        // Assert
        assertThat(resultRank).isEqualTo("9 ")
    }

    @Test
    fun upper_twoSymbols() {
        // Assemble
        // Act
        val resultRank = CliCardDrawerBig.CardTemplate.cardDisplayUpper(Card(SPADE, TEN))

        // Assert
        assertThat(resultRank).isEqualTo("10")
    }

    @Test
    fun lower1() {
        // Assemble
        // Act
        val resultRank = CliCardDrawerBig.CardTemplate.cardDisplayLower(Card(SPADE, NINE))

        // Assert
        assertThat(resultRank).isEqualTo("_S9")
    }

    @Test
    fun lower2() {
        // Assemble
        // Act
        val resultRank = CliCardDrawerBig.CardTemplate.cardDisplayLower(Card(CLUB, ACE))

        // Assert
        assertThat(resultRank).isEqualTo("_CA")
    }

    @Test
    fun lower3() {
        // Assemble
        // Act
        val resultRank = CliCardDrawerBig.CardTemplate.cardDisplayLower(Card(DIAMOND, JACK))

        // Assert
        assertThat(resultRank).isEqualTo("_DJ")
    }

    @Test
    fun lower_twoSymbolRank() {
        // Assemble
        // Act
        val resultRank = CliCardDrawerBig.CardTemplate.cardDisplayLower(Card(HEART, TEN))

        // Assert
        assertThat(resultRank).isEqualTo("H10")
    }

}