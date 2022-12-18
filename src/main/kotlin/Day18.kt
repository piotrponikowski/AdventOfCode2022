class Day18(input: List<String>) {

    private val points = input.map { line -> line.split(",") }
        .map { (x, y, z) -> Point(x.toInt(), y.toInt(), z.toInt()) }
        .toSet()

    private fun surfacePoints() = points.flatMap { point -> neighbours.map { neighbour -> point + neighbour } } - points

    fun part1() = surfacePoints().size

    fun part2(): Int {
        val surfacePoints = surfacePoints()
        val outsidePoints = mutableListOf<Point>()

        val rangeX = surfacePoints.minOf { it.x }..surfacePoints.maxOf { it.x }
        val rangeY = surfacePoints.minOf { it.y }..surfacePoints.maxOf { it.y }
        val rangeZ = surfacePoints.minOf { it.z }..surfacePoints.maxOf { it.z }

        val pointsToCheck = mutableListOf(Point(rangeX.first, rangeY.first, rangeZ.first))
        while (pointsToCheck.isNotEmpty()) {
            val pointToCheck = pointsToCheck.removeFirst()
            neighbours.map { neighbour -> neighbour + pointToCheck }.forEach { neighbour ->
                val inRange = neighbour.x in rangeX && neighbour.y in rangeY && neighbour.z in rangeZ
                val inPoints = neighbour in points
                val visited = neighbour in outsidePoints
                
                if (inRange && !inPoints && !visited) {
                    pointsToCheck += neighbour
                    outsidePoints += neighbour
                }
            }
        }
        
        return surfacePoints.count { surfacePoint -> surfacePoint in outsidePoints }
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