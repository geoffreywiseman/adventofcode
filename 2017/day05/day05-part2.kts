import java.io.File

fun jump() {
	val offset = jumps[pointer]
	if (offset < 3)
		jumps[pointer] += 1
	else
		jumps[pointer] -= 1
	pointer += offset
	jumpsTaken += 1
	if (jumpsTaken % 1000 == 0)
		print("Took $jumpsTaken jumps, currently at position $pointer\n")
}

val jumps = File("input.txt").readLines().map { it.toInt() }.toIntArray()
var pointer = 0
var jumpsTaken = 0;

while (pointer < jumps.size) {
	jump();
}
print("Exit found in $jumpsTaken jumps\n")
