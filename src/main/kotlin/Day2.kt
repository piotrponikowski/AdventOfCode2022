class Day2(input: List<String>) {

    val pairs = input.map { it.split(" ") }

    fun score(opp: String, me: String): Int {
        if (opp == "C" && me == "X" || opp == "A" && me == "Y" || opp == "B" && me == "Z") {
            return 6
        } else if (opp == "A" && me == "X" || opp == "B" && me == "Y" || opp == "C" && me == "Z") {
            return 3
        } else {
            return 0
        }
    }

    fun score2(me: String): Int {
        if (me == "X") {
            return 0
        } else if (me == "Y") {
            return 3
        } else {
            return 6
        }
    }
    
    fun type1(me: String): Int {
        if(me == "X") {
            return 1
        } else if(me == "Y") {
            return 2
        } else {
            return 3;
        }
    }

    fun type2(me: String): Int {
        if(me == "A") {
            return 1
        } else if(me == "B") {
            return 2
        } else {
            return 3;
        }
    }
    
    fun winningType(opp: String, me:String): String {
        if(opp == "A" && me == "Z") {
            return "B"
        } else if(opp == "B" && me == "Z") {
            return "C"
        } else if(opp == "C" && me == "Z") {
            return "A"
        }

        if(opp == "A" && me == "Y") {
            return "A"
        } else if(opp == "B" && me == "Y") {
            return "B"
        } else if(opp == "C" && me == "Y") {
            return "C"
        }

        if(opp == "A" && me == "X") {
            return "C"
        } else if(opp == "B" && me == "X") {
            return "A"
        } else if(opp == "C" && me == "X") {
            return "B"
        }
        
        throw RuntimeException("Unknown $opp $me")
    }

    fun part1() = pairs.map { (opp, me) -> score(opp, me) + type1(me) }.sum()

    fun part2() = pairs.map { (opp, me) ->score2(me) + type2(winningType(opp, me)) }.sum()
}


fun main() {

    val input = readLines("day2.txt")
//    val input = readLines("day2.txt", true)


    val result = Day2(input).part2()
    println(result)
}