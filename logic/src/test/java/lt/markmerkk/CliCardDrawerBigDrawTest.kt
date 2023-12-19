package lt.markmerkk

import lt.markmerkk.durak.Card
import lt.markmerkk.durak.CardRank
import lt.markmerkk.durak.CardRank.ACE
import lt.markmerkk.durak.CardSuite.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations

class CliCardDrawerBigDrawTest {

    lateinit var drawer: CliCardDrawerBig

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        drawer = CliCardDrawerBig(firstLineBreak = false)
    }

    @Test
    fun valid_club() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(CLUB, ACE))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|A  _   |\n" +
                        "|  ( )  |\n" +
                        "| (_'_) |\n" +
                        "|   |   |\n" +
                        "|_____CA|\n"
        )
    }

    @Test
    fun valid_club_twoSymbols() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(CLUB, CardRank.TEN))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|10 _   |\n" +
                        "|  ( )  |\n" +
                        "| (_'_) |\n" +
                        "|   |   |\n" +
                        "|____C10|\n"
        )
    }

    @Test
    fun allClubs() {
        // Assemble
        val allRanks = CardRank
                .values()
                .map { cardRank ->  Card(CLUB, cardRank) }

        // Act
        val resultDrawing = drawer.drawCards(allRanks)

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______ \n" +
                        "|A  _   ||K  _   ||Q  _   ||J  _   ||10 _   ||9  _   ||8  _   ||7  _   ||6  _   ||5  _   ||4  _   ||3  _   ||2  _   |\n" +
                        "|  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  ||  ( )  |\n" +
                        "| (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) || (_'_) |\n" +
                        "|   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   |\n" +
                        "|_____CA||_____CK||_____CQ||_____CJ||____C10||_____C9||_____C8||_____C7||_____C6||_____C5||_____C4||_____C3||_____C2|\n"
        )
    }

    @Test
    fun valid_heart() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(HEART, ACE))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|A _ _  |\n" +
                        "| ( v ) |\n" +
                        "|  \\ /  |\n" +
                        "|   .   |\n" +
                        "|_____HA|\n"
        )
    }

    @Test
    fun allHearts() {
        // Assemble
        val allRanks = CardRank
                .values()
                .map { cardRank ->  Card(HEART, cardRank) }

        // Act
        val resultDrawing = drawer.drawCards(allRanks)

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______ \n" +
                        "|A _ _  ||K _ _  ||Q _ _  ||J _ _  ||10_ _  ||9 _ _  ||8 _ _  ||7 _ _  ||6 _ _  ||5 _ _  ||4 _ _  ||3 _ _  ||2 _ _  |\n" +
                        "| ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) || ( v ) |\n" +
                        "|  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  |\n" +
                        "|   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   |\n" +
                        "|_____HA||_____HK||_____HQ||_____HJ||____H10||_____H9||_____H8||_____H7||_____H6||_____H5||_____H4||_____H3||_____H2|\n"
        )
    }

    @Test
    fun valid_diamond() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(DIAMOND, ACE))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|A  ^   |\n" +
                        "|  / \\  |\n" +
                        "|  \\ /  |\n" +
                        "|   .   |\n" +
                        "|_____DA|\n"
        )
    }

    @Test
    fun allDiamonds() {
        // Assemble
        val allRanks = CardRank
                .values()
                .map { cardRank ->  Card(DIAMOND, cardRank) }

        // Act
        val resultDrawing = drawer.drawCards(allRanks)

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______ \n" +
                        "|A  ^   ||K  ^   ||Q  ^   ||J  ^   ||10 ^   ||9  ^   ||8  ^   ||7  ^   ||6  ^   ||5  ^   ||4  ^   ||3  ^   ||2  ^   |\n" +
                        "|  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  ||  / \\  |\n" +
                        "|  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  ||  \\ /  |\n" +
                        "|   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   ||   .   |\n" +
                        "|_____DA||_____DK||_____DQ||_____DJ||____D10||_____D9||_____D8||_____D7||_____D6||_____D5||_____D4||_____D3||_____D2|\n"
        )
    }

    @Test
    fun valid_spade() {
        // Assemble
        // Act
        val resultDrawing = drawer.drawCards(Card(SPADE, ACE))

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______ \n" +
                        "|A  .   |\n" +
                        "|  /.\\  |\n" +
                        "| (_._) |\n" +
                        "|   |   |\n" +
                        "|_____SA|\n"
        )
    }

    @Test
    fun allSpades() {
        // Assemble
        val allRanks = CardRank
                .values()
                .map { cardRank ->  Card(SPADE, cardRank) }

        // Act
        val resultDrawing = drawer.drawCards(allRanks)

        // Assert
        print(resultDrawing)
        assertThat(resultDrawing).isEqualTo(
                        " _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______  _______ \n" +
                        "|A  .   ||K  .   ||Q  .   ||J  .   ||10 .   ||9  .   ||8  .   ||7  .   ||6  .   ||5  .   ||4  .   ||3  .   ||2  .   |\n" +
                        "|  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  ||  /.\\  |\n" +
                        "| (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) || (_._) |\n" +
                        "|   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   ||   |   |\n" +
                        "|_____SA||_____SK||_____SQ||_____SJ||____S10||_____S9||_____S8||_____S7||_____S6||_____S5||_____S4||_____S3||_____S2|\n"
        )
    }

}