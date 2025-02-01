class Grid(val name: String) {
    //declare x and y coordinates as array
    private var x = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    private var y = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

    //declare grid as an array of Tiles
    private var grid: ArrayList<Tile> = ArrayList()

    //initialize the tiles for the player to put their ship onto
    init {
        for (i in x) {
            for (j in y) {
                grid.add(Tile(i to j, TileState.UNKNOWN))
            }
        }
    }

    //declare an array of tiles as our grid
    fun displayGrid() {
        for (a in y) {
            print("$a: ")  // Print the row number
            for (b in x) {
                // Find the tile with the corresponding x and y
                val tile = grid.first { it.coord == (b to a) }
                print("${tile.state}  ")  // Print the tile's state
            }
            println()  // Move to the next line after printing each row
        }
    }

    //get a specific tile
    fun getTile(x: Int, y: Int): Tile {
        return grid.first { it.coord == (x to y) }
    }
}

class Tile(
    var coord: Pair<Int, Int>,
    var state: TileState
)

class MainShip(
    var orientation: Orientation,
    var length: Int = 3
) {
    var coords: MutableList<Pair<Int, Int>> = mutableListOf()
}

class Player(val name: String, val grid: Grid, val ship: MainShip) {

    fun placeMainShip(leadTile: Tile): Boolean {
        val tempList = mutableListOf<Pair<Int, Int>>()
        //add a for each function based on length of ship
        //store tile coordinates as data in a list or something
        if (ship.orientation == Orientation.VERTICAL) {
            for (i in 0 until ship.length) {
                val y = leadTile.coord.second + i
                if (y <= 10) {
                    tempList.add(leadTile.coord.first to y)
                } else {
                    tempList.clear()
                    return false
                }
            }
            ship.coords = tempList
        }

        if (ship.orientation == Orientation.HORIZONTAL) {
            for (i in 0 until ship.length) {
                val x = leadTile.coord.first + i
                if (x <= 10) {
                    tempList.add(x to leadTile.coord.second)
                } else {
                    tempList.clear()
                    return false
                }
            }
            ship.coords = tempList
        }

        return true
    }
}

enum class TileState {
    UNKNOWN,
    MISS,
    HIT
}

enum class Orientation {
    HORIZONTAL,
    VERTICAL
}