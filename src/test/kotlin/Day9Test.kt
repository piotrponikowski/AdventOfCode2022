import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day9Test : FunSpec({

    val realInput = readLines("day9.txt")
    val exampleInput1 = readLines("day9-1.txt", true)
    val exampleInput2 = readLines("day9-2.txt", true)
    
    context("Part 1") {
        test("should solve example") {
            Day9(exampleInput1).part1() shouldBe 13
        }
        
        test("should solve real input") {
            Day9(realInput).part1() shouldBe 6337
        }
    }

    context("Part 2") {
        test("should solve 1st example") {
            Day9(exampleInput1).part2() shouldBe 1
        }

        test("should solve 2nd example") {
            Day9(exampleInput2).part2() shouldBe 36
        }
        
        test("should solve real input") {
            Day9(realInput).part2() shouldBe 2455
        }
    }
})
