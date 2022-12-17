class Day17(input: String) {

    private val left = Point(-1, 0)
    private val right = Point(1, 0)
    private val down = Point(0, -1)

    private val directions = input.map { symbol ->
        when (symbol) {
            '>' -> right
            else -> left
        }
    }

    private val blocks = listOf(
        listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(3, 0)),
        listOf(Point(0, 1), Point(1, 0), Point(1, 1), Point(2, 1), Point(1, 2)),
        listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(2, 1), Point(2, 2)),
        listOf(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3)),
        listOf(Point(0, 0), Point(0, 1), Point(1, 0), Point(1, 1))
    )

    fun solve() {
        val board = mutableSetOf<Point>()
        val seen = mutableListOf<Pair<State, Int>>()

        var blockIndex = 0
        var directionIndex = 0
        
        var score = 0
  
        while (true) {
            val boardSignature = signature(board)

            val id = State(blockIndex, directionIndex, boardSignature)
            if (seen.find { it.first == id } != null) {
                println("loop")
                part2Score(id, seen)
                
                break
            }

            seen += id to score

            val top = board.maxOfOrNull { point -> point.y } ?: -1
            var block = blocks[blockIndex].map { point -> Point(point.x + 2, point.y + (top + 4)) }
            blockIndex = (blockIndex + 1) % blocks.size

            while (true) {
                val direction = directions[directionIndex]
                directionIndex = (directionIndex + 1) % directions.size


                val movedBlock = block.map { it + direction }
                val movedMaxX = movedBlock.maxOf { it.x }
                val movedMinX = movedBlock.minOf { it.x }

                if (movedBlock.intersect(board).isEmpty() && movedMinX >= 0 && movedMaxX <= 6) {
                    block = movedBlock
                }

                val droppedBlock = block.map { it + down }
                if (droppedBlock.intersect(board).isEmpty() && droppedBlock.minOf { it.y } >= 0) {
                    block = droppedBlock

                } else {
                    board += block
                    break
                }
            }

            score = board.maxOf { it.y } + 1


        }

    }

    private fun part2Score(id: State, seen: List<Pair<State, Int>>) {
        val loopStartIndex = seen.indexOfFirst { it.first == id }
        val loopSize = seen.size - loopStartIndex
        
        val loopStartScore = seen[loopStartIndex-1].second
        val loopScore = seen.last().second - loopStartScore

        val blocks = 1000000000000L
        
        val loopsCount = (blocks - loopStartIndex) / loopSize


        val remainingCount = (blocks - loopStartIndex) % loopSize
        val remainingScore = seen[loopStartIndex + remainingCount.toInt()].second - loopStartScore
        
        val score = loopStartScore + (loopsCount * loopScore) + remainingScore
        
        println(score)
        println()
    }

    private fun signature(board: Set<Point>): List<Int> {
        val sign = (0..6).map { x -> board.filter { it.x == x }.maxOfOrNull { it.y } ?: 0 }
        val min = sign.min()

        return sign.map { it - min }
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

    data class State(val blockIndex: Int, val directionIndex: Int, val top: List<Int>)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {

    val input = readText("day17.txt")
//    val input = readText("day17.txt", true)

    val result = Day17(input).part2()
    println(result)

}