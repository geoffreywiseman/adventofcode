import java.io.File

data class Position(val row: Int, val column: Int) {
	operator fun plus(other: Direction): Position {
		return Position(row + other.rowDelta, column + other.columnDelta)
	}

	fun inRanges(rowRange: IntRange, columnRange: IntRange): Boolean {
		return row in rowRange && column in columnRange
	}
}

data class Path(val letters: String, val steps: Int)

data class Direction(val name: String, val rowDelta: Int, val columnDelta: Int) {
	fun canTurn(direction: Direction): Boolean = (this.rowDelta and direction.rowDelta) == 0 && (this.columnDelta and direction.columnDelta) == 0
}

fun isValidTurnDestination(symbol: Char): Boolean {
	return when (symbol) {
		'+' -> true
		'|' -> true
		'-' -> true
		in 'A'..'Z' -> true
		else -> false
	}
}

fun walkMap(map: List<String>, startingPosition: Position): Path {
	val path = StringBuilder()
	var steps = 1
	var position = startingPosition
	var direction = directions.first()

	while (true) {
		println("At position $position, walking ${direction.name}, having taken ${steps} steps")
		val currentChar = map[position.row][position.column]
		when {
			currentChar.isLetter() -> {
				println("Passed through $currentChar")
				path.append(currentChar)
				position = position + direction
				steps += 1
			}
			currentChar == '+' -> {
				try {
					direction = directions.filter { direction.canTurn(it) }
							.first { option ->
								val turnPosition = position + option
								turnPosition.inRanges(rowRange, columnRange) && isValidTurnDestination(map[turnPosition.row][turnPosition.column])
							}
					position = position + direction
					println("Turned ${direction.name}")
					steps += 1
				} catch (exception: NoSuchElementException) {
					throw IllegalStateException("At intersection $position, but cannot find a place to turn.")
				}
			}
			currentChar == '|' || currentChar == '-' -> {
				steps += 1
				position = position + direction
			}
			currentChar == ' ' -> {
				println("Finished at $position")
				return Path(path.toString(), steps-1)
			}
			else -> throw IllegalStateException("Unexpected character: $currentChar")
		}

	}
}

// setup
val directions = listOf(
		Direction("south", 1, 0),
		Direction("west", 0, -1),
		Direction("north", -1, 0),
		Direction("east", 0, 1)
)
var map = File("day19-sample.txt").readLines()
val rowRange = 0 until map.size
val columnRange = 0 until map.map { it.length }.max()!!

val startingPosition = with(map.first()) { Position(0, this.indices.first { this[it] == '|' }) }
println("Starting position: $startingPosition")

val path = walkMap(map.map { it.padEnd(columnRange.endInclusive + 1) }, startingPosition)
println("Walked path '${path.letters}' in ${path.steps} steps")
