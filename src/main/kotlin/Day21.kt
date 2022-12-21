class Day21(input: List<String>) {

    private val numericPattern = Regex("""^\d+$""")

    private val monkeys = input.map { line ->
        val (name, data) = line.split(": ")

        if (data.matches(numericPattern)) {
            ValueMonkey(name, data.toLong())
        } else {
            val (arg1, action, arg2) = data.split(" ")
            ActionMonkey(name, arg1, arg2, action)
        }
    }

    fun part1() = solveRoot(monkeys)

    fun part2(): Long {
        var min = -1_000_000_000_000_000L
        var max = 1_000_000_000_000_000L

        if (solveDiff(prepareHuman(min)) < solveDiff(prepareHuman(max))) {
            min *= -1
            max *= -1
        }

        while (true) {
            val middle = (min + max) / 2
            val value = solveDiff(prepareHuman(middle))

            if (value > 0) {
                min = middle
            } else if (value < 0) {
                max = middle
            } else {
                for (solution in (middle - 10..middle + 10)) {
                    if (solveDiff(prepareHuman(solution)) == 0L) {
                        return solution
                    }
                }
            }
        }
    }

    private fun solve(monkeys: List<Monkey>): Map<String, Long> {
        val solution = mutableMapOf<String, Long>()
        val unsolvedMonkeys = monkeys.toMutableList()

        while (unsolvedMonkeys.isNotEmpty()) {

            val solvedMonkeys = mutableSetOf<Monkey>()
            for (monkey in unsolvedMonkeys) {
                when (monkey) {
                    is ValueMonkey -> {
                        solution[monkey.name] = monkey.value
                        solvedMonkeys += monkey
                    }
                    is ActionMonkey -> {
                        val val1 = solution[monkey.arg1]
                        val val2 = solution[monkey.arg2]

                        if (val1 != null && val2 != null) {
                            solution[monkey.name] = monkey.calculate(val1, val2)
                            solvedMonkeys += monkey
                        }
                    }
                }
            }

            unsolvedMonkeys -= solvedMonkeys
        }

        return solution
    }

    private fun solveRoot(monkeys: List<Monkey>) = solve(monkeys).let { solution -> solution["root"]!! }

    private fun solveDiff(monkeys: List<Monkey>) = solve(monkeys).let { solution ->
        val root = monkeys.filterIsInstance<ActionMonkey>().first { monkey -> monkey.name() == "root" }
        solution[root.arg1]!! - solution[root.arg2]!!
    }

    private fun prepareHuman(value: Long) = monkeys.map { monkey ->
        when (monkey.name()) {
            "humn" -> ValueMonkey("humn", value)
            else -> monkey
        }
    }

    sealed interface Monkey {
        fun name(): String
    }

    data class ValueMonkey(val name: String, val value: Long) : Monkey {
        override fun name() = name
    }

    data class ActionMonkey(val name: String, val arg1: String, val arg2: String, val action: String) : Monkey {
        override fun name() = name

        fun calculate(val1: Long, val2: Long) =
            when (action) {
                "+" -> val1 + val2
                "-" -> val1 - val2
                "*" -> val1 * val2
                "/" -> val1 / val2
                else -> throw RuntimeException("Unknown action: $action")
            }
    }
}