import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day1Test : FunSpec({

    val realInput = readLines("day1.txt")
    val exampleInput = readLines("day1.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day1(exampleInput).part1() shouldBe 1
        }
        
        test("should solve real input") {
            Day1(realInput).part1() shouldBe 1
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day1(exampleInput).part2() shouldBe 2
        }
        
        test("should solve real input") {
            Day1(realInput).part2() shouldBe 2
        }
    }
})
