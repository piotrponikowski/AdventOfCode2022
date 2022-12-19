class Day19(input: List<String>) {

    private val pattern = Regex("""Each (\w+) robot costs (.+?)\.""")

    private val blueprints = input.map { line -> parseBlueprint(line) }

    private fun parseBlueprint(content: String) = pattern.findAll(content)
        .map { match -> match.destructured }
        .map { (type, cost) -> Recipe(parseType(type), parseCost(cost)) }
        .let { recipes -> Blueprint(recipes.toList()) }

    private fun parseType(type: String) = Resources.fromMap(mapOf(type to 1))

    private fun parseCost(costContent: String) = costContent.split(" and ")
        .associate { costPart -> costPart.split(" ").let { (amount, type) -> type to amount.toInt() } }
        .let { cost -> Resources.fromMap(cost) }

    fun part1() = blueprints
        .mapIndexed { index, blueprint -> index + 1 to solve(blueprint, 24) }
        .sumOf { (id, geodes) -> id * geodes }

    fun part2() = blueprints.take(3)
        .map { blueprint -> solve(blueprint, 32) }
        .reduce { score, geodes -> score * geodes }

    fun solve(blueprint: Blueprint, minutes: Int): Int {
        val initialState = State(Resources(1, 0, 0, 0), Resources(0, 0, 0, 0))
        var states = mutableListOf(initialState)

        val maxOreRobots = blueprint.recipes
            .filter { recipe -> recipe.robot.ore == 0 }.maxOf { recipe -> recipe.cost.ore }

        val maxClayRobots = blueprint.recipes.maxOf { recipe -> recipe.cost.clay }

        val maxObsidianRobots = blueprint.recipes.maxOf { recipe -> recipe.cost.obsidian }

        var maxGeodes = 0

        repeat(minutes) {
            val nextStates = mutableListOf<State>()

            while (states.isNotEmpty()) {
                val currentState = states.removeFirst()


                for (recipe in blueprint.recipes) {
                    if (recipe.robot.ore > 0 && currentState.robots.ore >= maxOreRobots) {
                        continue
                    }

                    if (recipe.robot.clay > 0 && currentState.robots.clay >= maxClayRobots) {
                        continue
                    }

                    if (recipe.robot.obsidian > 0 && currentState.robots.obsidian >= maxObsidianRobots) {
                        continue
                    }

                    if (currentState.canBuild(recipe)) {
                        nextStates += currentState.build(recipe)
                    }
                }
                
                nextStates += currentState.collect()
            }

            maxGeodes = nextStates.maxOf { state -> state.collected.geode }

            val filteredStates = nextStates.filter { state -> maxGeodes - state.collected.geode <= 2 }
            val sorted = filteredStates.sortedBy { state -> state.quality() }.reversed().take(20000).toMutableList()

            states = sorted
        }

        return maxGeodes
    }

    data class State(val robots: Resources, val collected: Resources) {
        fun collect() = State(robots, collected + robots)

        fun canBuild(recipe: Recipe) = (collected - recipe.cost).isSufficient()

        fun build(recipe: Recipe) = State(robots + recipe.robot, (collected - recipe.cost) + robots)

        fun quality() = robots.ore + (robots.clay * 10) + (robots.obsidian * 100) + (robots.geode * 1000)
    }

    data class Blueprint(val recipes: List<Recipe>)

    data class Recipe(val robot: Resources, val cost: Resources)

    data class Resources(val ore: Int, val clay: Int, val obsidian: Int, val geode: Int) {
        operator fun plus(other: Resources) =
            Resources(ore + other.ore, clay + other.clay, obsidian + other.obsidian, geode + other.geode)

        operator fun minus(other: Resources) =
            Resources(ore - other.ore, clay - other.clay, obsidian - other.obsidian, geode - other.geode)

        fun isSufficient() = ore >= 0 && clay >= 0 && obsidian >= 0 && geode >= 0

        companion object {
            fun fromMap(input: Map<String, Int>) =
                Resources(input["ore"] ?: 0, input["clay"] ?: 0, input["obsidian"] ?: 0, input["geode"] ?: 0)
        }
    }
}