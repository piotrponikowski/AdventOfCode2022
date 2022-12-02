import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2Test : FunSpec({

    val realInput = readLines("day2.txt")
    val exampleInput = readLines("day2.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day2(exampleInput).part1() shouldBe 15
        }
        
        test("should solve real input") {
            Day2(realInput).part1() shouldBe 13526
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day2(exampleInput).part2() shouldBe 12
        }
        
        test("should solve real input") {
            Day2(realInput).part2() shouldBe 14204
        }
    }
})
