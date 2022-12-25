import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day25Test : FunSpec({

    val realInput = readLines("day25.txt")
    val exampleInput = readLines("day25.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day25(exampleInput).part1() shouldBe "2=-1=0"
        }

        test("should solve real input") {
            Day25(realInput).part1() shouldBe "2011-=2=-1020-1===-1"
        }
    }
})
