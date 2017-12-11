import java.io.File
import java.util.*

enum class State {
	GROUP,
	GARBAGE,
	IGNORE,
	COMMA
}

class Parser(val text: String) {
	var score = 0
	val stack = ArrayDeque<State>()
	var nonCancelledGarbageCharacters = 0

	fun parse() {
		text.forEach { char ->
			// print( "Stack: $stack\n")
			when (stack.peekFirst()) {
				State.IGNORE -> stack.pop()
				State.GARBAGE -> {
					when (char) {
						'>' -> {
							stack.pop()
							stack.push(State.COMMA)
						}
						'!' -> stack.push(State.IGNORE)
						else -> nonCancelledGarbageCharacters += 1
					}
				}
				State.COMMA -> {
					when (char) {
						',' -> stack.pop()
						'}' -> {
							stack.pop()
							closeGroup()
						}
						else -> throw IllegalStateException("In COMMA state and received $char")
					}
				}
				null, State.GROUP -> {
					when (char) {
						'<' -> stack.push(State.GARBAGE)
						'{' -> stack.push(State.GROUP)
						'}' -> closeGroup()
					}
				}
			}
		}
	}

	fun closeGroup() {
		val groupDepth = stack.count { it == State.GROUP }
		score += groupDepth
		stack.pop()
	}
}

val total = File("day09-input.txt").readLines().fold(0) { sum, line ->
	val parser = Parser(line)
	parser.parse()
	print("Score for line: $line\n")
	print("Non-cancelled garbage: ${parser.nonCancelledGarbageCharacters}\n")
	sum + parser.score
}

print("Total score: $total\n")

