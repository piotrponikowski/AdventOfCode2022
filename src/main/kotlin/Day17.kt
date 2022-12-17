class Day17(input: String) {

    val left = Point(-1, 0)
    val right = Point(1, 0)
    val down = Point(0, -1)

    val directions = input.map { symbol ->
        when (symbol) {
            '>' -> right
            else -> left
        }
    }

    val blocks = listOf(
        listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0)),
        listOf(Point(0, 1), Point(1, 0), Point(1, 1), Point(2, 1), Point(1, 2)),
        listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(2, 1), Point(2, 2)),
        listOf(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3)),
        listOf(Point(0, 0), Point(0, 1), Point(1, 0), Point(1, 1))
    )

    fun solve() {

        val board = mutableSetOf<Point>()
        var directionCounter = 0
        repeat(2022) { blockCounter ->
            val top = board.maxOfOrNull { point -> point.y } ?: -1
            var block = blocks[blockCounter % blocks.size].map { point -> Point(point.x + 2, point.y + (top + 4)) }

            while(true) {
                val direction = directions[directionCounter % directions.size ]
                directionCounter += 1
                //println(direction)

                //println(printPoints(board + block))
                
                val movedBlock = block.map { it + direction } 
                val movedMaxX = movedBlock.maxOf { it.x }
                val movedMinX = movedBlock.minOf { it.x }

                if(movedBlock.intersect(board).isEmpty() && movedMinX >=0 && movedMaxX <= 6) {
                    block = movedBlock
                }
                
                val droppedBlock = block.map { it + down }
                if(droppedBlock.intersect(board).isEmpty() && droppedBlock.minOf { it.y } >= 0) {
                    block = droppedBlock
                    
                } else {
                    board += block
                    break
                }
            }
            
//            println()
//            println(printPoints(board))
//            println()
        }
        
        val score = board.maxOf { it.y }
        println(score + 1)

    }

    fun part1() {
        solve()
    }

    fun part2() {
        solve()
    }

    private fun printPoints(state: Set<Point>): String {
        val xMax = 6
        val xMin = 0
        val yMax = state.maxOf { point -> point.y }
        val yMin = state.minOf { point -> point.y }

        return (yMax downTo yMin).joinToString("\n") { y ->
            (xMin..xMax).joinToString("") { x ->
                if (Point(x, y) in state) "#" else "."
            }
        }
    }
    
    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {

    val input = readText("day17.txt")
//    val input = readText("day17.txt", true)

    val result = Day17(input).part1()
    println(result)

}