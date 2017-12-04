import java.lang.Math.abs

fun getRows(ringMax: Int, ringDimension: Int): Array<ClosedRange<Int>> {
	return arrayOf(
			(ringMax - (ringDimension - 1))..ringMax,
			(ringMax - (ringDimension - 1) * 2)..(ringMax - ringDimension),
			(ringMax - (ringDimension - 1) * 3)..(ringMax - (ringDimension - 1) * 2 - 1),
			(ringMax - (ringDimension - 1) * 4 + 1)..(ringMax - (ringDimension - 1) * 3 - 1)
	)
}

fun getCoordinates(index: Int): Pair<Int, Int> {
	if (index == 1)
		return Pair(0, 0);

	val ringIndex = Math.ceil((Math.sqrt(index.toDouble()) - 1) / 2).toInt()
	val ringDimension = ringIndex * 2 + 1
	val ringMax = Math.pow(ringDimension.toDouble(), 2.0).toInt()
	val rows = getRows(ringMax, ringDimension)

	print("$index:\n\tringDimension:$ringDimension, ringMax:$ringMax, ringIndex:$ringIndex, rows: ${rows.toList()}\n")

	return when (index) {
		in rows[0] -> {
			print("\t$index is on bottom row\n")
			Pair(ringIndex - (ringMax - index), -ringIndex)
		}
		in rows[1] -> {
			print("\t$index is on left row\n")
			Pair(-ringIndex, ringMax - index - (ringDimension - 1) - ringIndex)
		}
		in rows[2] -> {
			print("\t$index is on top row\n")
			Pair((ringMax - index - (ringDimension - 1) * 2 - ringIndex), ringIndex)
		}
		in rows[3] -> {
			print("\t$index is on right row\n")
			Pair(ringIndex, index - (ringMax - (ringDimension - 1) * 4) - ringIndex)
		}
		else -> throw RuntimeException("Can't calulate co-ordinates for index: $index")
	}
}

// 1, 2, 3, 4, 5, 6, 7, 8, 9,12,20,23
// bottom row: 5,6,17,18,19,20
// left row: 5,6,17,18,19,20
// top row: 3, 4, 14, 15, 16
// right row: 2,10,11,12
arrayOf(361527)
		.map { getCoordinates(it) }
		.forEach { (x,y) ->
			val distance = abs(x) + abs(y);
			print("distance from ($x,$y): $distance\n")
		}