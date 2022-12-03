class Day3(private val input: List<String>) {

    fun part1() = solve(input.map { items -> items.chunked(items.length / 2) })

    fun part2() = solve(input.chunked(3))

    private fun solve(groups: List<List<String>>) = groups
        .map { group -> commonItem(group) }
        .sumOf { commonItem -> score(commonItem) }

    private fun commonItem(group: List<String>) = group
        .map { items -> items.toSet() }
        .reduce { prev, curr -> prev.intersect(curr) }
        .first()

    private fun score(item: Char) = when (item.isLowerCase()) {
        true -> item.code - 96
        false -> item.code - 38
    }
}