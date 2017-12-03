import java.io.File

File("input.txt").readLines().map { line ->
	val matches = line.mapIndexed { index, current ->
		val next = line[ ( index + 1 ) % line.length ]
		if( current == next ) Character.getNumericValue(current) else null;
	}
	val sum = matches.filterNotNull().sum()
	print("Answer: $sum\n")
}