import kotlin.math.sign

class Day14(input: List<String>) {

    private val groups = input.map { line -> parseLine(line) }

    private val board = groups
        .flatMap { group -> group.windowed(2).flatMap { (a, b) -> linePoints(a, b) } }
        .associateWith { '#' }
        .toMutableMap()

    private val maxY = board.keys.maxOf { point -> point.y + 2 }

    private fun parseLine(line: String) = line.split(" -> ")
        .map { operation -> operation.split(",") }
        .map { (x, y) -> Point(x.toInt(), y.toInt()) }

    private fun linePoints(from: Point, to: Point, result: List<Point> = listOf()): List<Point> {
        return if (from == to) {
            result + from
        } else {
            val direction = Point((to.x - from.x).sign, (to.y - from.y).sign)
            val nextFrom = from + direction
            linePoints(nextFrom, to, result + from)
        }
    }

    private fun drop(current: Point = Point(500, 0)): Point {
        val next = directions
            .map { direction -> current + direction }
            .firstOrNull { point -> point !in board }

        return if (next != null && next.y < maxY) {
            drop(next)
        } else {
            current
        }
    }

    fun part1() = solve { point -> point.y + 1 == maxY }

    fun part2() = solve { point -> point == Point(500, 0) } + 1

    fun solve(endCondition: (Point) -> Boolean): Int {
        while (true) {
            val point = drop()
            when (endCondition(point)) {
                true -> break
                false -> board[point] = 'o'
            }
        }

        return board.count { (_, value) -> value == 'o' }
    }

    private val directions = listOf(Point(0, 1), Point(-1, 1), Point(1, 1))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}