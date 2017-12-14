import java.io.File
import java.util.*

val graph = mutableMapOf<Int, MutableList<Int>>()
val linePattern = Regex("(\\d+) <-> ([\\d,\\s]+)")

fun parseLine(line: String) {
	val result = linePattern.matchEntire(line)
	if (result == null)
		throw IllegalArgumentException("Cannot parse input line: $line")
	else {
		val (source, targets) = result.destructured
		val sourceInt = source.toInt()
		val targetInts = targets.splitToSequence(",").map { it.trim() }.map { it.toInt() }
		val keyTargets = graph[sourceInt] ?: mutableListOf<Int>()
		keyTargets.addAll(targetInts)
		graph[sourceInt] = keyTargets
	}
}

fun getTransitiveConnections(start: Int): Set<Int> {
	val remaining = ArrayDeque<Int>()
	remaining.push(start)
	val transitiveConnections = mutableSetOf<Int>()

	while (!remaining.isEmpty()) {
		val current = remaining.pop()
		graph[current]!!.forEach { if (transitiveConnections.add(it)) remaining.push(it) }
	}
	return transitiveConnections.toSet()
}

fun countGroups(): Int {
	val programs = graph.keys.toMutableSet()
	var groups = 0

	while (!programs.isEmpty()) {
		groups += 1
		val start = programs.first()
		programs.removeAll(getTransitiveConnections(start))
	}

	return groups
}

File("day12-input.txt").readLines().forEach(this::parseLine)
print("Graph $graph\n")
val zeroConnect = getTransitiveConnections(0)
print("There are ${zeroConnect.size} connections from 0: ${zeroConnect}\n")
print("There are ${countGroups()} groups\n")