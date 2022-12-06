class Day6(val input: String) {

    private fun solve(size: Int) = input
        .windowed(size)
        .mapIndexed { index, letters -> index to letters }
        .first { (_, letters) -> letters.toSet().size == size }
        .let { (index, _) -> index + size }

    fun part1() = solve(4)

    fun part2() = solve(14)
}
