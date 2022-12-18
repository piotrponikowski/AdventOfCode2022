class Day18(input: List<String>) {

    private val points = input.map { line -> line.split(",") }
        .map { (x, y, z) -> Point(x.toInt(), y.toInt(), z.toInt()) }
        .toSet()

    private fun surfacePoints() = points.flatMap { point -> neighbours.map { neighbour -> point + neighbour } } - points

    fun part1() = surfacePoints().size

    fun part2(): Int {
        val surfacePoints = surfacePoints()
        val groups = mutableListOf<Set<Point>>()

        for (surfacePoint in surfacePoints) {
            val alreadyGrouped = groups.any { group -> surfacePoint in group }
            if (alreadyGrouped) {
                continue
            }

            val pointsToCheck = mutableListOf(surfacePoint)
            val group = mutableSetOf(surfacePoint)

            while (pointsToCheck.isNotEmpty()) {
                val pointToCheck = pointsToCheck.removeFirst()

                neighbours.forEach { neighbour ->
                    val nextPoint = neighbour + pointToCheck
                    if (nextPoint in surfacePoints && nextPoint !in group) {
                        pointsToCheck += nextPoint
                        group += nextPoint
                    }
                }
            }

            groups += group
        }

        val innerGroups = groups.filter { group ->
            group.all { point ->
                neighbours
                    .map { neighbour -> neighbour + point }
                    .all { neighbour -> neighbour in group || neighbour in points }
            }
        }
        
        val s1 = groups.flatten().size
        val s2 = surfacePoints.size
        val notGrouped = groups.flatten() - surfacePoints.toSet()

        return (surfacePoints - innerGroups.flatten().toSet()).size
    }

    private val neighbours = listOf(
        Point(-1, 0, 0), Point(1, 0, 0),
        Point(0, -1, 0), Point(0, 1, 0),
        Point(0, 0, -1), Point(0, 0, 1),
    )

    data class Point(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)
    }
}

fun main() {

    val input = readLines("day18.txt")
//    val input = readLines("day18.txt", true)

    val result = Day18(input).part2()
    println(result)

}