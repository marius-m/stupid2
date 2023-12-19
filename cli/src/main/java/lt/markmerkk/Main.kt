package lt.markmerkk

import lt.markmerkk.actions.ActionExecutorSystem
import lt.markmerkk.actions.ActionTranslatorHelp
import lt.markmerkk.actions.ActionTranslatorQuit
import lt.markmerkk.actions.system.ActionSystem
import lt.markmerkk.durak.*
import lt.markmerkk.durak.actions.*
import lt.markmerkk.durak.actions.system.ActionIllegalCannotTranslate
import lt.markmerkk.durak.actions.system.ActionIllegalMultipleActions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Scanner

class Main {

    fun main(args: Array<String>) {
        // Game components
        val players = listOf(
                Player("Marius"),
                Player("Enrika")
        )
        val turnsManager = TurnsManager(players = players)
        val game = Game(
                cards = Card.generateDeckSmall(),
                players = players,
                turnsManager = turnsManager
        )
        game.resetGame()
        game.refillPlayerCards()

        // Cli control components
        val inputReader = Scanner(System.`in`)
        val actionExecutorSystem = ActionExecutorSystem()
        val actionExecutorGame = ActionExecutorGame(players, game)
        val cliInputHandler = CliInputHandler(
                actionTranslators = listOf(
                        ActionTranslatorQuit(players),
                        ActionTranslatorThrowCards(players),
                        ActionTranslatorHelp(players, game),
                        ActionTranslatorTakeAll(players),
                        ActionTranslatorFinishRound(players)
                )
        )
        val cliCardDrawer = CliCardDrawer()

        logger.info("Hello and welcome to game of 'Stupid'!\n")
        while (!game.isGameOver) {
            printGameStatus(players, game.playingTable, turnsManager, cliCardDrawer, game.refillingDeck)
            val inputAction = cliInputHandler.handleInput(inputReader.nextLine())
            logger.divider()
            when (inputAction) {
                is ActionIllegalMultipleActions -> logger.info("Illegal action!")
                is ActionIllegalCannotTranslate -> logger.info("Illegal action!")
                is ActionGame -> actionExecutorGame.execute(inputAction)
                is ActionSystem -> actionExecutorSystem.execute(inputAction)
                else -> logger.warn("Action cannot be executed\n")
            }
        }

    }

    fun printGameStatus(
            players: List<Player>,
            playingTable: PlayingTable,
            turnsManager: TurnsManager,
            cliCardDrawer: CliCardDrawer,
            refillingDeck: RefillingDeck
    ) {
        logger.info("\n--- Cards on table --- \n")
        logger.info(cliCardDrawer.drawCards(playingTable.allAttackingCards()))
        logger.info(cliCardDrawer.drawCards(playingTable.allDefendingCards()))
        logger.info("\n------- \n")
        players.forEach {
            logger.info("${it.name}'s cards (${it.cardsInHandSize()}): \n")
            logger.info(cliCardDrawer.drawCards(it.cardsInHand()))
            logger.info("\n")
        }
        logger.info("Game status: ${turnsManager.attackingPlayer.name} turn to attack. ${refillingDeck.cards.size} cards left in deck.\n")
        logger.info("For available player actions, type in \"?\"\n")
        logger.info("For concrete player available actions \"[player name] ?\"\n")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }
}

fun main(args : Array<String>) {
    Main().main(args)
}

/**
 * Prints massive divider
 */
fun Logger.divider() {
    info("------------------------------------------\n")
    info("------------------------------------------\n")
    info("------------------------------------------\n")
    info("------------------------------------------\n")
    info("------------------------------------------\n")
}
