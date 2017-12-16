import java.io.File

fun getStartingPositions(size: Int): List<Char> {
	return List(size) { index -> 'a' + index }
}

fun spin(positions: List<Char>, size: Int): List<Char> {
	return positions.takeLast(size) + positions.dropLast(size)
}

fun exchange(positions: List<Char>, first: Int, second: Int): List<Char> {
	return List(positions.size) { index ->
		when (index) {
			first -> positions[second]
			second -> positions[first]
			else -> positions[index]
		}
	}
}

fun partner(positions: List<Char>, first: Char, second: Char): List<Char> {
	val firstIndex = positions.indexOf(first)
	if (firstIndex < 0)
		throw IllegalArgumentException("Cannot find program with name $first")

	val secondIndex = positions.indexOf(second)
	if (secondIndex < 0)
		throw IllegalArgumentException("Cannot find program with name $second")

	return exchange(positions, firstIndex, secondIndex)
}

val dance = File("day16-input.txt")
		.readText()
		.splitToSequence(",")

var positions = getStartingPositions(16)
repeat(1_000_000) { time ->
	positions = dance.fold(positions) { positions, step ->
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
	if( time % 100 == 0 )
		println( "Finished dance $time times" )
}
println("Positions: ${positions.joinToString("")}")


