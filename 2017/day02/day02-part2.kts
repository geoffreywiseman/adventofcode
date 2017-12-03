import java.io.File

val sum = File("day02-part2.txt").readLines().mapIndexed { index, line ->
	val values = line.splitToSequence(" ")
		.filter { token -> token.length > 0 }
		.map { token -> token.toInt() }
	val (divisor,dividend) = getDivisible(values)
	print( "Line $index: $dividend / $divisor\n")
	dividend/divisor;
}.filterNotNull().sum();
print("Answer: $sum\n")

fun getDivisible(values: Sequence<Int>) : Pair<Int,Int> {
	values.forEachIndexed { leftIndex, leftValue ->
		values.forEachIndexed { rightIndex, rightValue -> 
			if( leftIndex != rightIndex && leftValue < rightValue && ( rightValue % leftValue ) == 0 )
				return Pair(leftValue,rightValue)
		}
	}
	throw RuntimeException( "No divisible pair." )
}