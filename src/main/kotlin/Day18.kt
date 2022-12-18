class Day18(input: List<String>) {

    private val rock = input.map { line -> line.split(",") }
        .map { (x, y, z) -> Point(x.toInt(), y.toInt(), z.toInt()) }
        .toSet()

    private fun surfacePoints() = rock.flatMap { point -> point.neighbours() } - rock

    fun part1() = surfacePoints().size

    fun part2(): Int {
        val surfacePoints = surfacePoints()
        
        val rangeX = surfacePoints.minOf { it.x }..surfacePoints.maxOf { it.x }
        val rangeY = surfacePoints.minOf { it.y }..surfacePoints.maxOf { it.y }
        val rangeZ = surfacePoints.minOf { it.z }..surfacePoints.maxOf { it.z }

        val outsidePoints = mutableListOf<Point>()
        val pointsToCheck = mutableListOf(Point(rangeX.first, rangeY.first, rangeZ.first))
        
        while (pointsToCheck.isNotEmpty()) {
            val pointToCheck = pointsToCheck.removeFirst()
            
            pointToCheck.neighbours().forEach { neighbour ->
                val inRange = neighbour.x in rangeX && neighbour.y in rangeY && neighbour.z in rangeZ
                val inRock = neighbour in rock
                val visited = neighbour in outsidePoints

                if (inRange && !inRock && !visited) {
                    pointsToCheck += neighbour
                    outsidePoints += neighbour
                }
            }
        }

        return surfacePoints.count { surfacePoint -> surfacePoint in outsidePoints }
    }

    data class Point(val x: Int, val y: Int, val z: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y, z + other.z)

        fun neighbours() = listOf(
            Point(x - 1, y, z), Point(x + 1, y, z),
            Point(x, y - 1, z), Point(x, y + 1, z),
            Point(x, y, z - 1), Point(x, y, z + 1),
        )
    }
}