import java.io.File

val pattern = """(\d+)-(\d+) ([a-z]): ([a-z]+)""".toRegex()
File("input.txt").useLines() { seq ->
    val items = seq.map { parse(it) }.toList()

    // Part 1
    val part1 = items.count { (rule, password) -> 
        password.count { it == rule.char } in rule.range
    }
    println("Part 1: There are $part1 valid passwords")

    // Part 2
    val part2 = items.count { (rule, password) -> 
        val first = password[rule.range.start - 1]
        val second = password[rule.range.endInclusive - 1]
        ( first == rule.char ).xor( second == rule.char )
    }
    println("Part 2: There are $part2 valid passwords")
}

fun parse(line: String): Pair<Rule,String> {
    val mr = pattern.matchEntire(line) ?: throw IllegalArgumentException("$line doesn't match pattern")
    return Pair(
        Rule( 
            mr.groupValues[1].toInt() .. mr.groupValues[2].toInt(),
            mr.groupValues[3][0],
        ),
        mr.groupValues[4]
    )
}

data class Rule(val range: IntRange, val char: Char)
