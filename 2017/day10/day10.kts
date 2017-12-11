class Cord(length: Int) {
	val marks = Array(length) { index -> index }

	var skip = 0
	var position = 0

	val firstProduct: Int
		get() {
			return marks[0] * marks[1]
		}

	fun switch(onePosition: Int, twoPosition: Int) {
		val oneIndex = onePosition % marks.size;
		val twoIndex = twoPosition % marks.size;
		val holding = marks[oneIndex]
		marks[oneIndex] = marks[twoIndex]
		marks[twoIndex] = holding
	}

	fun reverse(startPosition: Int, endPosition: Int) {
		var start = startPosition
		var end = endPosition
		while (start < end) {
			switch(start, end)
			start += 1
			end -= 1
		}
	}

	fun twist(length: Int) {
		if (length > 0) {
			val start = position
			val end = position + length - 1
			reverse(start, end)
		}
		position += length + skip
		skip += 1
	}
}

fun partOne() {
	print("[Part 1]\n")
	val cord = Cord(256)
	arrayOf(46, 41, 212, 83, 1, 255, 157, 65, 139, 52, 39, 254, 2, 86, 0, 204).forEach { length ->
		cord.twist(length)
	}
	print("First Product: ${cord.firstProduct}\n")
}
partOne()

fun denseHash(sparseHash: Array<Int>): Array<Int> {
	val hash = Array(16) { 0 }
	for( denseIndex in 0..15 ) {
		for( sparseIndex in (denseIndex*16)..((denseIndex+1)*16-1) ) {
			hash[denseIndex] = hash[denseIndex] xor sparseHash[sparseIndex]
		}
	}
	return hash
}

fun partTwo(input: String) {
	print("\n[Part 2]\n")
	val cord = Cord(256)
	repeat(64) {
		input.forEach { cord.twist(it.toInt()) }
		arrayOf(17, 31, 73, 47, 23).forEach { cord.twist(it) }
	}
	val hash = denseHash(cord.marks)
			.map { it.toString(16) }
			.map { if( it.length == 2 ) it else "0$it"}
			.joinToString("")
	print("Dense hash '$hash' from input: '$input'\n")
}
partTwo("46,41,212,83,1,255,157,65,139,52,39,254,2,86,0,204")