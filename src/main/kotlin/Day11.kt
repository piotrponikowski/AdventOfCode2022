class Day11(input: String) {

    private val monkeys = input.split(System.lineSeparator().repeat(2))
        .map { data -> data.split(System.lineSeparator()).drop(1) }
        .map { (itemsLine, operationLine, divisorLine, trueLine, falseLine) ->
            val items = parseItems(itemsLine)
            val operation = parseOperation(operationLine)
            val divideBy = parseDivisor(divisorLine)
            val onTrue = parseResult(trueLine)
            val onFalse = parseResult(falseLine)
            Monkey(items, operation, divideBy, onTrue, onFalse)
        }

    private val inspectionCounters = monkeys.map { 0L }.toMutableList()
    private val prime = monkeys.map { it.divideBy }.reduce { a, b -> a * b }

    private fun parseItems(line: String) = line.replace("Starting items: ", "")
        .split(",").map { item -> item.trim().toLong() }.toMutableList()

    private fun parseOperation(line: String): Operation {
        val (_, operator, arg2) = line.replace("Operation: new = ", "").trim().split(" ")

        return when {
            operator == "+" && arg2 == "old" -> AddOldOperation
            operator == "+" -> AddOperation(arg2.toLong())
            operator == "*" && arg2 == "old" -> MulOldOperation
            operator == "*" -> MulOperation(arg2.toLong())
            else -> throw IllegalArgumentException("Unknown operation: $operator, $arg2")
        }
    }
    
    private fun parseDivisor(line: String) = line.split(" ").last().toLong()

    private fun parseResult(line: String) = line.split(" ").last().toInt()

    fun round() {

        monkeys.forEachIndexed { index, monkey ->
            //println("Monkey ${index}:")
            monkey.items.forEach { item ->
                //println("Monkey inspects an item with a worry level of ${item}.")

                inspectionCounters[index] += 1L

                val worryLevel = monkey.operation.calculate(item)
                //println("Worry level is calculated to ${worryLevel}.")

                val worryLevel2 = worryLevel
                //println("Monkey gets bored with item. Worry level is divided by 3 to ${worryLevel2}.")

                if (worryLevel2 % monkey.divideBy == 0L) {
                    //println("Current worry level is divisible by ${monkey.divideBy}.")
                    monkeys[monkey.onTrue].items += (worryLevel2 % prime)
                    //println("Item with worry level ${worryLevel2} is thrown to monkey ${monkey.onTrue}.")
                } else {
                    //println("Current worry level is not divisible by ${monkey.divideBy}.")
                    monkeys[monkey.onFalse].items += (worryLevel2 % prime)
                    //println("Item with worry level ${worryLevel2} is thrown to monkey ${monkey.onFalse}.")
                }
            }

            monkey.items.clear()
            //println()
        }

        //monkeys.forEachIndexed { index, monkey -> println("$index -> ${monkey.items}") }
        //println()

    }

    fun part1(): Long {
        //println("prime: ${prime}")

        repeat(20) {
            round()
            //println(counters)
        }

        return inspectionCounters.sorted().takeLast(2).reduce { a, b -> a * b }
    }

    fun part2(): Long {
        //println("prime: ${prime}")

        repeat(10000) {
            round()
            //println(counters)
        }

        return inspectionCounters.sorted().takeLast(2).reduce { a, b -> a * b }
    }

    sealed interface Operation {
        fun calculate(other: Long): Long
    }

    data class MulOperation(val value: Long) : Operation {
        override fun calculate(other: Long) = value * other
    }

    object MulOldOperation : Operation {
        override fun calculate(other: Long) = other * other
    }

    data class AddOperation(val value: Long) : Operation {
        override fun calculate(other: Long) = value + other
    }

    object AddOldOperation : Operation {
        override fun calculate(other: Long) = other + other
    }

    data class Monkey(
        val items: MutableList<Long>,
        val operation: Operation,
        val divideBy: Long,
        val onTrue: Int,
        val onFalse: Int
    )
}

fun main() {

    val input = readText("day11.txt")
//    val input = readText("day11.txt", true)

    val result = Day11(input).part1()
    println(result)

}