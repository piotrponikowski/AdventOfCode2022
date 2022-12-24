class Day24(input: List<String>) {

    private val board = input
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }
        .toMap()

    private val blizzards = board
        .filter { (_, symbol) -> symbol in listOf('>', '<', 'v', '^') }
        .map { (point, symbol) -> Blizzard(point, symbol) }

    private val xMax = board.keys.maxOf { point -> point.x }
    private val xMin = board.keys.minOf { point -> point.x }
    private val yMax = board.keys.maxOf { point -> point.y }
    private val yMin = board.keys.minOf { point -> point.y }

    fun step() {


    }

    fun moveBlizzards(blizzards: List<Blizzard>): List<Blizzard> {

        return blizzards.map { (point, symbol) ->

            if (symbol == '>') {
                val newPoint = point + right
                if (newPoint.x == xMax) {
                    Blizzard(Point(xMin + 1, point.y), symbol)
                } else {
                    Blizzard(newPoint, symbol)
                }
            } else if (symbol == '<') {
                val newPoint = point + left
                if (newPoint.x == xMin) {
                    Blizzard(Point(xMax - 1, point.y), symbol)
                } else {
                    Blizzard(newPoint, symbol)
                }
            } else if (symbol == 'v') {
                val newPoint = point + down
                if (newPoint.y == yMax) {
                    Blizzard(Point(point.x, yMin + 1), symbol)
                } else {
                    Blizzard(newPoint, symbol)
                }
            } else if (symbol == '^') {
                val newPoint = point + up
                if (newPoint.y == yMin) {
                    Blizzard(Point(point.x, yMax - 1), symbol)
                } else {
                    Blizzard(newPoint, symbol)
                }
            } else {
                Blizzard(point, symbol)
            }
        }
    }

    fun movePositions(positions: Set<Point>, blizzards: List<Blizzard>): Set<Point> {
        return positions.flatMap { position ->
            directions.mapNotNull { direction ->
                val nextPosition = direction + position

                val isBlizzard = blizzards.any { blizzard -> blizzard.position == nextPosition }

                if (isBlizzard || board[nextPosition] == null || board[nextPosition] == '#' ) {
                    null
                } else {
                    nextPosition
                }
            }

        }.toSet()
    }


    fun part1(): Int {
        val start = board.filter { (point, symbol) -> point.y == yMin && symbol == '.' }.keys.first()
        val end = board.filter { (point, symbol) -> point.y == yMax && symbol == '.' }.keys.first()

        var currentBlizzards = blizzards
        var currentPositions = setOf(start)

        var round = 1
        while (true) {
            currentBlizzards = moveBlizzards(currentBlizzards)
            currentPositions = movePositions(currentPositions, currentBlizzards)

            if (end in currentPositions) {
                return round
            }

            round++
        }
    }

    fun part2(): Int {
        val start = board.filter { (point, symbol) -> point.y == yMin && symbol == '.' }.keys.first()
        val end = board.filter { (point, symbol) -> point.y == yMax && symbol == '.' }.keys.first()

        var currentBlizzards = blizzards
        var currentPositions = setOf(start)

        var round = 1
        var trip = 1
        while (true) {
            currentBlizzards = moveBlizzards(currentBlizzards)
            currentPositions = movePositions(currentPositions, currentBlizzards)

            if (trip == 1 && end in currentPositions) {
                trip = 2
                currentPositions = setOf(end)
            }

            if (trip == 2 && start in currentPositions) {
                trip = 3
                currentPositions = setOf(start)
            }

            if (trip == 3 && end in currentPositions) {
                return round
            }

            round++
        }
    }

    private val right = Point(1, 0)
    private val left = Point(-1, 0)
    private val down = Point(0, 1)
    private val up = Point(0, -1)
    private val wait = Point(0, 0)

    private val directions = listOf(right, left, down, up, wait)

    data class Blizzard(val position: Point, val symbol: Char)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {

    val input = readLines("day24.txt")
//    val input = readLines("day24.txt", true)

    val result = Day24(input).part2()
    println(result)

}