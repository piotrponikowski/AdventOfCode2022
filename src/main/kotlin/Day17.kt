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

        val seen = mutableListOf<Pair<Condition, Int>>()
        var score = 0
        var blockCounter = 0
        while (true) {

            val top = board.maxOfOrNull { point -> point.y } ?: -1
            var block = blocks[blockCounter % blocks.size].map { point -> Point(point.x + 2, point.y + (top + 4)) }


            val blockSignature = blockCounter % blocks.size
            val directionSignature = directionCounter % directions.size
            val boardSignature = signature(board)

            val id = Condition(blockSignature, directionSignature, boardSignature)
            if (seen.find { it.first == id } != null) {
                println("loop")
                part2Score(id, seen)
                
                break
            }

            seen += id to score

            while (true) {
                val direction = directions[directionCounter % directions.size]
                directionCounter += 1
                //println(direction)

                //println(printPoints(board + block))

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

//            println()
//            println(printPoints(board))
//            println()
            score = board.maxOf { it.y } + 1

            blockCounter++
        }


//        val count = 1000000000000L
//        val loopScore = seen[seen.size - 2].second
//        val loopSize = seen.size - 1
//
//        val willRepeat = count / loopSize
//
//        val result = loopScore * willRepeat
//        println(result)
//        println()

    }

    private fun part2Score(id: Condition, seen: List<Pair<Condition, Int>>) {
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

    data class Condition(val block: Int, val direction: Int, val top: List<Int>)

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