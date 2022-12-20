class Day20(input: List<String>) {

    private val inputMessage = input.mapIndexed { index, line -> MessagePart(index, line.toLong()) }

    fun part1() = decrypt()

    fun part2() = decrypt(811589153, 10)

    private fun decrypt(multiplier: Long = 1, times: Int = 1): Long {
        val originalMessage = inputMessage.map { part -> MessagePart(part.id, part.value * multiplier) }

        var message = originalMessage
        repeat(times) {
            for (part in originalMessage) {
                message = move(message, part)
            }
        }

        return extract(message, 1000) + extract(message, 2000) + extract(message, 3000)
    }

    private fun move(currentMessage: List<MessagePart>, currentPart: MessagePart): List<MessagePart> {
        val currentIndex = currentMessage.indexOf(currentPart)

        val nextMessage = currentMessage.toMutableList()
        nextMessage.removeAt(currentIndex)

        var nextIndex = (currentIndex + currentPart.value) % nextMessage.size
        nextIndex = (nextIndex + nextMessage.size) % nextMessage.size

        nextMessage.add(nextIndex.toInt(), currentPart)

        return nextMessage.toList()
    }

    private fun extract(message: List<MessagePart>, offset: Int): Long {
        val zeroIndex = message.indexOfFirst { messagePart -> messagePart.value == 0L }
        return message[(zeroIndex + offset) % message.size].value
    }

    data class MessagePart(val id: Int, val value: Long)
}