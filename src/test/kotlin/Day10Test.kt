import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day10Test : FunSpec({

    val realInput = readLines("day10.txt")
    val exampleInput = readLines("day10.txt", true)

    context("Part 1") {
        test("should solve example") {
            Day10(exampleInput).part1() shouldBe 13140
        }

        test("should solve real input") {
            Day10(realInput).part1() shouldBe 12840
        }
    }

    context("Part 2") {
        test("should solve example") {
            val result = """
                |##..##..##..##..##..##..##..##..##..##..
                |###...###...###...###...###...###...###.
                |####....####....####....####....####....
                |#####.....#####.....#####.....#####.....
                |######......######......######......####
                |#######.......#######.......#######....."""
                .trimMargin()
                .replace(Regex("""\s"""), "")
            
            Day10(exampleInput).part2().replace(Regex("""\s"""), "") shouldBe result
        }

        test("should solve real input") {
            val result = """
                |####.#..#...##.####.###....##.####.####.
                |...#.#.#.....#.#....#..#....#.#.......#.
                |..#..##......#.###..###.....#.###....#..
                |.#...#.#.....#.#....#..#....#.#.....#...
                |#....#.#..#..#.#....#..#.#..#.#....#....
                |####.#..#..##..#....###...##..#....####."""
                .trimMargin()
                .replace(Regex("""\s"""), "")
            
            Day10(realInput).part2().replace(Regex("""\s"""), "") shouldBe result
        }
    }
})
