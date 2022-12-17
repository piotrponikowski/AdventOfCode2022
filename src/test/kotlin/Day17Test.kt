import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day17Test : FunSpec({

    val realInput = readText("day17.txt")
    val exampleInput = readText("day17.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day17(exampleInput).part1() shouldBe 3068L
        }

        test("should solve real input") {
            Day17(realInput).part1() shouldBe 3114L
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day17(exampleInput).part2() shouldBe 1514285714288L
        }

        test("should solve real input") {
            Day17(realInput).part2() shouldBe 1540804597682L
        }
    }
})
