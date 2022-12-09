class Day9(input: List<String>) {

    val instructions = input.map { it.split(" ") }
        .map { (a, b) -> Direction.valueOf(a) to b.toInt() }


    var visited = mutableSetOf<Point>()

    fun solve(size:Int) :Int {
//        var head = Point(0, 0)
        var rope = (0 until size).map { Point(0, 0) }.toMutableList()

        instructions.forEach { (dir, steps) ->
            
            (1..steps).forEach { step ->
                val head = rope.first() + dir

                rope[0] = head

                (0 until size-1).forEach { index -> 
                    val h = rope[index]
                    val t = rope[index+1]
                    
                    rope[index+1] = adjust(h,t)

                }

                visited += rope.last()
   
            }
        }
        
        return visited.size

    }

    fun adjust(head: Point, tail: Point): Point {
        var b = tail

        if (head.x - tail.x == 2 && head.y == tail.y) {
            b += Direction.R
        } else if (head.x - tail.x == -2 && head.y == tail.y) {
            b += Direction.L
        } else if (head.y - tail.y == 2 && head.x == tail.x) {
            b += Direction.D
        } else if (head.y - tail.y == -2 && head.x == tail.x) {
            b += Direction.U
        }

        if (tail + Direction.R + Direction.R + Direction.U == head) {
            b += Direction.R
            b += Direction.U
        } else if (tail + Direction.R + Direction.R + Direction.D == head) {
            b += Direction.R
            b += Direction.D
        }

        if (tail + Direction.L + Direction.L + Direction.U == head) {
            b += Direction.L
            b += Direction.U
        } else if (tail + Direction.L + Direction.L + Direction.D == head) {
            b += Direction.L
            b += Direction.D
        }

        if (tail + Direction.U + Direction.U + Direction.L == head) {
            b += Direction.U
            b += Direction.L
        } else if (tail + Direction.U + Direction.U + Direction.R == head) {
            b += Direction.U
            b += Direction.R
        }

        if (tail + Direction.D + Direction.D + Direction.L == head) {
            b += Direction.D
            b += Direction.L
        } else if (tail + Direction.D + Direction.D + Direction.R == head) {
            b += Direction.D
            b += Direction.R
        }

        if (tail + Direction.R + Direction.R + Direction.U + Direction.U  == head) {
            b += Direction.R
            b += Direction.U
        } else if (tail + Direction.R + Direction.R + Direction.D + Direction.D  == head) {
            b += Direction.R
            b += Direction.D
        }

        if (tail + Direction.L + Direction.L + Direction.U + Direction.U  == head) {
            b += Direction.L
            b += Direction.U
        } else if (tail + Direction.L + Direction.L + Direction.D + Direction.D  == head) {
            b += Direction.L
            b += Direction.D
        }
        
        return b
    }

    fun part1() = solve(2)

    fun part2() = solve(10)


    private fun printPoints(state: Set<Point>): String {
        val xMax = state.maxOf { point -> point.x }
        val xMin = state.minOf { point -> point.x }
        val yMax = state.maxOf { point -> point.y }
        val yMin = state.minOf { point -> point.y }

        return (yMin..yMax).joinToString("\n") { y ->
            (xMin..xMax).joinToString("") { x ->
                if (Point(x, y) in state) "#" else " "
            }
        }
    }


    enum class Direction(val x: Int, val y: Int) {
        L(-1, 0), R(1, 0), U(0, -1), D(0, 1)
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Direction) = Point(x + other.x, y + other.y)
    }
}

fun main() {

    val input = readLines("day9.txt")
//    val input = readLines("day9.txt", true)

    val result = Day9(input).part1()
    println(result)

}