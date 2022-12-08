class Day8(input: List<String>) {

    private val trees = input.map { line -> line.map { value -> value.toString().toInt() } }
        .flatMapIndexed { y, line -> line.mapIndexed { x, size -> Point(x, y) to size } }.toMap()

    fun part1() = trees
        .filter { (location, size) ->
            findTreeLines(location)
                .any { line -> line.all { otherLocation -> trees[otherLocation]!! < size } }
        }.count()

    fun part2() = trees
        .map { (location, size) ->
            findTreeLines(location)
                .map { line -> countVisible(line, size) }
                .reduce { score, distance -> score * distance }
        }.max()

    private fun findTreeLines(start: Point) = directions.map { direction -> pointsInDirection(start, direction) }

    private fun pointsInDirection(current: Point, direction: Point, points: List<Point> = listOf()): List<Point> {
        val next = current + direction

        return when (trees.containsKey(next)) {
            true -> pointsInDirection(next, direction, points + next)
            else -> points
        }
    }

    private fun countVisible(line: List<Point>, size: Int): Int {
        return when {
            line.isEmpty() -> 0
            trees[line.first()]!! < size -> countVisible(line.drop(1), size) + 1
            else -> 1
        }
    }

    private val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}