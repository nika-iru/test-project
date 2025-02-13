import java.util.Scanner
import kotlin.system.exitProcess

// ANSI color codes for console output
const val RESET = "\u001B[0m"
const val RED = "\u001B[31m"  // Red for hits
const val BLUE = "\u001B[34m" // Blue for ships
const val GREEN = "\u001B[32m" // Green for Player 1
const val CYAN = "\u001B[36m"  // Cyan for Player 2

fun main() {
    val scanner = Scanner(System.`in`)

    println("${BLUE}=======================================${RESET}")
    println("${BLUE}    Welcome to Battleship! ${RESET}")
    println("${BLUE} Prepare for an epic naval battle!${RESET}")
    println("${BLUE}=======================================${RESET}\n")
    println("Each player will place their ship and take turns attacking! May the best strategist win!\n")

    // Initialize players with empty grids
    val player1 = Player("${GREEN}P1$RESET", Grid("P1Grid"), MainShip(Orientation.HORIZONTAL))
    val player2 = Player("${CYAN}P2$RESET", Grid("P2Grid"), MainShip(Orientation.HORIZONTAL))

    // Players place their ships
    println("\n${GREEN}Player 1, choose your ship placement:$RESET")
    placeShip(scanner, player1)

    println("\n${CYAN}Player 2, choose your ship placement:$RESET")
    placeShip(scanner, player2)

    // Start game loop
    var currentPlayer = player1
    var opponent = player2

    while (true) {
        println("\n${currentPlayer.name}'s turn to attack!")
        currentPlayer.attack(scanner, opponent)

        // Check if the opponent's ship is fully destroyed
        if (opponent.isShipSunk()) {
            println("\n${currentPlayer.name} wins! All enemy ships have been sunk!")
            break
        }

        // Swap players for next turn
        val temp = currentPlayer
        currentPlayer = opponent
        opponent = temp
    }
}

fun placeShip(scanner: Scanner, player: Player) {
    var placed = false
    while (!placed) {
        println("${player.name}, enter the starting X (0-9):")
        val x = scanner.nextInt()
        println("${player.name}, enter the starting Y (0-9):")
        val y = scanner.nextInt()
        println("${player.name}, choose orientation (H for Horizontal, V for Vertical):")
        val orientation = if (scanner.next().equals("H", ignoreCase = true)) Orientation.HORIZONTAL else Orientation.VERTICAL
        player.ship.orientation = orientation

        val tile = player.grid.getTile(x, y)
        if (tile != null && player.placeMainShip(tile)) {
            println("\nShip placed successfully! Updated Grid:")
            player.grid.displayGrid()
            placed = true
        } else {
            println("Invalid placement. Try again.\n")
        }
    }
}