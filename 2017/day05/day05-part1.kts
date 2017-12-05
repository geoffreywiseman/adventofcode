import java.io.File

fun jump() {
	val jump = jumps[pointer]
	jumps[pointer] += 1
	pointer += jump
	jumpsTaken += 1
	print("Jumped to $pointer\n")
}

val jumps = File("input.txt").readLines().map { it.toInt() }.toIntArray()
var pointer = 0
var jumpsTaken = 0;

while (pointer < jumps.size) {
	jump();
}
print("Exit found in $jumpsTaken jumps\n")
