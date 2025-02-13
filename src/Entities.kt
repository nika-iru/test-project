import java.util.Scanner

class Grid(val name: String) {
    private val grid: ArrayList<Tile> = ArrayList()

    init {
        for (i in 0 until 10) {
            for (j in 0 until 10) {
                grid.add(Tile(i to j, TileState.UNKNOWN))
            }
        }
    }

    fun displayGrid() {
        println("\nGrid: $name")
        print("   ")
        for (x in 0 until 10) print("$x  ")
        println()
        for (y in 0 until 10) {
            print("$y: ")
            for (x in 0 until 10) {
                val tile = grid.first { it.coord == (x to y) }
                val displayChar = when (tile.state) {
                    TileState.UNKNOWN -> "~"
                    TileState.MISS -> "M"
                    TileState.HIT -> "${RED}X$RESET"
                    TileState.SHIP -> "${BLUE}■$RESET"
                }
                print("$displayChar  ")
            }
            println()
        }
    }

    fun getTile(x: Int, y: Int): Tile? {
        return grid.firstOrNull { it.coord == (x to y) }
    }

    fun markOccupied(coords: List<Pair<Int, Int>>) {
        coords.forEach { coord ->
            grid.firstOrNull { it.coord == coord }?.state = TileState.SHIP
        }
    }

    fun attackTile(x: Int, y: Int): Boolean {
        val tile = getTile(x, y)
        return if (tile != null) {
            when (tile.state) {
                TileState.SHIP -> {
                    tile.state = TileState.HIT
                    println("${RED}Hit!$RESET")
                    true
                }
                TileState.UNKNOWN -> {
                    tile.state = TileState.MISS
                    println("Miss!")
                    false
                }
                else -> {
                    println("Already attacked this spot.")
                    false
                }
            }
        } else {
            println("Invalid attack coordinates.")
            false
        }
    }
}

class Tile(var coord: Pair<Int, Int>, var state: TileState)

class MainShip(var orientation: Orientation, var length: Int = 3) {
    var coords: MutableList<Pair<Int, Int>> = mutableListOf()
}

class Player(val name: String, val grid: Grid, val ship: MainShip) {
    fun placeMainShip(leadTile: Tile): Boolean {
        val tempList = mutableListOf<Pair<Int, Int>>()
        if (ship.orientation == Orientation.VERTICAL) {
            for (i in 0 until ship.length) {
                val y = leadTile.coord.second + i
                if (y < 10 && grid.getTile(leadTile.coord.first, y)?.state == TileState.UNKNOWN) {
                    tempList.add(leadTile.coord.first to y)
                } else return false
            }
        } else {
            for (i in 0 until ship.length) {
                val x = leadTile.coord.first + i
                if (x < 10 && grid.getTile(x, leadTile.coord.second)?.state == TileState.UNKNOWN) {
                    tempList.add(x to leadTile.coord.second)
                } else return false
            }
        }
        ship.coords = tempList
        grid.markOccupied(tempList)
        return true
    }

    fun attack(scanner: Scanner, opponent: Player) {
        var validAttack = false
        while (!validAttack) {
            println("${name}, enter attack X (0-9):")
            val x = scanner.nextInt()
            println("${name}, enter attack Y (0-9):")
            val y = scanner.nextInt()
            validAttack = opponent.grid.attackTile(x, y)
            opponent.grid.displayGrid()
        }
    }

    fun isShipSunk(): Boolean {
        return ship.coords.all { coord ->
            val tile = grid.getTile(coord.first, coord.second)
            tile?.state == TileState.HIT
        }
    }
}

enum class TileState { UNKNOWN, MISS, HIT, SHIP }
enum class Orientation { HORIZONTAL, VERTICAL }