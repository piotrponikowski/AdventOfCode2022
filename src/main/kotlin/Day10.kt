class Day10(input: List<String>) {

    private val instructions = input.map { line ->
        when (line == "noop") {
            true -> NoopInstruction
            else -> AddInstruction(line.split(" ").last().toInt())
        }
    }

    private val registerHistory = instructions.fold(listOf(1)) { state, instruction ->
        when (instruction) {
            is NoopInstruction -> state + state.last()
            is AddInstruction -> state + listOf(state.last(), state.last() + instruction.value)
        }
    }

    fun part1() = listOf(20, 60, 100, 140, 180, 220)
        .sumOf { cycleIndex -> registerHistory[cycleIndex - 1] * cycleIndex }

    fun part2() = registerHistory.dropLast(1).fold(listOf<Char>()) { picture, value ->
        val crtPosition = (picture.size % 40) + 1
        val spritePosition = (value..value + 2)

        when (crtPosition in spritePosition) {
            true -> picture + '#'
            false -> picture + '.'
        }
    }.chunked(40).joinToString(System.lineSeparator()) { chunk -> chunk.joinToString("") }
    
    sealed interface Instruction

    object NoopInstruction : Instruction

    data class AddInstruction(val value: Int) : Instruction
}