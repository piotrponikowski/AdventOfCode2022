class Day1(input: String) {

    private val calories = groupLines(input).map { elf -> elf.sumOf { food -> food.toInt() } }

    fun part1() = calories.max()

    fun part2() = calories.sortedDescending().take(3).sum()
}