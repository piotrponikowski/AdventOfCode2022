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

    fun part1(): Long {
        val (boardStates, loopIndex) = solveUntilLoop()
        return calculateScore(boardStates, loopIndex, 2022)
    }

    fun part2(): Long {
        val (boardStates, loopIndex) = solveUntilLoop()
        return calculateScore(boardStates, loopIndex, 1000000000000L)
    }

    private fun solveUntilLoop(): Pair<List<Pair<BoardState, Int>>, Int> {
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


                val movedBlock = currentBlock.map { block -> block + direction }
                val movedMaxX = movedBlock.maxOf { block -> block.x }
                val movedMinX = movedBlock.minOf { block -> block.x }

                if (movedBlock.intersect(board).isEmpty() && movedMinX >= 0 && movedMaxX <= 6) {
                    currentBlock = movedBlock
                }

                val droppedBlock = currentBlock.map { block -> block + down }
                if (droppedBlock.intersect(board).isEmpty() && droppedBlock.minOf { block -> block.y } >= 0) {
                    currentBlock = droppedBlock

                } else {
                    board += currentBlock
                    break
                }
            }

            score = board.maxOf { block -> block.y } + 1
        }
    }

    private fun calculateScore(seen: List<Pair<BoardState, Int>>, loopStartIndex: Int, repeat: Long): Long {
        val startingScore = seen[loopStartIndex - 1].second

        val loopSize = seen.size - loopStartIndex
        val loopCount = (repeat - loopStartIndex) / loopSize
        val loopScore = (seen.last().second - startingScore) * loopCount

        val remainingCount = (repeat - loopStartIndex) % loopSize
        val remainingScore = seen[loopStartIndex + remainingCount.toInt()].second - startingScore

        return startingScore + loopScore + remainingScore
    }

    private fun toBoardState(boardIndex: Int, directionIndex: Int, board: Set<Point>): BoardState {
        val topPoints = (0..6).map { x -> board.filter { point -> point.x == x }.maxOfOrNull { point -> point.y } ?: 0 }
        val minTopPoint = topPoints.min()
        val normalizedTopPoints = topPoints.map { point -> point - minTopPoint }

        return BoardState(boardIndex, directionIndex, normalizedTopPoints)
    }

    data class BoardState(val blockIndex: Int, val directionIndex: Int, val topPoints: List<Int>)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}