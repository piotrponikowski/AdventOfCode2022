import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day16Test : FunSpec({

    val realInput = readLines("day16.txt")
    val exampleInput = readLines("day16.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day16(exampleInput).part1() shouldBe 1651
        }

        test("should solve real input") {
            Day16(realInput).part1() shouldBe 2087
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day16(exampleInput).part2() shouldBe 1707
        }

        test("should solve real input") {
            Day16(realInput).part2() shouldBe 2591
        }
    }
})
