class Day12(input: List<String>) {

    val rawPoints = input.map { line -> line.map { value -> value.toString().first() } }
        .flatMapIndexed { y, line -> line.mapIndexed { x, height -> Point(x, y) to height } }.toMap()

    val points = rawPoints.mapValues { (_, b) -> b.code - 'a'.code }

    val start = rawPoints.filter { it.value == 'S' }.map { it.key }.first()
    val end = rawPoints.filter { it.value == 'E' }.map { it.key }
        .first()


    fun solve(): List<Point> {
        val visited = mutableMapOf<Point, Int>()

        val paths = mutableListOf(listOf(start))
        visited[start] = 0

        while (paths.isNotEmpty()) {
            val currentPath = paths.removeFirst()
            val lastPoint = currentPath.last()
            val lastValue = if (lastPoint == start) -1 else points[lastPoint]!!
            
            directions.forEach { direction ->

                val nextPoint = lastPoint + direction
                val nextValue = points[nextPoint]

                if (nextPoint == end && rawPoints[lastPoint]!! == 'z') {
                    return currentPath
                }

                if (nextPoint != end) {
                    if (nextValue != null
                        && (nextValue <= lastValue + 1)
                        && (!visited.containsKey(nextPoint))
                    ) {
                        paths += (currentPath + nextPoint)
                        visited[nextPoint] = currentPath.size


//                        println("Adding ${paths.last()}")
                    }
                }
            }

            println(printPoints(visited.keys))
            println()

        }

        throw RuntimeException("No solution")
    }

    fun part1() = solve().also { t -> println(printPoints(t.toSet())) }

    fun part2() = 2

    private fun printPoints(state: Set<Point>): String {
        val xMax = rawPoints.keys.maxOf { point -> point.x }
        val xMin = rawPoints.keys.minOf { point -> point.x }
        val yMax = rawPoints.keys.maxOf { point -> point.y }
        val yMin = rawPoints.keys.minOf { point -> point.y }

        return (yMin..yMax).joinToString("\n") { y ->
            (xMin..xMax).joinToString("") { x ->
                if (Point(x, y) in state) "#" else "."
            }
        }
    }

    private val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {

    val input = readLines("day12.txt")
//    val input = readLines("day12.txt", true)

    val result = Day12(input).part1()

//    val day = Day12(input)
//    println(day.printPoints(Day12(input).solve()))
    
    println(result)

}