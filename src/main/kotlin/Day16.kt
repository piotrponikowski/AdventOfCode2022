class Day16(input: List<String>) {

    private val pattern = Regex("""Valve (.+) has flow rate=(\d+); tunnels? leads? to valves? (.+)""")

    val tunnels = input
        .map { line -> pattern.matchEntire(line)!!.destructured }
        .map { (name, rate, leadsTo) -> Tunnel(name, rate.toInt(), leadsTo.split(", ")) }
        .associateBy { tunnel -> tunnel.name }

    fun solve() {

        var states = mapOf(State("AA") to 0)

        repeat(30) { minute ->
            val timeLeft = (30 - minute) -1
            println("Minute ${minute}, ${states.size}")

            val nextStates = mutableMapOf<State , Int>()

            for ((currentState, currentScore) in states) {
                val currenTunnel = tunnels[currentState.currentName]!!
                val isOpen = currentState.currentName in currentState.opened
                
                if(!isOpen && currenTunnel.rate > 0) {
                    val nextState = State(currentState.currentName, currentState.opened + currentState.currentName)
                    val nextScore = currentScore + (currenTunnel.rate * timeLeft)
                    val existingScore = nextStates[nextState] ?: -1
                    
                    if(nextScore > existingScore) {
                        nextStates[nextState] = nextScore
                    }
                }
                
                for(nextName in currenTunnel.leadsTo) {
                    val nextState = State(nextName, currentState.opened)
                    val nextScore = currentScore
                    val existingScore = nextStates[nextState] ?: -1
                    
                    if(nextScore > existingScore) {
                        nextStates[nextState] = currentScore
                    }
                }
            }
            
            states = nextStates
        }
        
        val score = states.values.sorted().reversed().take(1)
        println(score)
    }

    fun part1() {
        solve()
    }

    fun part2() = 2

    data class Tunnel(val name: String, val rate: Int, val leadsTo: List<String>)

    data class State(val currentName: String, val opened: List<String> = listOf())
}

fun main() {

    val input = readLines("day16.txt")
//    val input = readLines("day16.txt", true)

    val result = Day16(input).part1()
    println(result)

}