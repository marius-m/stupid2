package lt.markmerkk.durak

import io.kotest.core.spec.style.DescribeSpec
import lt.markmerkk.durak.CardRank.*
import lt.markmerkk.durak.CardSuite.DIAMOND
import lt.markmerkk.durak.CardSuite.HEART
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy

class PlayingTableAttackSpek : DescribeSpec({
    describe("playing table is empty") {
        val playingTable = PlayingTable(cards = emptyList())

        context("on any card thrown") {
            playingTable.attack(Card(DIAMOND, NINE))

            it("should contain attacking card") {
                assertThat(playingTable.cards).containsExactly(PlayingCardPair(
                        attackingCard = Card(DIAMOND, NINE),
                        defendingCard = null
                ))
            }
        }
    }


    describe("playing table already has attacking item") {
        lateinit var playingTable: PlayingTable
        beforeTest {
            playingTable = PlayingTable(
                cards = listOf(
                    PlayingCardPair(
                        attackingCard = Card(DIAMOND, NINE),
                        defendingCard = null
                    )
                )
            )
        }

        context("try adding same rank card") {
            playingTable.attack(Card(HEART, NINE))

            it("should contain placed card") {
                assertThat(playingTable.cards).containsExactly(
                        PlayingCardPair(
                                attackingCard = Card(DIAMOND, NINE),
                                defendingCard = null
                        ),
                        PlayingCardPair(
                                attackingCard = Card(HEART, NINE),
                                defendingCard = null
                        )
                )
            }
        }

        context("try adding different rank card") {
            it("should throw exception as an illegal action") {
                assertThatThrownBy { playingTable.attack(Card(DIAMOND, JACK)) }
            }
            it("should not contain card on table") {
                assertThat(playingTable.cards).containsExactly(
                        PlayingCardPair(
                                attackingCard = Card(DIAMOND, NINE),
                                defendingCard = null
                        )
                )
            }
        }
    }

    describe("playing table is full") {
        lateinit var playingTable: PlayingTable
        beforeTest {
            playingTable = PlayingTable(cards = listOf(
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, TWO),
                            defendingCard = null
                    ),
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, THREE),
                            defendingCard = null
                    ),
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, FOUR),
                            defendingCard = null
                    ),
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, FIVE),
                            defendingCard = null
                    ),
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, SIX),
                            defendingCard = null
                    ),
                    PlayingCardPair(
                            attackingCard = Card(DIAMOND, SEVEN),
                            defendingCard = null
                    )
            ))
        }
        context("valid card thrown") {
            it("should throw exception as an illegal action") {
                assertThatThrownBy { playingTable.attack(Card(HEART, SIX)) }
            }
            it("should not contain card on table") {
                assertThat(playingTable.cards.size).isEqualTo(Consts.MAX_PAIRS_ON_TABLE)
            }
        }
    }
})

