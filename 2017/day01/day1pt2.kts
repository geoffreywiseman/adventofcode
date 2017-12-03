import java.io.File

File("day1pt2.txt").readLines().mapIndexed { lineIndex, line ->
	print("Line $lineIndex:\n")
	val matchDelta = line.length/2
	val matches = line.mapIndexed { index, current ->
		val matchIndex = (index + matchDelta) % line.length
		val next = line[ matchIndex ]
		if( current == next ) Character.getNumericValue(current) else null;
	}
	val sum = matches.filterNotNull().sum()
	print("\tsum: $sum\n")
}