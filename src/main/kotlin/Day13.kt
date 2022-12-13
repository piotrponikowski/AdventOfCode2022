class Day13(input: List<String>) {

    private val packets = input
        .filter { line -> line.isNotBlank() }
        .map { line -> parse(line) }

    private val dividers = listOf(parse("[[2]]", true), parse("[[6]]", true))

    fun part1() = packets.asSequence()
        .chunked(2)
        .mapIndexed { index, (element1, element2) -> index to compare(element1.toList(), element2.toList()) }
        .filter { (_, result) -> result == -1 }
        .map { (index, _) -> index + 1 }
        .reduce { score, index -> score + index }

    fun part2() = (packets + dividers).asSequence()
        .sortedWith { a, b -> compare(a.toList(), b.toList()) }
        .mapIndexed { index, packet -> index to packet }
        .filter { (_, packet) -> packet.isDivider() }
        .map { (index, _) -> index + 1 }
        .reduce { score, index -> score * index }

    private fun compare(list1: ListElement, list2: ListElement): Int {
        val commonSizeList = list1.elements.zip(list2.elements)

        commonSizeList.forEach { (element1, element2) ->
            if (element1 is IntElement && element2 is IntElement) {
                if (element1.value < element2.value) {
                    return -1
                } else if (element1.value > element2.value) {
                    return 1
                }
            } else {
                val result = compare(element1.toList(), element2.toList())
                if (result != 0) {
                    return result
                }
            }
        }

        if (list1.elements.size < list2.elements.size) {
            return -1
        } else if (list1.elements.size > list2.elements.size) {
            return 1
        }

        return 0
    }

    companion object {

        fun parse(line: String, divisor: Boolean = false): Element {
            val digits = line.toIntOrNull()

            return if (digits != null) {
                IntElement(digits, divisor)
            } else {
                val trimmedLine = line.drop(1).dropLast(1)
                val elements = splitByComma(trimmedLine).map { subLine -> parse(subLine) }
                ListElement(elements, divisor)
            }
        }

        fun splitByComma(line: String) = splitByComma(line, 0, 0, 0, listOf())

        private fun splitByComma(line: String, level: Int, last: Int, index: Int, parts: List<String>): List<String> {
            if (line.isEmpty()) {
                return listOf()
            }

            val symbol = line[index]
            val nextIndex = index + 1

            return when {
                index == line.length - 1 -> {
                    val newPart = line.substring(last)
                    parts + newPart
                }
                symbol == ',' && level == 0 -> {
                    val newPart = line.substring(last, index)
                    splitByComma(line, level, nextIndex, nextIndex, parts + newPart)
                }
                symbol == '[' -> splitByComma(line, level + 1, last, nextIndex, parts)
                symbol == ']' -> splitByComma(line, level - 1, last, nextIndex, parts)
                else -> splitByComma(line, level, last, nextIndex, parts)
            }
        }
    }

    sealed class Element {
        fun toList() = when (this) {
            is IntElement -> ListElement(listOf(this))
            is ListElement -> this
        }

        abstract fun isDivider(): Boolean
    }

    data class IntElement(val value: Int, val divider: Boolean = false) : Element() {
        override fun isDivider() = divider
    }

    data class ListElement(val elements: List<Element>, val divider: Boolean = false) : Element() {
        override fun isDivider() = divider
    }
}