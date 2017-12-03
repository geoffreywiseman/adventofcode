import java.io.File

File("day1pt1.txt").readLines().mapIndexed { lineIndex, line ->
	val matches = line.mapIndexed { index, current ->
		val next = line[ ( index + 1 ) % line.length ]
		if( current == next ) Character.getNumericValue(current) else null;
	}
	val sum = matches.filterNotNull().sum()
	print("Line $lineIndex: $sum\n")
}