class Day7(input: String) {

    private val commands = input.split("$")
        .map { content -> content.trim() }
        .filter { content -> content.isNotBlank() }
        .map { content -> content.split(System.lineSeparator()) }
        .map { content -> content.first() to content.drop(1) }

    private val root = solveRoot()
    private val totalSize = root.size()

    fun part1() = root.findAllSubDirs()
        .map { directory -> directory.size() }
        .filter { size -> size < 100000 }
        .sum()

    fun part2() = root.findAllSubDirs()
        .map { directory -> directory.size() }
        .filter { size -> size >= 30000000 - (70000000 - totalSize) }
        .min()

    private fun solveRoot(): Directory = Directory("/", null, mutableListOf()).also { root ->
        commands.fold(root) { current, (commandLine, resultLines) ->
            when {
                commandLine == "cd /" -> root
                commandLine == "cd .." -> current.parent!!
                commandLine.startsWith("cd") -> current.findDir(commandLine.split(" ").last())!!
                else -> current.apply { children += parseChildren(current, resultLines) }
            }
        }
    }

    private fun parseChildren(parent: Directory, resultLines: List<String>) = resultLines
        .map { line -> line.split(" ") }
        .map { (type, name) ->
            when (type == "dir") {
                true -> Directory(name, parent, mutableListOf())
                false -> File(name, type.toInt())
            }
        }

    interface Element {
        fun size(): Int
        fun name(): String
    }

    data class File(val name: String, val size: Int) : Element {
        override fun name() = name
        override fun size() = size
    }

    data class Directory(val name: String, val parent: Directory?, val children: MutableList<Element>) : Element {
        override fun name() = name
        override fun size() = children.sumOf { child -> child.size() }

        fun findAllSubDirs(current: Directory = this): List<Directory> = current.findAllDirs()
            .let { dirs -> dirs + dirs.flatMap { dir -> findAllSubDirs(dir) } }

        fun findDir(name: String) = findAllDirs().find { it.name == name }

        private fun findAllDirs() = children.filterIsInstance<Directory>()
    }
}