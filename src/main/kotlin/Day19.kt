class Day19(input: List<String>) {

//    val pattern = Regex(""".*Each (\w+) robot costs (.*)""")

    val blueprints = input.map { line -> line.split(".") }
        .map { robotLines ->
            robotLines.filter { it.isNotBlank() }.map { robotLine ->
                val (typeLise, costLine) = robotLine.split(" robot costs ")
                val type = typeLise.split(" ").last()
                val cost = costLine.split(" and ").map {
                    val (inAmount, inType) = it.split(" ")
                    Cost(inType, inAmount.toInt())
                }.toSet()

                Receipt(type, cost)
            }
        }


    fun solve(blueprint: List<Receipt>) {
        val initState = State(mapOf("ore" to 1), emptyMap())
        var statesToCheck = listOf(initState)

        repeat(24) { minute ->
            println("Minute: $minute")
            val nextStates = mutableSetOf<State>()

            statesToCheck.forEach { stateToCheck ->

                // gathering
                val nextGathered = stateToCheck.gathered.toMutableMap()
                for (robot in stateToCheck.robots) {
                    val alreadyGathered = nextGathered[robot.key] ?: 0
                    val newGathered = alreadyGathered + robot.value
                    nextGathered[robot.key] = newGathered
                }

                nextStates += State(stateToCheck.robots, nextGathered.toMap())

                //constructing
                blueprint.forEach { recipe ->
                    val canConstruct = recipe.cost.all { cost ->
                        val gathered = stateToCheck.gathered[cost.type] ?: 0
                        gathered >= cost.amount
                    }

                    if (canConstruct) {
                        val nextRobots = stateToCheck.robots.toMutableMap()
                        nextRobots[recipe.type] = (nextRobots[recipe.type] ?: 0) + 1

                        val nextGathered2 = nextGathered.toMutableMap()

                        recipe.cost.forEach { cost ->
                            nextGathered2[cost.type] = nextGathered2[cost.type]!! - cost.amount
                        }

                        nextStates += State(nextRobots, nextGathered2.toMap())
                    }
                }
            }

            
            val groupedStates = nextStates.groupBy { it.robots }

            val filteredStates = groupedStates.flatMap { group ->

                val filteredStates = group.value.filter { current ->

                    val better = nextStates.find { other ->
                        current != other && other.robots == current.robots && current.gathered.all { (key, value) ->
                            (other.gathered[key] ?: 0) >= value
                        }
                    }
                    
                    better == null
                }

                filteredStates
            }
            
            println(filteredStates.size)
            statesToCheck = filteredStates.toList()
        }


    }

    fun part1() {
        solve(blueprints[0])
    }

    fun part2() = 2

    data class State(val robots: Map<String, Int>, val gathered: Map<String, Int>)

    data class Cost(val type: String, val amount: Int)
    data class Receipt(val type: String, val cost: Set<Cost>)
}

fun main() {

//    val input = readLines("day19.txt")
    val input = readLines("day19.txt", true)

    val result = Day19(input).part1()
    println(result)

}