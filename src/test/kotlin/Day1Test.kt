import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day1Test : FunSpec({

    val realInput = readText("day1.txt")
    val exampleInput = readText("day1.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day1(exampleInput).part1() shouldBe 24000
        }
        
        test("should solve real input") {
            Day1(realInput).part1() shouldBe 71934
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day1(exampleInput).part2() shouldBe 45000
        }
        
        test("should solve real input") {
            Day1(realInput).part2() shouldBe 211447
        }
    }
})
