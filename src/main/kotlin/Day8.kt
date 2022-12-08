class Day8(input: List<String>) {

    val trees = input.map { line -> line.map { value -> value.toString().toInt() } }
        .flatMapIndexed { y, line -> line.mapIndexed { x, risk -> Point(x, y) to risk } }.toMap()

    fun solve() = trees.map { tree ->

        var isAnyVisible = false

        directions.forEach { dir ->

            var isVisible = true

            var otherPoint = tree.key
            while (otherPoint != null) {
                otherPoint += dir

                val otherValue = trees[otherPoint] ?: break

                if (otherValue >= tree.value) {
                    isVisible = false
                }
            }

            isAnyVisible = isAnyVisible || isVisible
        }

        tree.key to isAnyVisible
    }

    fun solve2() = trees.map { tree ->

        var totalScore = 1
        
        if(tree.key.x == 2 && tree.key.y ==3) {
            println()
        }

        directions.forEach { dir ->

            var score = 0

            var otherPoint = tree.key
            while (otherPoint != null) {
                otherPoint += dir

                val otherValue = trees[otherPoint] ?: break

                if (otherValue < tree.value) {
                    score += 1
                } else {
                    score += 1
                    break
                }
            }

            totalScore *= score
        }

        tree.key to totalScore
    }


    fun part1() = 1

    fun part2() = 2

    private val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {

    val input = readLines("day8.txt")
//    val input = readLines("day8.txt", true)

    val result = Day8(input).solve2().map { it.second }
        .max()
    println(result)

}