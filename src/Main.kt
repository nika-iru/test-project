fun main() {
    // Initialize the players
    val player1 = Player("P1", MainShip(Orientation.VERTICAL), ScoutShip(Orientation.HORIZONTAL), Grid("P1Grid"))
    val player2 = Player("P2", MainShip(Orientation.VERTICAL), ScoutShip(Orientation.VERTICAL), Grid("P2Grid"))

    // Place the main ship for player1 at D4
    val successPlayer1 = player1.placeMainShip('D', 0)

    // Display the result
    if (successPlayer1) {
        println("${player1.name}'s main ship placed successfully at D4")
    } else {
        println("Failed to place ${player1.name}'s main ship at D4")
    }

    // Display the grid for player1
    println("${player1.name}'s grid:")
    player1.myGrid.displayGrid()
}