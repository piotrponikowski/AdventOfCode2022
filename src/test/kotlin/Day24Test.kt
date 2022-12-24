import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day24Test : FunSpec({

    val realInput = readLines("day24.txt")
    val exampleInput = readLines("day24.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day24(exampleInput).part1() shouldBe 18
        }

        test("should solve real input") {
            Day24(realInput).part1() shouldBe 311
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day24(exampleInput).part2() shouldBe 54
        }

        test("should solve real input") {
            Day24(realInput).part2() shouldBe 869
        }
    }
})
