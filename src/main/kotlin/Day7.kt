class Day7(input: String) {

    private val commands = input.split("$")
        .map { content -> content.trim() }
        .filter { content -> content.isNotBlank() }
        .map { content -> content.split(System.lineSeparator()) }
        .map { content -> content.first() to content.drop(1) }

    fun part1() = findAllSubDirectories(root)
        .map { directory -> directory.size() }
        .filter { size -> size < 100000 }
        .sum()

    fun part2() = findAllSubDirectories(root)
        .map { directory -> directory.size() }
        .filter { size -> size >= 30000000 - (70000000 - root.size()) }
        .min()

    private val root = Directory("/", null, mutableListOf())

    init {
        commands.fold(root) { current, (commandLine, resultLines) ->
            when {
                commandLine == "cd /" -> root
                commandLine == "cd .." -> current.parent!!
                commandLine.startsWith("cd") -> findChild(current, commandLine.split(" ").last())!!
                else -> current.apply { children += parseChildren(current, resultLines) }
            }
        }
    }

    private fun findChild(directory: Directory, name: String) = directory.children
        .filterIsInstance<Directory>()
        .find { it.name == name }

    private fun parseChildren(parent: Directory, resultLines: List<String>) = resultLines
        .map { line -> line.split(" ") }
        .map { (type, name) ->
            when (type == "dir") {
                true -> Directory(name, parent, mutableListOf())
                false -> File(name, type.toInt())
            }
        }

    private fun findAllSubDirectories(directory: Directory): List<Directory> = directory.children
        .filterIsInstance<Directory>()
        .let { subDirectories ->
            subDirectories + subDirectories.flatMap { subDirectory ->
                findAllSubDirectories(
                    subDirectory
                )
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
    }
}