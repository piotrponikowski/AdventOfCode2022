import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day11Test : FunSpec({

    val realInput = readText("day11.txt")
    val exampleInput = readText("day11.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day11(exampleInput).part1() shouldBe 10605L
        }

        test("should solve real input") {
            Day11(realInput).part1() shouldBe 54253L
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day11(exampleInput).part2() shouldBe 2713310158L
        }

        test("should solve real input") {
            Day11(realInput).part2() shouldBe 13119526120L
        }
    }
})
