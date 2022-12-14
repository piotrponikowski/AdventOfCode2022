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

    fun part1(): Int {
        while (true) {
            val point = drop()
            
            if (point.y + 1 == maxY) {
                break
            } else {
                board[point] = 'o'
            }
            
            //println(printPoints())
            //println(board.count { (key, value) -> value == 'o' })
        }
        return board.count { (key, value) -> value == 'o' }
    }

    fun part2(): Int {
        while (true) {
            val point = drop()

            if (point == Point(500, 0)) {
                board[point] = 'o'
                break
            } else {
                board[point] = 'o'
            }

            //println(printPoints())
            //println(board.count { (key, value) -> value == 'o' })
        }
        return board.count { (key, value) -> value == 'o' }
    }
    
    fun printPoints(): String {
        val xMax = board.keys.maxOf { point -> point.x }
        val xMin = board.keys.minOf { point -> point.x }
        val yMax = board.keys.maxOf { point -> point.y }
        val yMin = board.keys.minOf { point -> point.y }

        return (yMin..yMax).joinToString("\n") { y ->
            (xMin..xMax).joinToString("") { x ->
                (board[Point(x, y)] ?: ' ').toString()
            }
        }
    }


    private val directions = listOf(Point(0, 1), Point(-1, 1), Point(1, 1))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {

//    val input = readLines("day14.txt")
    val input = readLines("day14.txt", true)

    val day = Day14(input)
    val result = day.part1()

    println(result)

}