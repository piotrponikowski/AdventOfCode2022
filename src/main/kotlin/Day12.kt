class Day12(input: List<String>) {

    private val board = input.map { line -> line.map { value -> value.toString().first() } }
        .flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }

    private val heights = board.toMap().mapValues { (_, symbol) ->
        when (symbol) {
            'S' -> 0
            'E' -> 25
            else -> symbol.code - 'a'.code
        }
    }

    fun part1(): Int {
        val start = findPointWithSymbol('S')
        val exit = findPointWithSymbol('E')

        return solve(start, exit)
    }

    fun part2(): Int {
        val starts = findAllStartPoints()
        val exit = findPointWithSymbol('E')

        return starts.map { start -> solve(start, exit)}.min()
    }

    private fun solve(start: Point, exit: Point): Int {
        val pathsToCheck = mutableListOf(listOf(start))
        val visitedPoints = mutableSetOf(start)

        while (pathsToCheck.isNotEmpty()) {
            val currentPath = pathsToCheck.removeFirst()
            val currentPoint = currentPath.last()
            val currentHeight = heights[currentPoint]!!

            directions.forEach { direction ->
                val nextPoint = currentPoint + direction
                val nextHeight = heights[nextPoint]

                if (nextHeight != null && nextHeight <= currentHeight + 1) {

                    if (nextPoint == exit) {
                        return currentPath.size
                    }

                    if (!visitedPoints.contains(nextPoint)) {
                        pathsToCheck += currentPath + nextPoint
                        visitedPoints += nextPoint
                    }
                }
            }
        }

        return Int.MAX_VALUE
    }

    private fun findPointWithSymbol(expectedValue: Char) = board
        .first { (_, value) -> value == expectedValue }
        .let { (key, _) -> key }

    private fun findAllStartPoints() = heights
        .filter { (_, height) -> height == 0 }.keys

    private val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}