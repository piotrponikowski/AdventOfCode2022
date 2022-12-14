import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day21Test : FunSpec({

    val realInput = readLines("day21.txt")
    val exampleInput = readLines("day21.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day21(exampleInput).part1() shouldBe 152
        }

        test("should solve real input") {
            Day21(realInput).part1() shouldBe 256997859093114
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day21(exampleInput).part2() shouldBe 301
        }

        test("should solve real input") {
            Day21(realInput).part2() shouldBe 3952288690726
        }
    }
})
