class Day3(input: List<String>) {

    val r1 = input.map { it.substring(0, it.length / 2).toCharArray().toSet() to it.substring(it.length / 2).toCharArray().toSet()   }
    val r2 = r1.map { (p1, p2) -> p1.intersect(p2) }
    val r3 = r2.map { it.toTypedArray()[0] }
    val r4 = r3.map { 
        if(it.isLowerCase()) {
             it.code - 96
        } else {
            it.code - 38 
        }
    }
    
    val p1 = input.map { it.toCharArray().toList() }
    val p2 = p1.chunked(3);
    val p3 = p2.map { (t1, t2, t3) -> t1.intersect(t2).intersect(t3) }
    val p4 = p3.map { it.toTypedArray()[0] }
    val p5 = p4.map {
        if(it.isLowerCase()) {
            it.code - 96
        } else {
            it.code - 38
        }
    }
    
    fun part1() = r4.sum()

    fun part2() = p5.sum()
}

fun main() {

    val input = readLines("day3.txt")
//    val input = readLines("day3.txt", true)
    
    val result = Day3(input).part2()
    
    
    println('a'.code - 96)
    println('A'.code - 38)


    println(result)
}