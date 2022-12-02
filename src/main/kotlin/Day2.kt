class Day2(input: List<String>) {

    private val games = input.map { it.split(" ").map { hand -> Hand.valueOf(hand) } }

    fun part1() = games.map { (oppHand, myHand) -> oppHand.shape to myHand.shape }
        .sumOf { (oppShape, myShape) -> myShape.play(oppShape).score + myShape.score }

    fun part2() = games.map { (oppHand, myHand) -> oppHand.shape to myHand.result!! }
        .sumOf { (oppShape, myResult) -> myResult.findShape(oppShape).score + myResult.score }

    enum class Hand(val shape: Shape, val result: Result?) {
        A(Shape.ROCK, null), B(Shape.PAPER, null), C(Shape.SCISSORS, null),
        X(Shape.ROCK, Result.LOSS), Y(Shape.PAPER, Result.DRAW), Z(Shape.SCISSORS, Result.WIN)
    }

    enum class Shape(val score: Int) {
        ROCK(1), PAPER(2), SCISSORS(3);

        fun getWinningShape() = when (this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }

        fun getLosingShape() = when (this) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }

        fun play(other: Shape) = when (other) {
            getWinningShape() -> Result.WIN
            getLosingShape() -> Result.LOSS
            else -> Result.DRAW
        }
    }

    enum class Result(val score: Int) {
        WIN(6), DRAW(3), LOSS(0);

        fun findShape(shape: Shape) = when (this) {
            WIN -> shape.getLosingShape()
            LOSS -> shape.getWinningShape()
            DRAW -> shape
        }
    }
}