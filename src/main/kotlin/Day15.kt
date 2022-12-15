import kotlin.math.abs
import kotlin.math.absoluteValue

class Day15(input: List<String>) {

    val pattern = Regex("""Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")

    val points = input.map { line -> pattern.matchEntire(line)!!.destructured }
        .map { (x1, y1, x2, y2) -> Point(x1.toInt(), y1.toInt()) to Point(x2.toInt(), y2.toInt()) }

    val distances = points.map { (p1, p2) -> p1 to p1.distance(p2) }


    val minX = points.minOf { (p1, p2) -> p1.x - p1.distance(p2).absoluteValue }
    val maxX = points.maxOf { (p1, p2) -> p1.x + p1.distance(p2).absoluteValue }

    val y = 2000000

    fun part1() = (minX..maxX).count { x ->
        val ref = Point(x, y)

        val isBeacon = points.any { (p1, p2) -> p2 == ref }

        if (isBeacon) {
            false
        } else {
            distances.any { (other, range) -> other.distance(ref) <= range }
        }
    }

    fun part2() = 2

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
        fun distance(other: Point) = abs(other.x - x) + abs(other.y - y)
    }
}

fun main() {

    val input = readLines("day15.txt")
//    val input = readLines("day15.txt", true)

    val day = Day15(input)
    val result = day.points.forEach { (p1, p2) -> println("$p1 $p2 ${p1.distance(p2)}") }

    println(result)

    println(day.minX)
    println(day.maxX)

    println(day.part1())

}