class Day16(input: List<String>) {

    private val pattern = Regex("""Valve (.+) has flow rate=(\d+); tunnels? leads? to valves? (.+)""")

    private val tunnels = input
        .map { line -> pattern.matchEntire(line)!!.destructured }
        .map { (name, rate, leadsTo) -> Tunnel(name, rate.toInt(), leadsTo.split(", ")) }
        .associateBy { tunnel -> tunnel.name }

    fun solve(initState: Pair<State, Int>, minutes: Int): Map<State, Int> {
        var states = mapOf(initState)

        repeat(minutes) { minute ->
            val timeLeft = (minutes - minute) - 1
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
                    val existingScore = nextStates[nextState] ?: -1

                    if (currentScore > existingScore) {
                        nextStates[nextState] = currentScore
                    }
                }
            }

            states = nextStates
        }

        return states
    }

    fun part1() = solve(State("AA") to 0, 30)
        .entries.maxBy { entry -> entry.value }
        .value


    fun part2(): Int {
        val states = solve(State("AA") to 0, 26)

        val bestStates = states.map { it.key.opened to it.value }
            .groupBy({ a -> a.first }, { b -> b.second })
            .mapValues { (_, scores) -> scores.max() }
        
        var maxScore = 0
        for (state1 in bestStates) {
            for (state2 in bestStates) {
                if (state1.key.intersect(state2.key).isEmpty()) {

                    val score = state1.value + state2.value
                    if (score > maxScore) {
                        maxScore = score
                    }
                }
            }
        }

        return maxScore
    }

    data class Tunnel(val name: String, val rate: Int, val leadsTo: List<String>)

    data class State(val currentName: String, val opened: Set<String> = setOf())
}