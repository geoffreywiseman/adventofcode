import java.io.File

val sum = File("day02-part1.txt").readLines().mapIndexed { index, line ->
	val values = line.splitToSequence(" ")
		.filter { token -> token.length > 0 }
		.map { token -> token.toInt() }
	val min = values.min()!!
	val max = values.max()!!
	val diff = max - min
	print("Line $index, diff: ${diff}\n")
	diff
}.filterNotNull().sum();
print("Answer: $sum\n")