import java.io.File

fun getStartingPositions(size: Int): String {
	return List(size) { index -> 'a' + index }.joinToString("")
}

fun spin(positions: String, size: Int): String {
	return positions.takeLast(size) + positions.dropLast(size)
}

fun exchange(positions: String, first: Int, second: Int): String {
	return buildString {
		this.append(positions)
		val temp = this[first]
		this[first] = this[second]
		this[second] = temp
	}
}

fun partner(positions: String, first: Char, second: Char): String {
	val firstIndex = positions.indexOf(first)
	if (firstIndex < 0)
		throw IllegalArgumentException("Cannot find program with name $first")

	val secondIndex = positions.indexOf(second)
	if (secondIndex < 0)
		throw IllegalArgumentException("Cannot find program with name $second")

	return exchange(positions, firstIndex, secondIndex)
}

fun dance(startingPositions: String, instructions: Sequence<String>): String {
	return instructions.fold(startingPositions) { positions, step ->
		when {
			step[0] == 's' -> spin(positions, step.drop(1).toInt())
			step[0] == 'x' -> {
				val (first, second) = step.drop(1)
						.split('/')
						.map { it.toInt() }
				exchange(positions, first, second)
			}
			step[0] == 'p' -> {
				val (first, second) = step.drop(1)
						.split('/')
						.map { it.first() }
				partner(positions, first, second)
			}
			else -> throw IllegalArgumentException("Unexpected step: ${step}")
		}
	}
}

val instructions = File("day16-input.txt")
		.readText()
		.splitToSequence(",")

// part 1
println(dance(getStartingPositions(16), instructions))

// part 2
fun getEndPosition(rounds: Int, instructions: Sequence<String>): String {
	var currentPosition = getStartingPositions(16)
	val history = mutableListOf<String>(currentPosition)

	while (true) {
		val nextPosition = dance(currentPosition, instructions)
		val cycleStart = history.indexOf(nextPosition)
		if (cycleStart != -1) {
			val remainingRounds = rounds - (history.size - 1)
			val cycleSize = history.size - cycleStart
			val equivalentIndex = (remainingRounds-1) % cycleSize + cycleStart
			return history[equivalentIndex]
		} else if (history.size == rounds) {
			return nextPosition
		}
		currentPosition = nextPosition
		history.add(nextPosition)
	}
}
println(getEndPosition(1_000_000, instructions))
