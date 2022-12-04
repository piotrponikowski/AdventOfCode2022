class Day4(input: List<String>) {

    private val pattern = Regex("""^(\d+)-(\d+),(\d+)-(\d+)$""")

    private val pairs = input.map { pattern.matchEntire(it)!!.destructured }
        .map { (from1, to1, from2, to2) -> from1.toInt()..to1.toInt() to from2.toInt()..to2.toInt() }
        .map { (range1, range2) -> range1.toSet() to range2.toSet() }


    fun part1() = pairs.count { (range1, range2) -> range1.containsAll(range2) || range2.containsAll(range1) }

    fun part2() = pairs.count { (range1, range2) -> range1.intersect(range2).isNotEmpty() }

}