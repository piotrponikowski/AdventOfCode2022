import kotlin.math.max

class Day16(input: List<String>) {

    private val pattern = Regex("""Valve (.+) has flow rate=(\d+); tunnels? leads? to valves? (.+)""")

    val tunnels = input
        .map { line -> pattern.matchEntire(line)!!.destructured }
        .map { (name, rate, leadsTo) -> Tunnel(name, rate.toInt(), leadsTo.split(", ")) }
        .associateBy { tunnel -> tunnel.name }

    fun solve() {

        var states = mapOf(State("AA", "AA") to 0)
        val visited = setOf<State>()

        repeat(26) { minute ->
            val timeLeft = (26 - minute) - 1
            println("Minute ${minute}, ${states.size}")

            val score = states.values.sorted().reversed().take(1)
            println("Max score: $score")

            val partialStates = mutableMapOf<State, Int>()
            val nextStates = mutableMapOf<State, Int>()

            // me
            for ((currentState, currentScore) in states) {
                val currenTunnel = tunnels[currentState.c1]!!
                val isOpen = currentState.c1 in currentState.opened

                if (!isOpen && currenTunnel.rate > 0) {
                    val nextState = State(currentState.c1, currentState.c2, currentState.opened + currentState.c1)
                    val nextScore = currentScore + (currenTunnel.rate * timeLeft)
                    val existingScore = max(partialStates[nextState] ?: -1, partialStates[nextState.flip()] ?: -1)

                    if (nextScore > existingScore) {
                        partialStates[nextState] = nextScore
                    }
                }

                for (nextName in currenTunnel.leadsTo) {
                    val nextState = State(nextName, currentState.c2, currentState.opened)
                    val nextScore = currentScore
                    val existingScore = partialStates[nextState] ?: -1

                    if (nextScore > existingScore) {
                        partialStates[nextState] = currentScore
                    }
                }
            }

            // elephant
            for ((currentState, currentScore) in partialStates) {
                val currenTunnel = tunnels[currentState.c2]!!
                val isOpen = currentState.c2 in currentState.opened

                if (!isOpen && currenTunnel.rate > 0) {
                    val nextState = State(currentState.c1, currentState.c2, currentState.opened + currentState.c2)
                    val nextScore = currentScore + (currenTunnel.rate * timeLeft)
                    val existingScore = nextStates[nextState] ?: -1

                    if (nextScore > existingScore) {
                        nextStates[nextState] = nextScore
                    }
                }

                for (nextName in currenTunnel.leadsTo) {
                    val nextState = State(currentState.c1, nextName, currentState.opened)
                    val nextScore = currentScore
                    val existingScore = max(nextStates[nextState] ?: -1, nextStates[nextState.flip()] ?: -1)

                    if (nextScore > existingScore) {
                        nextStates[nextState] = currentScore
                    }
                }
            }

            states = nextStates
            println()
        }


    }

    fun part2() {
        solve()
    }


    data class Tunnel(val name: String, val rate: Int, val leadsTo: List<String>)

    data class State(val c1: String, val c2: String, val opened: Set<String> = setOf()) {
        fun flip() = State(c2, c1, opened)
    }
}

fun main() {

//    val input = readLines("day16.txt")
    val input = readLines("day16.txt", true)

    val result = Day16(input).part2()
    println(result)

}