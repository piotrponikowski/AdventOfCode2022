import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day12Test : FunSpec({

    val realInput = readLines("day12.txt")
    val exampleInput = readLines("day12.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day12(exampleInput).part1() shouldBe 31
        }

        test("should solve real input") {
            Day12(realInput).part1() shouldBe 420
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day12(exampleInput).part2() shouldBe 29
        }

        test("should solve real input") {
            Day12(realInput).part2() shouldBe 414
        }
    }
})
