import com.sun.tools.javac.Main

fun main() {
    val ship1 = MainShip(Orientation.HORIZONTAL)
    val ship2 = MainShip(Orientation.HORIZONTAL)
    // Initialize the players
    val player1 = Player("P1", Grid("P1Grid"), ship1)
    val player2 = Player("P2", Grid("P2Grid"), ship2)

    if (player1.placeMainShip((player1.grid.getTile(9,9)))) {
        print("Ship Placed Successfully")
    }
    else print("Ship Placed Failed")

    // Display the grid for player1
    println("${player1.name}'s grid:")
    player1.grid.displayGrid()
}