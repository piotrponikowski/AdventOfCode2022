import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day8Test : FunSpec({

    val realInput = readLines("day8.txt")
    val exampleInput = readLines("day8.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day8(exampleInput).part1() shouldBe 21
        }
        
        test("should solve real input") {
            Day8(realInput).part1() shouldBe 1843
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day8(exampleInput).part2() shouldBe 8
        }
        
        test("should solve real input") {
            Day8(realInput).part2() shouldBe 180000
        }
    }
})
