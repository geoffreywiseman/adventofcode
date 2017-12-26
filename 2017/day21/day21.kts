import java.io.File
import kotlin.math.sqrt

fun parse(filename: String): Map<String, String> {
	val expansions = mutableMapOf<String, String>()
	val transformations = arrayOf<Array<Int>>(
			arrayOf(0, 1, 2, 3),
			arrayOf(1, 3, 0, 2),
			arrayOf(3, 2, 1, 0),
			arrayOf(2, 0, 3, 1),
			arrayOf(1, 0, 3, 2),
			arrayOf(2, 3, 0, 1),
			arrayOf(3, 1, 2, 0),
			arrayOf(0, 2, 1, 3),
			arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8),
			arrayOf(2, 5, 8, 1, 4, 7, 0, 3, 6),
			arrayOf(8, 7, 6, 5, 4, 3, 2, 1, 0),
			arrayOf(6, 3, 0, 7, 4, 1, 8, 5, 2),
			arrayOf(2, 1, 0, 5, 4, 3, 8, 7, 6),
			arrayOf(6, 7, 8, 3, 4, 5, 0, 1, 2),
			arrayOf(8, 5, 2, 7, 4, 1, 6, 3, 0),
			arrayOf(0, 3, 6, 1, 4, 7, 2, 5, 8)
	)

	fun getInputPatterns(inputWithSeparators: String): Set<String> {
		val input = inputWithSeparators.split("/").joinToString("")
		return transformations.filter { array -> input.length == array.size }
				.map { xform ->
					xform.map { charIndex -> input[charIndex] }
							.joinToString("")
				}
				//.apply { println("Input $input gives patterns: ${this.toSet()}") }
				.toSet()
	}

	File(filename).readLines().forEach { line ->
		val trimmed = line.trim()
		val index = trimmed.indexOf(" => ")
		val output = trimmed.substring(index + 4)
		val filteredOutput = output.filter { it != '/' }
		val input = trimmed.substring(0, index)
		println("Input: $input, Output: $output")
		getInputPatterns(input).forEach { pattern -> expansions[pattern] = filteredOutput }
	}
	return expansions.toMap()
}

fun splitGrid(grid: String, expansionSize: Int, patternSize: Int): List<String> {
	val splits = mutableListOf<String>()
	for (index in 0 until grid.length step expansionSize) {
		if ((index / patternSize) % expansionSize == 0) {
			splits.add(
					buildString {
						for (row in 0 until expansionSize) {
							this.append(grid.substring(index + patternSize * row, index + expansionSize + patternSize * row))
						}
					}
			)
		}
	}
	return splits;
}

fun joinGrid(splits: List<String>, expansionSize: Int, patternSize: Int): String {
	val splitLength = splits.first().length
	val expandedSize = sqrt(splitLength.toDouble()).toInt()
	var splitsRemaining = splits
	return buildString {
		val splitsPerRow = patternSize / expansionSize;
		while (splitsRemaining.isNotEmpty()) {
			val rowSplits = splitsRemaining.take(splitsPerRow)
			splitsRemaining = splitsRemaining.drop(splitsPerRow)
			for (row in 0 until expandedSize) {
				val start = 0 + expandedSize * row
				val end = expandedSize * (row + 1)
				rowSplits
						.map { it.substring(start, end) }
						.forEach { segment -> append(segment) }
			}
		}
	}
}

fun expand(grid: String, expansions: Map<String, String>): String {
	println("Expanding: $grid")
	val inputLength = grid.length
	val patternSize = sqrt(inputLength.toDouble()).toInt()
	if (patternSize * patternSize != inputLength)
		throw IllegalStateException("Cannot make an even side length from input length $inputLength")
	val expansionSize = when {
		patternSize % 2 == 0 -> 2
		patternSize % 3 == 0 -> 3
		else -> throw IllegalStateException("Cannot find expansion for pattern size $patternSize")
	}

	if (grid.length == expansionSize * expansionSize) {
		val result = expansions[grid]
		if (result == null)
			throw IllegalStateException("Cannot find expansion for: $grid")
		else
			return result
	} else {
		val expandedSplits = splitGrid(grid, expansionSize, patternSize)
				.map { expansions[it]!! }
		return joinGrid(expandedSplits, expansionSize, patternSize)
	}
}

fun expand(startGrid: String, expansions: Map<String, String>, iterations: Int): String {
	var grid = startGrid
	repeat(iterations) {
		grid = expand(grid, expansions)
	}
	return grid
}

val expansions = parse("input.txt")
val startGrid = ".#...####"

val partOne = expand(startGrid, expansions, 5)
val partOneLit = partOne.count { it == '#' }
println("Part 1: $partOneLit lit pixels.")

val partTwo = expand(startGrid, expansions, 5)
val partTwoLit = partOne.count { it == '#' }
println("Part 2: $partOneLit lit pixels.")
