package lt.markmerkk.durak

import kotlin.random.Random

data class Card(
        val suite: CardSuite,
        val rank: CardRank,
        val isTrump: Boolean = false
): Comparable<Card> {

    override fun compareTo(other: Card): Int {
        if (weight() > other.weight()) {
            return 1
        }
        if (weight() == other.weight()) {
            return 0
        }
        return -1
    }

    fun valueAsString(): String = "${suite.out}${rank.out}"

    fun weight(): Int = if (isTrump) rank.weight + 100 else rank.weight

    override fun toString(): String {
        return "Card(${suite.out}${rank.out})"
    }

    companion object {
        val regexPattern = "[SHDC]([AKQJ]|[0-9]){1,2}"
        val cardStringMap: Map<String, Card> = CardSuite.values().toList()
                .combine(CardRank.values().toList())
                .fold(mutableMapOf()) { mutableMap, suiteRankPair ->
                    val card = Card(suiteRankPair.first, suiteRankPair.second)
                    mutableMap.put(card.valueAsString(), card)
                    mutableMap
                }

        fun generateDeck(cardTypeTrump: CardSuite): List<Card> {
            val cards = mutableListOf<Card>()
            for (suite in CardSuite.values()) {
                val isTrump = suite == cardTypeTrump
                for (rank in CardRank.values()) {
                    cards.add(Card(suite, rank, isTrump))
                }
            }
            return cards
        }

        fun generateDeckSmall(cardTypeTrump: CardSuite): List<Card> {
            val cards = mutableListOf<Card>()
            for (suite in CardSuite.values()) {
                val isTrump = suite == cardTypeTrump
                val smallerSetOfRanks = CardRank.values()
                        .filter {
                            it == CardRank.ACE
                                    || it == CardRank.KING
                                    || it == CardRank.QUEEN
                                    || it == CardRank.JACK
                                    || it == CardRank.TEN
                                    || it == CardRank.NINE
                        }
                for (rank in smallerSetOfRanks) {
                    cards.add(Card(suite, rank, isTrump))
                }
            }
            return cards
        }

        fun randomSuite(random: Random = Random): CardSuite {
            val randomNum = (0..3).random(random)
            return CardSuite.values()[randomNum]
        }

        fun fromString(cardAsString: String): Card? {
            if (cardStringMap.containsKey(cardAsString))
                return cardStringMap[cardAsString]
            return null
        }
    }
}

@Throws(IllegalStateException::class)
fun List<Card>.trumpOrThrow(): CardSuite {
    return this.firstOrNull { it.isTrump }?.suite ?: throw IllegalStateException("No trump found")
}

fun List<Card>.filterSameRank(rank: CardRank): List<Card> = filter { it.rank == rank }

enum class CardSuite(
        val out: String
) {
    SPADE("S"),
    HEART("H"),
    DIAMOND("D"),
    CLUB("C"),
    ;
}

enum class CardRank(
        val out: String,
        val fullOut: String,
        val weight: Int
) {
    ACE("A", "Ace", 14),
    KING("K", "King", 13),
    QUEEN("Q", "Queen", 12),
    JACK("J", "Jack", 11),
    TEN("10", "10", 10),
    NINE("9", "9", 9),
    EIGHT("8", "8", 8),
    SEVEN("7", "7", 7),
    SIX("6", "6", 6),
    FIVE("5", "5", 5),
    FOUR("4", "4", 4),
    THREE("3", "3", 3),
    TWO("2", "2", 2),
    ;
}