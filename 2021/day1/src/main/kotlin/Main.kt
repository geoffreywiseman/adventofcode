fun main(args: Array<String>) {
    val one = part1(getResourceLines("input.txt"))
    println("Part 1: depth increased ${one} times")

    val two = part2(getResourceLines("input.txt"))
    println("Part 2: moving sum depth increased ${two} times")
}

fun getResourceLines(name: String): Sequence<String> {
    return Thread.currentThread()
        .contextClassLoader
        .getResourceAsStream(name)!!
        .bufferedReader()
        .lineSequence()
}

fun part1(lines: Sequence<String>): Int {
    return lines.map(String::toInt)
        .countIncreases()
}

fun part2(lines: Sequence<String>): Int {
    return lines.map(String::toInt)
        .windowed(3)
        .map { it.sum() }
        .countIncreases()
}

fun Sequence<Int>.countIncreases(): Int = this.zipWithNext().count() { it.second > it.first }
