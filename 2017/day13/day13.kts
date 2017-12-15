import java.io.File

val linePattern = Regex("(\\d+):\\s*(\\d+)")
val firewall = File("day13-input.txt").readLines()
		.map { linePattern.matchEntire(it)!!.groupValues }
		.associate { it[1].toInt() to it[2].toInt() }

fun scannerPosition(picosecond: Int, range: Int): Int {
	if (range == 1)
		return 0;
	val patternLength = (range - 1) * 2
	val index = picosecond % patternLength
	return if (index < range)
		index
	else
		patternLength - index
}

fun severity(picosecond: Int): Int {
	val currentDepth = picosecond
	val rangeAtDepth = firewall[currentDepth]
	if (rangeAtDepth == null)
		return 0;

	val scannerPosition = scannerPosition(picosecond, rangeAtDepth)
	return if (scannerPosition == 0) currentDepth * rangeAtDepth else 0;
}

fun firewallPenetrated(delay:Int): Boolean {
	val depths = 0..firewall.keys.max()!!
	for( depth in depths ) {
		val range = firewall[depth]
		if( range != null && scannerPosition( depth + delay, range ) == 0 )
			return false;
	}
	return true;
}


// Part 1
val picosecondRange = 0..firewall.keys.max()!!
val totalSeverity = picosecondRange.fold(0) { totalSeverity, picosecond -> totalSeverity + severity(picosecond) }
print("Total severity: $totalSeverity\n")

// Part 2
var delay = 0
while(!firewallPenetrated(delay))
	delay +=1
print( "Firewall penetrated at delay $delay\n")