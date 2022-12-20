import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day20Test : FunSpec({

    val realInput = readLines("day20.txt")
    val exampleInput = readLines("day20.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day20(exampleInput).part1() shouldBe 3
        }

        test("should solve real input") {
            Day20(realInput).part1() shouldBe 13522
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day20(exampleInput).part2() shouldBe 1623178306
        }

        test("should solve real input") {
            Day20(realInput).part2() shouldBe 17113168880158
        }
    }
})
