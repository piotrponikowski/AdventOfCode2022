import kotlin.math.abs
import kotlin.math.sign

class Day9(input: List<String>) {

    private val instructions = input.map { it.split(" ") }
        .map { (a, b) -> Direction.valueOf(a) to b.toInt() }

    var visited = mutableSetOf<Point>()

    fun solve(size: Int): Int {
//        var head = Point(0, 0)
        var rope = (0 until size).map { Point(0, 0) }.toMutableList()

        instructions.forEach { (dir, steps) ->

            (1..steps).forEach { step ->
                val head = rope.first() + dir

                rope[0] = head

                (0 until size - 1).forEach { index ->
                    val h = rope[index]
                    val t = rope[index + 1]

                    rope[index + 1] = moveTail(h, t)

                }

                visited += rope.last()

            }
        }

        return visited.size

    }

    private fun moveTail(head: Point, tail: Point) =
        when (head.isNextTo(tail)) {
            true -> tail
            false -> Point(tail.x + (head.x - tail.x).sign, tail.y + (head.y - tail.y).sign)
        }


    fun part1() = solve(2)

    fun part2() = solve(10)


    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1)
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)

        fun isNextTo(other: Point) = abs(x - other.x) <= 1 && abs(y - other.y) <= 1
    }
}

fun main() {

    val input = readLines("day9.txt")
//    val input = readLines("day9.txt", true)

    val result = Day9(input).part1()
    println(result)

}