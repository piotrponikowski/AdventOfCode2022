import kotlin.math.sign

class Day14(input: List<String>) {

    val groups = input.map { line ->
        line.split(" -> ")
            .map { operation -> operation.split(",") }
            .map { (x, y) -> Point(x.toInt(), y.toInt()) }
    }

    val board = mutableSetOf<Point>()
    val sands = mutableSetOf<Point>()

    init {
        prepareBoard()
    }

    fun prepareBoard() {
        groups.forEach { group ->
            group.windowed(2).forEach { (a, b) ->
                val dx = (b.x - a.x).sign
                val dy = (b.y - a.y).sign
                val d = Point(dx, dy)

                var current = a
                board += current

                while (current != b) {
                    current += d
                    board += current
                }
            }
        }

        println(printPoints())
    }

    val down = Point(0, 1)
    val left = Point(-1, 1)
    val right = Point(1, 1)


    fun dropSand(): Point {
        var sand = start
        val fullState = board + sands

        while (true) {
            var next = sand
            if (sand + down !in fullState) {
                next += down
            } else if (sand + left !in fullState) {
                next += left
            } else if (sand + right !in fullState) {
                next += right
            }

            if (next == sand) {
                sands += sand
                break
            } else if(next.y == maxY){
                sands += sand
                break
            }else {
                sand = next
            }
        }

        return sand
    }

    val start = Point(500, 0)
    val maxY = board.maxOf { it.y } + 2

    fun part1() {

        while (true) {
            sands += dropSand()
            //println(printPoints())
            println(sands.size)
        }
    }

    fun part2() {

        while (true) {
            sands += dropSand()
            //println(printPoints())
            println(sands.size)
        }
    }


    private fun printPoints(): String {
        val xMax = board.maxOf { point -> point.x }
        val xMin = board.minOf { point -> point.x }
        val yMax = board.maxOf { point -> point.y }
        val yMin = board.minOf { point -> point.y }

        return (yMin..yMax).joinToString("\n") { y ->
            (xMin..xMax).joinToString("") { x ->
                when (Point(x, y)) {
                    in board -> "#"
                    in sands -> "o"
                    else -> " "
                }
            }
        }
    }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {

    val input = readLines("day14.txt")
//    val input = readLines("day14.txt", true)

    val day = Day14(input)
    val result = day.part2()

    println(result)

}