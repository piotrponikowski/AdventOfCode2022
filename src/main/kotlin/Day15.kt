import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day15(input: List<String>) {

    private val pattern = Regex("""Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""")

    private val points = input.map { line -> pattern.matchEntire(line)!!.destructured }
        .map { (x1, y1, x2, y2) -> Point(x1.toInt(), y1.toInt()) to Point(x2.toInt(), y2.toInt()) }

    private val sensors = points.map { (sensor, beacon) -> sensor to sensor.distance(beacon) }

    private val beacons = points.map { (_, beacon) -> beacon }

    private fun scanPerRow(sensor: Point, sensorRange: Int, scanRow: Int): IntRange {
        val rowMin = sensor.y - sensorRange
        val rowMax = sensor.y + sensorRange
        val sensorRows = (rowMin..rowMax)

        return if (scanRow in sensorRows) {
            val distance = abs(sensor.y - scanRow)
            val offset = sensorRange - distance
            val points = (sensor.x - offset..sensor.x + offset)

            points
        } else {
            IntRange.EMPTY
        }
    }

    private fun countBeacons(scanY: Int) = beacons
        .filter { beacon -> beacon.y == scanY }
        .map { beacon -> beacon.x }
        .toSet().count()

    fun part1(scanY: Int) = sensors
        .map { (sensor, range) -> scanPerRow(sensor, range, scanY) }
        .map { range -> range.toSet() }
        .reduce { total, range -> total + range }
        .count() - countBeacons(scanY)

    fun part2() = (4000000 downTo 0).asSequence()
        .map { scanY -> scanY to sensors.map { (sensor, range) -> scanPerRow(sensor, range, scanY) } }
        .map { (scanY, ranges) -> scanY to reduceRanges(ranges) }
        .first { (_, ranges) -> ranges.size > 1 }
        .let { (scanY, ranges) -> 
            (ranges.first().last + 1) * 4000000L + scanY 
        }

    private fun reduceRanges(ranges: List<IntRange>): List<IntRange> {
        val sortedRanges = ranges
            .filter { range -> !range.isEmpty() }
            .sortedBy { range -> range.first }

        val reducedRanges = mutableListOf<IntRange>()

        for (current in sortedRanges) {
            if (reducedRanges.isEmpty()) {
                reducedRanges += current
            } else {
                val last = reducedRanges.removeLast()
                if (overlaps(last, current)) {
                    reducedRanges += mergeRanges(last, current)
                } else {
                    reducedRanges += last
                    reducedRanges += current
                }
            }
        }

        return reducedRanges
    }

    private fun overlaps(r1: IntRange, r2: IntRange) = r1.first <= r2.last && r2.first <= r1.last

    private fun mergeRanges(r1: IntRange, r2: IntRange) = (min(r1.first, r2.first)..max(r1.last, r2.last))

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
        fun distance(other: Point) = abs(other.x - x) + abs(other.y - y)
    }
}

fun main() {

    val input = readLines("day15.txt")
//    val input = readLines("day15.txt", true)


    val day = Day15(input)
//    println(day.part1(10))
//    println(day.part1(2000000))

    println(day.part2())
//    println(day.part2(2000000))


}