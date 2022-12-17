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

    fun solve(): Pair<List<Pair<BoardState, Int>>, Int> {
        val board = mutableSetOf<Point>()
        val boardStates = mutableListOf<Pair<BoardState, Int>>()

        var blockIndex = 0
        var directionIndex = 0

        var score = 0

        while (true) {
            val boardState = toBoardState(blockIndex, directionIndex, board)
            val loopIndex = boardStates.indexOfFirst { (otherBoardId, _) -> otherBoardId == boardState }
            
            if (loopIndex > -1) {
                return boardStates to loopIndex
            } else {
                boardStates += boardState to score  
            }

            val topPoint = board.maxOfOrNull { point -> point.y } ?: -1
            var currentBlock = blocks[blockIndex].map { point -> Point(point.x + 2, point.y + (topPoint + 4)) }
            blockIndex = (blockIndex + 1) % blocks.size

            while (true) {
                val direction = directions[directionIndex]
                directionIndex = (directionIndex + 1) % directions.size


                val movedBlock = currentBlock.map { it + direction }
                val movedMaxX = movedBlock.maxOf { it.x }
                val movedMinX = movedBlock.minOf { it.x }

                if (movedBlock.intersect(board).isEmpty() && movedMinX >= 0 && movedMaxX <= 6) {
                    currentBlock = movedBlock
                }

                val droppedBlock = currentBlock.map { it + down }
                if (droppedBlock.intersect(board).isEmpty() && droppedBlock.minOf { it.y } >= 0) {
                    currentBlock = droppedBlock

                } else {
                    board += currentBlock
                    break
                }
            }

            score = board.maxOf { it.y } + 1
        }
    }

    private fun part2Score(seen: List<Pair<BoardState, Int>>, loopStartIndex: Int) {
        val loopSize = seen.size - loopStartIndex

        val loopStartScore = seen[loopStartIndex - 1].second
        val loopScore = seen.last().second - loopStartScore

        val blocks = 1000000000000L

        val loopsCount = (blocks - loopStartIndex) / loopSize

        val remainingCount = (blocks - loopStartIndex) % loopSize
        val remainingScore = seen[loopStartIndex + remainingCount.toInt()].second - loopStartScore

        val score = loopStartScore + (loopsCount * loopScore) + remainingScore

        println(score)
        println()
    }

    private fun toBoardState(boardIndex: Int, directionIndex: Int, board: Set<Point>): BoardState {
        val topPoints = (0..6).map { x -> board.filter { point -> point.x == x }.maxOfOrNull { point -> point.y } ?: 0 }
        val minTopPoint = topPoints.min()
        val normalizedTopPoints = topPoints.map { point -> point - minTopPoint }

        return BoardState(boardIndex, directionIndex, normalizedTopPoints)
    }

    fun part1() {
        solve()
    }

    fun part2() {
        val (boardStates, loopIndex) = solve()
        part2Score(boardStates, loopIndex)
    }

    data class BoardState(val blockIndex: Int, val directionIndex: Int, val topPoints: List<Int>)

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