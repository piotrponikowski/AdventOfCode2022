class Day23(input: List<String>) {

    private val elves = input.flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }
        .filter { (_, symbol) -> symbol == '#' }
        .map { (point, _) -> point }
        .toSet()


    fun part1(): Int {
        var currentState = elves
        var currentPlans = plans

        repeat(10) {
            currentState = step(currentState, currentPlans)
            currentPlans = swapPlans(currentPlans)
        }

        val xMax = currentState.maxOf { point -> point.x }
        val xMin = currentState.minOf { point -> point.x }
        val yMax = currentState.maxOf { point -> point.y }
        val yMin = currentState.minOf { point -> point.y }

        val width = (xMax - xMin) + 1
        val height = (yMax - yMin) + 1

        return (width * height) - currentState.size
    }

    fun part2(): Int {
        var currentState = elves
        var currentPlans = plans

        var lastState = currentState
        var round = 1

        while (true) {
            currentState = step(currentState, currentPlans)
            currentPlans = swapPlans(currentPlans)

            if (currentState == lastState) {
                return round
            }

            round++
            lastState = currentState
        }
    }

    private fun step(state: Set<Point>, plans: List<List<Point>>) = executeMoves(planMoves(state, plans))

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

    private fun executeMoves(plannedMoves: List<Pair<Point, Point>>): Set<Point> {
        val noCollisionMoves = plannedMoves
            .groupBy({ (_, nextPosition) -> nextPosition }, { (position, _) -> position })
            .filter { (_, group) -> group.size == 1 }
            .keys

        val newPositions = plannedMoves.map { (position, nextPosition) ->
            when (nextPosition in noCollisionMoves) {
                true -> nextPosition
                false -> position
            }
        }

        return newPositions.toSet()
    }

    fun swapPlans(plans: List<List<Point>>) = plans.drop(1) + listOf(plans.first())


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