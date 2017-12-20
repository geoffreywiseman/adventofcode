// Part 1
fun spin(insertions: Int, steps: Int): List<Int> {
	val buffer = mutableListOf(0)
	var position = 0

	(1..insertions).forEach { insertion ->
		val newPosition = (position + steps) % buffer.size + 1
		buffer.add(newPosition, insertion)
		position = newPosition
	}
	return buffer
}

val bufferOne = spin(2017, 349)
val position = bufferOne.indexOf(2017)
val nextValue = bufferOne[(position + 1) % bufferOne.size]
println("Part 1:\nThe next value is $nextValue")

// Part 2
fun afterZero(insertions:Int, steps:Int): Int? {
	var afterZero: Int? = null
	var size = 1
	var position = 0
	(1..insertions).forEach { insertion ->
		val newPosition = (position + steps) % size + 1
		if( newPosition == 1 )
			afterZero = insertion
		size += 1
		position = newPosition
	}
	return afterZero
}

val valueAfterZero = afterZero(50_000_000, 349)
println("\nPart 2:\nThe value after 0 is $valueAfterZero")
