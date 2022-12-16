import kotlin.math.max

class Day16(input: List<String>) {

    private val pattern = Regex("""Valve (.+) has flow rate=(\d+); tunnels? leads? to valves? (.+)""")

    val tunnels = input
        .map { line -> pattern.matchEntire(line)!!.destructured }
        .map { (name, rate, leadsTo) -> Tunnel(name, rate.toInt(), leadsTo.split(", ")) }
        .associateBy { tunnel -> tunnel.name }

    fun solve(initState: Pair<State, Int>, minutes: Int): Map<State, Int> {

        var states = mapOf(initState)

        repeat(minutes) { minute ->
            val timeLeft = (minutes - minute) - 1
            //println("Minute ${minute}, ${states.size}")

            val nextStates = mutableMapOf<State, Int>()

            for ((currentState, currentScore) in states) {
                val currenTunnel = tunnels[currentState.currentName]!!
                val isOpen = currentState.currentName in currentState.opened

                if (!isOpen && currenTunnel.rate > 0) {
                    val nextState = State(currentState.currentName, currentState.opened + currentState.currentName)
                    val nextScore = currentScore + (currenTunnel.rate * timeLeft)
                    val existingScore = nextStates[nextState] ?: -1

                    if (nextScore > existingScore) {
                        nextStates[nextState] = nextScore
                    }
                }

                for (nextName in currenTunnel.leadsTo) {
                    val nextState = State(nextName, currentState.opened)
                    val nextScore = currentScore
                    val existingScore = nextStates[nextState] ?: -1

                    if (nextScore > existingScore) {
                        nextStates[nextState] = currentScore
                    }
                }
            }

            states = nextStates
        }

        return states
    }

    fun part1(): Int {
        val initialState = State("AA") to 0
        val states = solve(initialState, 30)
        val bestState = states.entries.maxBy { entry -> entry.value }
        return bestState.value
    }

    fun part2(): Int {
        val initialState = State("AA") to 0
        val states = solve(initialState, 26)
        val sortedStates = states.entries.toList().sortedBy { it.value }.reversed()
        var maxScore = 0
        var counter = 0
        
        for(entry in sortedStates) {
            counter++
            val newStart = State("AA", entry.key.opened) to entry.value
            val nextStates = solve(newStart, 26)
            val bestState = nextStates.entries.maxBy { entry2 -> entry2.value }
            if(bestState.value > maxScore) {
                maxScore = bestState.value
                println(maxScore)
            }
        }
        
        return maxScore
    }

    data class Tunnel(val name: String, val rate: Int, val leadsTo: List<String>)

    data class State(val currentName: String, val opened: List<String> = listOf())
}

fun main() {

    val input = readLines("day16.txt")
//    val input = readLines("day16.txt", true)

    val result = Day16(input).part2()
    println(result)

}