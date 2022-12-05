class Day5(input: String) {

    private val pattern = Regex("""^move (\d+) from (\d+) to (\d+)$""")

    private val groupedInput = groupLines(input)
    private val stacksInput = groupedInput.first()
    private val instructionsInput = groupedInput.last()

    private val stackCount = stacksInput.last().length / 4

    private val stacks = (0..stackCount)
        .map { stackIndex -> stacksInput.map { stack -> stack[stackIndex * 4 + 1] }.filter { tile -> tile.isLetter() } }

    private val instructions = instructionsInput
        .map { line -> pattern.matchEntire(line)!!.destructured }
        .map { (count, from, to) -> Instruction(count.toInt(), from.toInt() - 1, to.toInt() - 1) }

    private fun solve(reversed: Boolean) = instructions
        .fold(stacks) { state, instruction -> executeInstruction(state, instruction, reversed) }
        .map { stack -> stack.first() }
        .joinToString("")

    private fun executeInstruction(stacks: List<List<Char>>, instruction: Instruction, reversed: Boolean) =
        stacks.mapIndexed { index, stack ->
            when (index) {
                instruction.from -> stack.drop(instruction.count)
                instruction.to -> stacks[instruction.from].take(instruction.count)
                    .let { crates -> if (reversed) crates.reversed() else crates } + stack
                else -> stack
            }
        }

    fun part1() = solve(true)

    fun part2() = solve(false)

    data class Instruction(val count: Int, val from: Int, val to: Int)
}