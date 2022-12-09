class Day9(input: List<String>) {

    val instructions = input.map { it.split(" ") }
        .map { (a, b) -> Direction.valueOf(a) to b.toInt() }

    
    var visited = mutableSetOf<Point>()

    fun solve() {
        var head = Point(0, 0)
        var tail = Point(0, 0)

        instructions.forEach { (dir, steps) ->
            (1..steps).forEach { step ->
                head += dir

                if (head.x - tail.x == 2 && head.y == tail.y) {
                    tail += Direction.R
                } else if (head.x - tail.x == -2 && head.y == tail.y) {
                    tail += Direction.L
                } else if (head.y - tail.y == 2 && head.x == tail.x) {
                    tail += Direction.D
                } else if (head.y - tail.y == -2 && head.x == tail.x) {
                    tail += Direction.U
                }
                
                if(tail + Direction.R + Direction.R + Direction.U == head ){
                    tail += Direction.R
                    tail += Direction.U
                } else if(tail + Direction.R + Direction.R + Direction.D == head ){
                    tail += Direction.R
                    tail += Direction.D
                }

                if(tail + Direction.L + Direction.L + Direction.U == head ){
                    tail += Direction.L
                    tail += Direction.U
                } else if(tail + Direction.L + Direction.L + Direction.D == head ){
                    tail += Direction.L
                    tail += Direction.D
                }

                if(tail + Direction.U + Direction.U + Direction.L == head ){
                    tail += Direction.U
                    tail += Direction.L
                } else if(tail + Direction.U + Direction.U + Direction.R == head ){
                    tail += Direction.U
                    tail += Direction.R
                }

                if(tail + Direction.D + Direction.D + Direction.L == head ){
                    tail += Direction.D
                    tail += Direction.L
                } else if(tail + Direction.D + Direction.D + Direction.R == head ){
                    tail += Direction.D
                    tail += Direction.R
                }

                visited += tail
                
                println("$dir $step, $head - $tail")
       
            }
            println(printPoints(visited))
            println()
        }
        
        println(visited.size)

    
    }

    fun part1() = 1

    fun part2() = 2


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

    val result = Day9(input).solve()
    println(result)

}