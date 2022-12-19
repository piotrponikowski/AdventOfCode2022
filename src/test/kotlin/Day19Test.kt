import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day19Test : FunSpec({

    val realInput = readLines("day19.txt")
    val exampleInput = readLines("day19.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day19(exampleInput).part1() shouldBe 33
        }

        test("should solve real input") {
            Day19(realInput).part1() shouldBe 1550
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day19(exampleInput).part2() shouldBe 3472
        }

        test("should solve real input") {
            Day19(realInput).part2() shouldBe 18630
        }
    }
})
