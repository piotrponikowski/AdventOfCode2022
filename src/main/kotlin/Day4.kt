class Day4(input: List<String>) {

    private val pairs = input
        .map { line -> line.split(",", "-").map { section -> section.toInt() } }
        .map { (from1, to1, from2, to2) -> from1..to1 to from2..to2 }
        .map { (range1, range2) -> range1.toSet() to range2.toSet() }

    fun part1() = pairs.count { (range1, range2) -> range1.containsAll(range2) || range2.containsAll(range1) }

    fun part2() = pairs.count { (range1, range2) -> range1.intersect(range2).isNotEmpty() }
}