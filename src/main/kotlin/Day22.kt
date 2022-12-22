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

    fun solve():Int {
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
                    if(newSymbol == null) {
                        
                        var wrapX = newPosition.x
                        var wrapY = newPosition.y

                        if(direction == right) {
                            wrapX = board.filter { it.key.y == newPosition.y }.minOf { it.key.x }
                        } else if(direction == left) {
                            wrapX = board.filter { it.key.y == newPosition.y }.maxOf { it.key.x }
                        } else  if(direction == down) {
                            wrapY = board.filter { it.key.x == newPosition.x }.minOf { it.key.y }
                        } else if(direction == up) {
                            wrapY = board.filter { it.key.x == newPosition.x }.maxOf { it.key.y }
                        }
                        
                        val wrapPosition = Point(wrapX, wrapY)
                        if(board[wrapPosition]!! == '.') {
                            position = wrapPosition  
                        }
                    } else if(newSymbol == '.') {
                        position = newPosition
                    } 
                }
                
            }
            
            println("Position: $position, direction: $direction")
            println()
        }
        
        var directionScore = 0
        if(direction == right) {
            directionScore = 0
        } else if(direction == down) {
            directionScore = 1
        } else if(direction == left) {
            directionScore = 2
        } else if(direction == up) {
            directionScore = 3
        }
        
        val score = ((position.y+1) * 1000) + ((position.x+1) * 4) + directionScore
        
        println()
        
        
        return score
    }

    fun part1() =solve()
    

    fun part2()  {
        val xMax = board.keys.maxOf { point -> point.x }
        val xMin = board.keys.minOf { point -> point.x }
        val yMax = board.keys.maxOf { point -> point.y }
        val yMin = board.keys.minOf { point -> point.y }

        val width = xMax+1
        val height = yMax+1
        
        val cubeSize= width / 4


        println()
    }


    private fun printPoints(state: Map<Point, Char>): String {
        val xMax = state.keys.maxOf { point -> point.x }
        val xMin = state.keys.minOf { point -> point.x }
        val yMax = state.keys.maxOf { point -> point.y }
        val yMin = state.keys.minOf { point -> point.y }

        return (yMin..yMax).joinToString("\n") { y ->
            (xMin..xMax).joinToString("") { x ->
                (state[Point(x, y)] ?: ' ').toString()
            }
        }
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

//    val input = readText("day22.txt")
    val input = readText("day22.txt", true)

    val result = Day22(input).part2()
    println(result)

}