import java.io.File

val testcase = File("testcase.txt").bufferedReader().readLines()
val input = File("input.txt").bufferedReader().readLines()

val one = part1(input)
println("Part 1: $one")

val two = part2(input)
println("Part 2: $two")

fun part1( lines: List<String> ): Int {
    val columns = lines.first().indices.map { col ->
        lines.map { it[col] }
    }
    val epsilonBits = mutableListOf<Char>();
    val gammaBits = mutableListOf<Char>();
    columns.forEach { column -> 
        val groups = column.groupingBy { it }.eachCount()
        gammaBits.add(groups.maxByOrNull { it.value }!!.key)
        epsilonBits.add(groups.minByOrNull { it.value }!!.key)
    }
    val gamma = gammaBits.joinToString("").toInt(2)
    val epsilon = epsilonBits.joinToString("").toInt(2)
    return gamma * epsilon;
}

fun part2( input: List<String> ): Int {
    val O2 = getLastRemaining( input, selector('0', '1', '1') ) 
    val CO2 = getLastRemaining( input, selector('1', '0', '0') )
    return O2*CO2
}

fun selector( ifMoreZeros: Char, ifMoreOnes: Char, ifEqual: Char ): (List<Char>) -> Char {
    return { bits ->
        val counts = bits.groupingBy { it }.eachCount()
        val zeros = counts.getOrDefault('0', 0)
        val ones = counts.getOrDefault('1', 0)
        when {
            zeros > ones -> ifMoreZeros
            ones > zeros -> ifMoreOnes
            else -> ifEqual
        }            
    }
}

fun getLastRemaining( lines: List<String>, selector: (List<Char>) -> Char ): Int {
    val bits = lines.first().indices
    var remaining = lines
    for (bitIndex in bits) {
        val bit = selector( remaining.map { it[bitIndex] } )
        remaining = remaining.filter { it[bitIndex] == bit }
        if( remaining.size == 1 )
            return remaining.first().toInt(2)
    }
    throw IllegalStateException( "No single survivor" )
}