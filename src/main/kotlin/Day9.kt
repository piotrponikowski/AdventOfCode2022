import kotlin.math.abs
import kotlin.math.sign

class Day9(input: List<String>) {

    private val instructions = input.map { it.split(" ") }
        .map { (a, b) -> Direction.valueOf(a) to b.toInt() }
    
    fun part1() = solve(2)

    fun part2() = solve(10)

    fun solve(size: Int) :Int {
        var rope = (0 until size).map { Point(0, 0) }
        val visited = mutableSetOf<Point>()
        
        instructions.forEach { (direction, steps) ->
            repeat(steps) {
                rope = moveTail(rope, direction)
                visited += rope.last()
            }
        }
        
        return visited.count()
    }


    private fun moveTail(rope: List<Point>, direction: Direction, newRope: List<Point> = listOf()): List<Point> {
        return if (newRope.isEmpty()) {
            moveTail(rope.drop(1), direction, listOf(rope.first() + direction))

        } else if (rope.isNotEmpty()) {
            val currentHead = newRope.last()
            val currentTail = rope.first()
            val adjustedTail = adjustTail(currentHead, currentTail)

            moveTail(rope.drop(1), direction, newRope + adjustedTail)
        } else {
            newRope
        }
    }

    private fun adjustTail(head: Point, tail: Point) =
        when (head.isNextTo(tail)) {
            true -> tail
            false -> Point(tail.x + (head.x - tail.x).sign, tail.y + (head.y - tail.y).sign)
        }

    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1)
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)

        fun isNextTo(other: Point) = abs(x - other.x) <= 1 && abs(y - other.y) <= 1
    }
}