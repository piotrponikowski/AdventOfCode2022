class Day1(input: String) {

    private val calories = input
        .split(System.lineSeparator().repeat(2))
        .map { elf -> elf.split(System.lineSeparator()).sumOf { food -> food.toInt() } }

    fun part1() = calories.max()

    fun part2() = calories.sortedDescending().take(3).sum()
}