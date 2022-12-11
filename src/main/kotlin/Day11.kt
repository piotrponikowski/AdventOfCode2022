class Day11(input: String) {

    val monkeys = input.split(System.lineSeparator().repeat(2))
        .map { data ->
            val lines = data.split(System.lineSeparator())
            val items = lines[1].replace("Starting items: ", "").split(",").map { it.trim().toInt() }
            val operation = parseOperation(lines[2])
            val divideBy = lines[3].split(" ").last().toInt()
            val onTrue = lines[4].split(" ").last().toInt()
            val onFalse = lines[5].split(" ").last().toInt()

            Monkey(items.toMutableList(), operation, divideBy, onTrue, onFalse)
        }

    val counters = monkeys.map { 0 }.toMutableList()
    
    fun parseOperation(line: String): Operation {
        val operationData = line.replace("Operation: new = ", "").trim().split(" ")
        val (arg1, operator, arg2) = operationData

        if (operator == "+") {
            if (arg2 == "old") {
                return AddOldOperation
            } else {
                return AddOperation(arg2.toInt())
            }
        } else if (operator == "*") {
            if (arg2 == "old") {
                return MulOldOperation
            } else {
                return MulOperation(arg2.toInt())
            }
        } else {
            throw RuntimeException("Unknown operation: $arg1 $operator $arg2")
        }
    }


    fun round() {

        monkeys.forEachIndexed { index, monkey ->
            //println("Monkey ${index}:")
            monkey.items.forEach { item ->
                //println("Monkey inspects an item with a worry level of ${item}.")

                counters[index] += 1
                
                val worryLevel = monkey.operation.calculate(item)
                //println("Worry level is calculated to ${worryLevel}.")
                
                val worryLevel2 = worryLevel / 3
                //println("Monkey gets bored with item. Worry level is divided by 3 to ${worryLevel2}.")
                
                if(worryLevel2 % monkey.divideBy == 0) {
                    //println("Current worry level is divisible by ${monkey.divideBy}.")
                    monkeys[monkey.onTrue].items += worryLevel2
                    //println("Item with worry level ${worryLevel2} is thrown to monkey ${monkey.onTrue}.")
                } else {
                    //println("Current worry level is not divisible by ${monkey.divideBy}.")
                    monkeys[monkey.onFalse].items += worryLevel2
                    //println("Item with worry level ${worryLevel2} is thrown to monkey ${monkey.onFalse}.")
                }
            }
            
            monkey.items.clear()
            //println()
        }
        
        monkeys.forEachIndexed { index, monkey -> println("$index -> ${monkey.items}") }
        println()

    }
    
    fun part1():Int {
        repeat(20) {
            round()
            println(counters)
        }
        
        return counters.sorted().takeLast(2).reduce {a, b -> a*b}
    }

    fun part2() = 2

    sealed interface Operation {
        fun calculate(other: Int): Int
    }

    data class MulOperation(val value: Int) : Operation {
        override fun calculate(other: Int) = value * other
    }

    object MulOldOperation : Operation {
        override fun calculate(other: Int) = other * other
    }

    data class AddOperation(val value: Int) : Operation {
        override fun calculate(other: Int) = value + other
    }

    object AddOldOperation : Operation {
        override fun calculate(other: Int) = other + other
    }

    data class Monkey(
        val items: MutableList<Int>,
        val operation: Operation,
        val divideBy: Int,
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