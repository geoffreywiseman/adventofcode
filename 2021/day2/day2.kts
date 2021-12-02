import java.io.File

fun part1( lines: Sequence<String> ): Int {
    var x = 0
    var y = 0

    lines.forEach { line ->
        val (direction, amount) = line.split(" ")
        when( direction ) {
            "forward" -> x += amount.toInt()
            "up" -> y -= amount.toInt()
            "down" -> y += amount.toInt()
        }
    }
    return x*y
}

fun part2( lines: Sequence<String> ): Int {
    var x = 0
    var y = 0
    var aim = 0

    lines.forEach { line ->
        val (direction, amount) = line.split(" ")
        when( direction ) {
            "forward" -> {
                x += amount.toInt()
                y += aim * amount.toInt()
            }
            "up" -> aim -= amount.toInt()
            "down" -> aim += amount.toInt()
        }
    }
    return x*y
}

val one = File("input.txt").useLines(block=::part1)
println( "Part 1: $one" )

val two = File("input.txt").useLines(block=::part2)
println( "Part 2: $two" )
