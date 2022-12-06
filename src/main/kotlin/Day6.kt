class Day6(val input: String) {

    private fun solve(size: Int) = input
        .windowed(size)
        .indexOfFirst { letters -> letters.toSet().size == size }
        .let { index -> index + size }

    fun part1() = solve(4)

    fun part2() = solve(14)
}
