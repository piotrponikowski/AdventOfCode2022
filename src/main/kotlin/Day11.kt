class Day11(input: String) {

    private val monkeys = groupLines(input)
        .map { lines -> lines.drop(1) }
        .map { (items, operation, divisor, onTrue, onFalse) ->
            Monkey(
                parseItems(items),
                parseOperation(operation),
                parseDivisor(divisor),
                parseResult(onTrue),
                parseResult(onFalse)
            )
        }

    private val inspectionCounters = monkeys.map { 0L }.toMutableList()
    private val totalDivideBy = monkeys.map { it.divideBy }.reduce { a, b -> a * b }

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

    private fun round(useRelief: Boolean) {
        monkeys.forEachIndexed { monkeyIndex, monkey ->
            monkey.items.forEach { item ->
                inspectionCounters[monkeyIndex]++

                val baseWorryLevel = monkey.operation.calculate(item)
                val worryLevel = when (useRelief) {
                    true -> baseWorryLevel / 3
                    false -> baseWorryLevel % totalDivideBy
                }

                val result = worryLevel % monkey.divideBy == 0L
                val nextMonkeyIndex = if (result) monkey.onTrue else monkey.onFalse

                monkeys[nextMonkeyIndex].items += worryLevel
            }

            monkey.items.clear()
        }
    }

    fun part1() = repeat(20) { round(true) }.run { score() }

    fun part2() = repeat(10000) { round(false) }.run { score() }

    private fun score() = inspectionCounters.sorted()
        .takeLast(2)
        .reduce { score, counter -> score * counter }

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