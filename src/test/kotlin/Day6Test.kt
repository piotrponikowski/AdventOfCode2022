import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day6Test : FunSpec({

    val realInput = readText("day6.txt")
    val exampleInput = readText("day6.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day6(exampleInput).part1() shouldBe 7
        }
        
        test("should solve real input") {
            Day6(realInput).part1() shouldBe 1625
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day6(exampleInput).part2() shouldBe 19
        }
        
        test("should solve real input") {
            Day6(realInput).part2() shouldBe 2250
        }
    }
})
