class Day23(input: List<String>) {

    val board = input.flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }
        .toMap()

    val elves = board.filter { it.value == '#' }.keys
    val availablePoints = board.keys

    fun step(state: Set<Point>): Set<Point> {
        val proposedState = mutableSetOf<Pair<Point, Point>>()
        val nextState = mutableSetOf<Point>()


        state.forEach { elf ->
            val anyElfNear = allNeighbours.map { it + elf }.any { it in state }

            if (anyElfNear) {
                val check =
                    adjustedChecks.firstOrNull { check -> check.map { it + elf }.all { cond -> cond !in state } }
                if (check != null) {
                    proposedState.add(elf to (elf + check[1]))
                } else {
                    proposedState.add(elf to elf)
                }
            } else {
                proposedState.add(elf to elf)
            }
        }


        proposedState.forEach { (elf, newPos) ->
            val count = proposedState.count { newPos == it.second }
            if (count == 1) {
                nextState += newPos
            } else {
                nextState += elf
            }
        }

        adjustedChecks = adjustedChecks.drop(1) + listOf(adjustedChecks.first())

        return nextState
    }


    fun part1() :Int {
        var state = elves

        repeat(10) {
            state = step(state)
            
        }

        val xMax = state.maxOf { point -> point.x }
        val xMin = state.minOf { point -> point.x }
        val yMax = state.maxOf { point -> point.y }
        val yMin = state.minOf { point -> point.y }

        val width = (xMax - xMin) + 1
        val height = (yMax - yMin) + 1

        return (width * height) - state.size
    }

    fun part2() :Int {
        var state = elves
        var prevState: Set<Point>

        var round = 1
        while (true) {
            prevState = state
            state = step(state)
            
            if(state == prevState) {
                break
            }


            round++
        }

        return round
    }

    val northCheck = listOf(Point(-1, -1), Point(0, -1), Point(1, -1))
    val southCheck = listOf(Point(-1, 1), Point(0, 1), Point(1, 1))
    val westCheck = listOf(Point(-1, -1), Point(-1, 0), Point(-1, 1))
    val eastCheck = listOf(Point(1, -1), Point(1, 0), Point(1, 1))

    val checks = listOf(northCheck, southCheck, westCheck, eastCheck)
    var adjustedChecks = checks

    val allNeighbours = listOf(
        Point(-1, -1), Point(-1, 0), Point(-1, 1),
        Point(0, -1), Point(0, 1),
        Point(1, -1), Point(1, 0), Point(1, 1),
    )

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {

    val input = readLines("day23.txt")
//    val input = readLines("day23.txt", true)

    val result = Day23(input).part2()
    println(result)

}