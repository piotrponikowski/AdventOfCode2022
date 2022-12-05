import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day5Test : FunSpec({

    val realInput = readText("day5.txt")
    val exampleInput = readText("day5.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day5(exampleInput).part1() shouldBe "CMZ"
        }
        
        test("should solve real input") {
            Day5(realInput).part1() shouldBe "ZRLJGSCTR"
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day5(exampleInput).part2() shouldBe "MCD"
        }
        
        test("should solve real input") {
            Day5(realInput).part2() shouldBe "PRTTGRFPB"
        }
    }
})
