val input = "hwlqcszp"

class Cord() {
	val marks = Array(256) { index -> index }
	var skip = 0
	var position = 0

	fun switch(left: Int, right: Int) {
		val leftIndex = left % marks.size
		val rightIndex = right % marks.size
		val holding = marks[leftIndex]
		marks[leftIndex] = marks[rightIndex]
		marks[rightIndex] = holding
	}

	fun reverse(start: Int, end: Int) {
		var left = start
		var right = end
		while (left < right) {
			switch(left, right)
			left += 1
			right -= 1
		}
	}

	fun twist(length: Int) {
		if (length > 0) {
			reverse(position, position + length - 1)
		}
		position += length + skip
		skip += 1
	}

}

class KnotHash(key: String) {
	val sparseHash: Array<Int> = run {
		val cord = Cord()
		repeat(64) {
			key.map { it.toInt() }
					.plus(arrayOf(17, 31, 73, 47, 23))
					.forEach(cord::twist)
		}
		cord.marks
	}

	val denseHash = run {
		val hash = Array(16) { 0 }
		for (denseIndex in 0..15) {
			for (sparseIndex in (denseIndex * 16)..((denseIndex + 1) * 16 - 1)) {
				hash[denseIndex] = hash[denseIndex] xor sparseHash[sparseIndex]
			}
		}
		hash
	}

	fun toHexString(): String {
		return denseHash.map { it.toString(16) }
				.map { it.padStart(2, '0') }
				.joinToString("")
	}

	fun toBinaryString(): String {
		return denseHash.map { it.toString(2) }
				.map { it.padStart(8, '0') }
				.joinToString("")
	}
}

val dimensions = (0..127)

 PART 1
val usedBlocks = dimensions.map { "$input-$it" }
		.map { KnotHash(it) }
		.map { it.toBinaryString() }
		.map { it.count { it == '1' } }
		.sum()
println("Used blocks: $usedBlocks")

// PART 2
val directions = listOf( Pair(0,1), Pair(0,-1), Pair(1,0), Pair(-1,0) )
val grid = dimensions.map { "$input-$it" }
		.map { KnotHash(it) }
		.map { it.toBinaryString() }
		.map { it.toCharArray() }
		.toTypedArray()
val regions = dimensions.map { row ->
	dimensions.count { column -> isRegion(row,column) }
}.sum()
println("Found $regions region fragments.")

fun isRegion(row:Int, column:Int): Boolean {
	return if( grid[row][column] == '1') {
		zeroFill(row,column);
		true;
	} else {
		false;
	}
}

fun zeroFill(row: Int, column: Int) {
	grid[row][column] = 'x'
	directions.forEach { (rowDelta, columnDelta) ->
		val adjacentRow = row + rowDelta
		val adjacentColumn = column + columnDelta
		if( adjacentRow in dimensions && adjacentColumn in dimensions && grid[adjacentRow][adjacentColumn] == '1' )
			zeroFill(adjacentRow,adjacentColumn)
	}
}
