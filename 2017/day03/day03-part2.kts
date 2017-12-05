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

	return when (index) {
		in rows[0] -> Pair(ringIndex - (ringMax - index), -ringIndex)
		in rows[1] -> Pair(-ringIndex, ringMax - index - (ringDimension - 1) - ringIndex)
		in rows[2] -> Pair((ringMax - index - (ringDimension - 1) * 2 - ringIndex), ringIndex)
		in rows[3] -> Pair(ringIndex, index - (ringMax - (ringDimension - 1) * 4) - ringIndex)
		else -> throw RuntimeException("Can't calulate co-ordinates for index: $index")
	}
}

fun getSum(centre: Pair<Int, Int>): Int {
	val (x, y) = centre
	return listOf(
			Pair(x + 1, y), Pair(x + 1, y + 1), Pair(x, y + 1), Pair(x - 1, y + 1), Pair(x - 1, y), Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1)
	).map { coordinate ->
		calculatedValues.get(coordinate)
	}.filterNotNull().sum();
}

val input = 361527
val calculatedValues = mutableMapOf<Pair<Int, Int>, Int>(Pair(0, 0) to 1)
for (index in 2..100) {
	val coordinate = getCoordinates(index);
	val sum = getSum(coordinate)
	print("Index $index at coordinate $coordinate is: $sum\n");
	if (sum > input) {
		break;
	}
	calculatedValues.put(coordinate,sum)
}