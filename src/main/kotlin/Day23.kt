class Day23(input: List<String>) {

    private val elves = input.flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }
        .filter { (_, symbol) -> symbol == '#' }
        .map { (point, _) -> point }
        .toSet()

    private fun planMoves(state: Set<Point>, plans: List<List<Point>>) = state
        .map { position ->
            val isNotAlone = neighbours
                .map { neighbour -> neighbour + position }
                .any { neighbour -> neighbour in state }

            val nextPlan = plans
                .map { plan -> plan.map { direction -> direction + position } }
                .firstOrNull { plan -> plan.all { point -> point !in state } }

            if (isNotAlone && nextPlan != null) {
                position to nextPlan[1]
            } else {
                position to position
            }
        }


    private fun executeMoves(plannedPositions: List<Pair<Point, Point>>) = plannedPositions
        .map { (position, nextPosition) ->
            when (plannedPositions.count { (_, otherPosition) -> nextPosition == otherPosition }) {
                1 -> nextPosition
                else -> position
            }
        }.toSet()

    private fun step(state: Set<Point>, plans: List<List<Point>>) = executeMoves(planMoves(state, plans))

    fun part1(): Int {
        var state = elves
        var plans = plans

        repeat(10) {
            state = step(state, plans)
            plans = plans.drop(1) + listOf(plans.first())
        }

        val xMax = state.maxOf { point -> point.x }
        val xMin = state.minOf { point -> point.x }
        val yMax = state.maxOf { point -> point.y }
        val yMin = state.minOf { point -> point.y }

        val width = (xMax - xMin) + 1
        val height = (yMax - yMin) + 1

        return (width * height) - state.size
    }

    fun part2(): Int {
        var state = elves
        var plans = plans

        var prevState: Set<Point>

        var round = 1
        while (true) {
            prevState = state

            state = step(state, plans)
            plans = plans.drop(1) + listOf(plans.first())

            if (state == prevState) {
                break
            }


            round++
        }

        return round
    }


    private val neighbours = (-1..1).flatMap { x -> (-1..1).map { y -> Point(x, y) } } - Point(0, 0)

    private val plans = listOf(
        listOf(Point(-1, -1), Point(0, -1), Point(1, -1)),
        listOf(Point(-1, 1), Point(0, 1), Point(1, 1)),
        listOf(Point(-1, -1), Point(-1, 0), Point(-1, 1)),
        listOf(Point(1, -1), Point(1, 0), Point(1, 1)),
    )

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}