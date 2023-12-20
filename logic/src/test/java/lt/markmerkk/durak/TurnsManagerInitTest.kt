package lt.markmerkk.durak

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TurnsManagerInitTest : AbsTurnsManagerTest() {

    @Test
    fun noPlayers() {
        // Assemble
        // Act
        // Assert
        assertThrows<IllegalStateException> {
            TurnsManager(emptyList())
        }
    }

    @Test
    fun valid() {
        // Assemble
        // Act
        // Assert
        assertThat(turnsManager2Player.attackingPlayer).isNotNull()
        assertThat(turnsManager2Player.defendingPlayer).isNotNull()
        assertThat(turnsManager2Player.attackingPlayer).isNotEqualTo(turnsManager2Player.defendingPlayer)
    }

}