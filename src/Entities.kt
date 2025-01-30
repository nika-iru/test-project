class Grid(val name: String) {
    //declare x and y coordinates as array
    var x = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J')
    var y = arrayOf(0, 1, 2,3, 4, 5, 6, 7, 8, 9)

    //declare grid as an array of Tiles
    private var grid: ArrayList<Tile> = ArrayList()

    //initialize the tiles for the player to put their ship onto
    init {
        for (i in x) {
            for (j in y) {
                grid.add(Tile(i, j, TileState.EMPTY))
            }
        }
    }

    //declare an array of tiles as our grid
    fun displayGrid() {
        for (a in y) {
            print("$a: ")  // Print the row number
            for (j in x) {
                // Find the tile with the corresponding x and y
                val tile = grid.first { it.x == j && it.y == a }
                print("${tile.state}  ")  // Print the tile's state
            }
            println()  // Move to the next line after printing each row
        }
    }

    //get a specific tile
    private fun getTile(x: Char, y: Int): Tile {
        return grid.first { it.x == x && it.y == y }
    }

    private fun Tile.isEmpty(): Boolean {
        return this.state == TileState.EMPTY
    }

    fun placeShip(x: Char, y: Int, ship: Ship): Boolean {
        val shipTiles = mutableListOf<Tile>()

        for (i in 0..< ship.length) {
            val tile = when (ship.orientation) {
                Orientation.VERTICAL -> getTile(x, y)
                Orientation.HORIZONTAL -> getTile(x , y)
            }

            if (tile.state != TileState.EMPTY || tile.x !in this.x || tile.y !in this.y) {
                return false
            }
            shipTiles.add(tile)
        }

        if (ship.orientation == Orientation.VERTICAL) {
            //get tiles
            val tile1 = getTile(x, y)
            val tile2 = getTile(x, y + 1)
            val tile3 = getTile(x, y + 2)
            if (!tile1.isEmpty() && !tile2.isEmpty() && !tile3.isEmpty()) {
                return false
            }
        }

        for (tile in shipTiles) {
            tile.state = TileState.OCCUPIED
        }

        return true
    }
}

class Tile(
    var x: Char,
    var y: Int,
    var state: TileState
)

open class Ship(
    var orientation: Orientation,
    var length: Int
)

class MainShip(orientation: Orientation) : Ship(orientation = orientation, length = 3)

class ScoutShip(orientation: Orientation) : Ship(orientation = orientation, length = 2)

class Player(val name: String, val mainShip: MainShip, val scoutShip: ScoutShip, val myGrid: Grid) {
    fun placeMainShip(x: Char, y: Int): Boolean {
        return myGrid.placeShip(x, y, mainShip)
    }
    fun placeScoutShip(x: Char, y: Int): Boolean {
        return myGrid.placeShip(x, y, scoutShip)
    }
}

enum class TileState {
    OCCUPIED,
    EMPTY,
    UNKNOWN,
    MISS,
    HIT
}

enum class Orientation {
    HORIZONTAL,
    VERTICAL
}