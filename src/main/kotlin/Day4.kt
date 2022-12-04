class Day4(input: List<String>) {

    val regex = Regex("""^(\d+)-(\d+),(\d+)-(\d+)$""")
    
    val elfs = input.map { regex.matchEntire(it)!!.destructured }
        .map { (x1, x2, y1, y2) -> x1.toInt()..x2.toInt() to y1.toInt()..y2.toInt() }
        .map { (g1, g2) -> g1.toSet() to g2.toSet() }
    
    
    fun part1() = elfs.map { (p1, p2) -> if (p1.containsAll(p2) || p2.containsAll(p1)) 1 else 0 }.sum()

    fun part2() = elfs.map { (p1, p2) -> if (p1.intersect(p2).isNotEmpty()) 1 else 0 }.sum()
    
}



fun main() {

    val input = readLines("day4.txt")
//    val input = readLines("day4.txt", true);
    
    val result = Day4(input).part2()
    println(result)
    
}