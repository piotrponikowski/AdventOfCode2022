class Day22(input: String) {

    private val instructionPattern = Regex("""([0-9]+)([A-Z]*)""")

    private val groupedInput = groupLines(input)
    private val boardInput = groupedInput.first()
    private val instructionsInput = groupedInput.last()

    val board = boardInput.flatMapIndexed { y, line -> line.mapIndexed { x, symbol -> Point(x, y) to symbol } }
        .filter { (_, value) -> value in listOf('.', '#') }
        .toMap()

    val instructions = instructionPattern.findAll(instructionsInput.first())
        .toList()
        .flatMap { part ->
            if (part.groups.size == 3) {
                listOf(part.groupValues[1], part.groupValues[2])
            } else {
                listOf(part.groupValues[1])
            }
        }.filter { instruction -> instruction.isNotBlank() }

    private val startingY = board.filter { it.value == '.' }.minOf { it.key.y }
    private val startingX = board.filter { it.value == '.' && it.key.y == startingY }.minOf { it.key.x }

    val startingPoint = Point(startingX, startingY)
    val startingDirection = Point(1, 0)

    fun solve(): Int {
        var position = startingPoint
        var direction = startingDirection

        println("Position: $position, direction: $direction")

        for (instruction in instructions) {
            println("Instruction: $instruction")

            if (instruction == "R") {
                direction = Point(-direction.y, direction.x)
            } else if (instruction == "L") {
                direction = Point(direction.y, -direction.x)
            } else {

                val steps = instruction.toInt()
                repeat(steps) {
                    val newPosition = position + direction
                    val newSymbol = board[newPosition]
                    if (newSymbol == null) {

                        val (wrapPosition, wrapDirection) = wrap(position, direction)

                        if (board[wrapPosition]!! == '.') {
                            position = wrapPosition
                            direction = wrapDirection
                        }
                    } else if (newSymbol == '.') {
                        position = newPosition
                    }
                }

            }

            println("Position: $position, direction: $direction")
            println()
        }

        var directionScore = 0
        if (direction == right) {
            directionScore = 0
        } else if (direction == down) {
            directionScore = 1
        } else if (direction == left) {
            directionScore = 2
        } else if (direction == up) {
            directionScore = 3
        }

        val score = ((position.y + 1) * 1000) + ((position.x + 1) * 4) + directionScore

        println()


        return score
    }

    fun part2() = solve()

    fun wrap(position: Point, direction: Point): Pair<Point, Point> {
        println("Moving outside from: $position, direction: $direction")

        val positionX = position.x
        val positionY = position.y

        var wrapPosition: Point? = null
        var wrapDirection: Point? = null

        if (direction == up) {
            if (positionX in 0..49 && positionY == 100) {
                wrapPosition = Point(50, 50 + positionX)
                wrapDirection = right
            } else if (positionX in 50..99 && positionY == 0) {
                wrapPosition = Point(0, 150 + (positionX - 50))
                wrapDirection = right
            } else if (positionX in 100..149 && positionY == 0) {
                wrapPosition = Point(0 + (positionX - 100), 199)
                wrapDirection = up
            }
        }

        if (direction == down) {
            if (positionX in 0..49 && positionY == 199) {
                wrapPosition = Point(100 + positionX, 0)
                wrapDirection = down
            } else if (positionX in 50..99 && positionY == 149) {
                wrapPosition = Point(49, 150 + (positionX - 50))
                wrapDirection = left
            } else if (positionX in 100..149 && positionY == 49) {
                wrapPosition = Point(99, 50 + (positionX - 100))
                wrapDirection = left
            }
        }

        if (direction == left) {
            if (positionX == 50 && positionY in 0..49) {
                wrapPosition = Point(0, 100 + (49 - positionY))
                wrapDirection = right
            } else if (positionX == 50 && positionY in 50..99) {
                wrapPosition = Point(0 + (positionY - 50), 100)
                wrapDirection = down
            } else if (positionX == 0 && positionY in 100..149) {
                wrapPosition = Point(50, 0 + (149 - positionY))
                wrapDirection = right
            } else if (positionX == 0 && positionY in 150..199) {
                wrapPosition = Point(50 + (positionY - 150), 0)
                wrapDirection = down
            }
        }

        if (direction == right) {
            if (positionX == 149 && positionY in 0..49) {
                wrapPosition = Point(99, 100 + (49 - positionY))
                wrapDirection = left
            } else if (positionX == 99 && positionY in 50..99) {
                wrapPosition = Point(100 + (positionY - 50), 49)
                wrapDirection = up
            } else if (positionX == 99 && positionY in 100..149) {
                wrapPosition = Point(149, 0 + (149 - positionY))
                wrapDirection = left
            } else if (positionX == 49 && positionY in 150..199) {
                wrapPosition = Point(50 + (positionY - 150), 149)
                wrapDirection = up
            }
        }

        return wrapPosition!! to wrapDirection!!
    }

    val right = Point(1, 0)
    val left = Point(-1, 0)
    val down = Point(0, 1)
    val up = Point(0, -1)

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    }
}

fun main() {

    val input = readText("day22.txt")
//    val input = readText("day22.txt", true)

    val result = Day22(input).part2()
    println(result)

}