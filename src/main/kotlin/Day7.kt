class Day7(input: String) {

    val commands = input.split("$").map { it.trim() }.filter { it.isNotBlank() }
    val root = Dir("/")
    
    val allDirs = mutableListOf(root)

    fun solve() {

        var current = root
        commands.forEach { command ->

            println(command)

            if (command == "cd /") {
                current = root
            } else if (command == "cd ..") {
                current = current.parent!!
            } else if (command.startsWith("cd")) {
                val (_, name) = command.split(" ")
                var nextDir = current.dirs.find { it.name == name }

                if (nextDir == null) {
                    nextDir = Dir(name, current)
                    current.dirs += nextDir
                    allDirs += nextDir
                }

                current = nextDir
            } else if(command.startsWith("ls")) {
                val list = command.split(System.lineSeparator())
                
                list.drop(1).forEach { element ->
                    if(element.startsWith("dir")) {
                        
                    } else {
                        val (size, name) = element.split(" ")
                        current.files += File(name, size.toInt())
                    }
                }
            }
        }
    }


    fun size(dir: Dir) : Int {
        return dir.files.sumOf { it.size } + dir.dirs.sumOf { d -> size(d) }
    }
    
  
    
    fun part1() = allDirs.map { dir -> size(dir) }
        .filter { it < 100000 }
        .sum()
      

    fun part2():Int{
        val rootSize = size(root)
        val unusedSpace = 70000000 - rootSize
        val needToDelete = 30000000 - unusedSpace
        
        return allDirs.map { dir -> dir.name to size(dir) }
            .filter { (name, total) -> total >= needToDelete }
            .sortedBy { (name, total) -> total }
            .first().second
        
    } 


    data class Dir(
        val name: String,
        val parent: Dir? = null,
        val dirs: MutableList<Dir> = mutableListOf(),
        val files: MutableList<File> = mutableListOf(),
    ) {
        override fun toString(): String {
            return "$name, $dirs $files"
        }
    }
    

    data class File(val name: String, val size: Int)
}

fun main() {

    val input = readText("day7.txt")
//    val input = readText("day7.txt", true)

    val day = Day7(input)
    day.solve()

    println(day.part2())

}