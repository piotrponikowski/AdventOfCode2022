import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import Day13.IntElement
import Day13.ListElement


class Day13Test : FunSpec({

    val realInput = readLines("day13.txt")
    val exampleInput = readLines("day13.txt", true)

    context("Part 1") {

        context("should split by comma") {
            table(
                headers("input", "result"),
                row("1,1,3,1,1", listOf("1", "1", "3", "1", "1")),
                row("[1],[2,3,4]", listOf("[1]", "[2,3,4]")),
                row("[8,7,6]", listOf("[8,7,6]")),
                row("1,[2,[3,[4,[5,6,7]]]],8,9", listOf("1", "[2,[3,[4,[5,6,7]]]]", "8", "9")),
            ).forAll { input, result ->
                test(input) {
                    Day13.splitByComma(input) shouldBe result
                }
            }
        }

        context("should parse") {
            table(
                headers("input", "result"),
                row("1", IntElement(1)),
                row("[]", ListElement(listOf())),
                row("[2]", ListElement(listOf(IntElement(2)))),
                row("[1,1,3]", ListElement(listOf(IntElement(1), IntElement(1), IntElement(3)))),
                row("[[1],4]", ListElement(listOf(ListElement(listOf(IntElement(1))), IntElement(4)))),
            ).forAll { input, result ->
                test(input) {
                    Day13.parse(input) shouldBe result
                }
            }
        }

        test("should solve example") {
            Day13(exampleInput).part1() shouldBe 13
        }

        test("should solve real input") {
            Day13(realInput).part1() shouldBe 5808
        }
    }

    context("Part 2") {
        test("should solve example") {
            Day13(exampleInput).part2() shouldBe 140
        }

        test("should solve real input") {
            Day13(realInput).part2() shouldBe 22713
        }
    }
})
