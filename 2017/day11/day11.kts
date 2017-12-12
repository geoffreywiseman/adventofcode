import java.io.File
import java.lang.Math.abs

data class HexCoord(val x: Int, val y: Int, val z: Int) {
	companion object {
		val ORIGIN = HexCoord(0, 0, 0)
	}

	fun distanceFromOrigin(): Int {
		return (abs(x) + abs(y) + abs(z)) / 2
	}

	operator fun plus(coord: HexCoord): HexCoord {
		return HexCoord(x + coord.x, y + coord.y, z + coord.z)
	}
}

val directionDeltas = mapOf(
		"n" to HexCoord(0, 1, -1),
		"ne" to HexCoord(1, 0, -1),
		"se" to HexCoord(1, -1, 0),
		"s" to HexCoord(0, -1, 1),
		"sw" to HexCoord(-1, 0, 1),
		"nw" to HexCoord(-1, 1, 0)
)


fun getDestinationCoordinate(directions: String): HexCoord {
	return directions.splitToSequence(",").fold(HexCoord.ORIGIN) { position, direction ->
		val delta = directionDeltas.get(direction)
		if (delta == null)
			throw IllegalArgumentException("Unexpected direction: $direction")
		position + delta
	}
}

fun getFarthestCoordinate(directions: String): HexCoord {
	val (_,farthest) = directions.splitToSequence(",").fold(Pair(HexCoord.ORIGIN, HexCoord.ORIGIN)) { (current, farthest), direction ->
		val delta = directionDeltas.get(direction)
		if (delta == null)
			throw IllegalArgumentException("Unexpected direction: $direction")
		val nextCurrent = current + delta
		val nextFarthest = if( nextCurrent.distanceFromOrigin() > farthest.distanceFromOrigin() ) nextCurrent else farthest
		Pair(nextCurrent,nextFarthest)
	}
	return farthest
}


// Part 1
fun printDirectionsToDistance(directions: String) {
	val coord = getDestinationCoordinate(directions)
	print("Directions of length ${directions.length} lead to coord $coord at distance ${coord.distanceFromOrigin()}\n")
}
printDirectionsToDistance("ne,ne,ne")
printDirectionsToDistance("ne,ne,sw,sw")
printDirectionsToDistance("ne,ne,s,s")
printDirectionsToDistance("se,sw,se,sw,sw")
printDirectionsToDistance(File("day11-input.txt").readText())

// Part 2
fun printFarthestPosition(directions: String) {
	val coord = getFarthestCoordinate(directions)
	print("Directions would be farthest from origin at $coord and a distance of distance ${coord.distanceFromOrigin()}\n")
}
printFarthestPosition(File("day11-input.txt").readText())
