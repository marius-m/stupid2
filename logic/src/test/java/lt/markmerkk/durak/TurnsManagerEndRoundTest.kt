package lt.markmerkk.durak

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TurnsManagerEndRoundTest : AbsTurnsManagerTest() {

    @Test
    fun players2_next() {
        // Assert
        assertThat(turnsManager2Player.attackingPlayer).isEqualTo(player1)
        assertThat(turnsManager2Player.defendingPlayer).isEqualTo(player2)

        // Act
        turnsManager2Player.endRound()

        // Assert
        assertThat(turnsManager2Player.attackingPlayer).isEqualTo(player2)
        assertThat(turnsManager2Player.defendingPlayer).isEqualTo(player1)
    }

    @Test
    fun players3_next() {
        // Assert
        assertThat(turnsManager3Player.attackingPlayer).isEqualTo(player1)
        assertThat(turnsManager3Player.defendingPlayer).isEqualTo(player2)

        // Act
        turnsManager3Player.endRound()

        // Assert
        assertThat(turnsManager3Player.attackingPlayer).isEqualTo(player2)
        assertThat(turnsManager3Player.defendingPlayer).isEqualTo(player3)

        // Act
        turnsManager3Player.endRound()

        // Assert
        assertThat(turnsManager3Player.attackingPlayer).isEqualTo(player3)
        assertThat(turnsManager3Player.defendingPlayer).isEqualTo(player1)
    }

    @Test
    fun players4_next() {
        // Assert
        assertThat(turnsManager4Player.attackingPlayer).isEqualTo(player1)
        assertThat(turnsManager4Player.defendingPlayer).isEqualTo(player2)

        // Act
        turnsManager4Player.endRound()

        // Assert
        assertThat(turnsManager4Player.attackingPlayer).isEqualTo(player2)
        assertThat(turnsManager4Player.defendingPlayer).isEqualTo(player3)

        // Act
        turnsManager4Player.endRound()

        // Assert
        assertThat(turnsManager4Player.attackingPlayer).isEqualTo(player3)
        assertThat(turnsManager4Player.defendingPlayer).isEqualTo(player4)

        // Act
        turnsManager4Player.endRound()

        // Assert
        assertThat(turnsManager4Player.attackingPlayer).isEqualTo(player4)
        assertThat(turnsManager4Player.defendingPlayer).isEqualTo(player1)
    }

}
