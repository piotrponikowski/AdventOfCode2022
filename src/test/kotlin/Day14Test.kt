import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day14Test : FunSpec({

    val realInput = readLines("day14.txt")
    val exampleInput = readLines("day14.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day14(exampleInput).part1() shouldBe 24
        }

        test("should solve real input") {
            Day14(realInput).part1() shouldBe 625
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day14(exampleInput).part2() shouldBe 93
        }

        test("should solve real input") {
            Day14(realInput).part2() shouldBe 25193
        }
    }
})
