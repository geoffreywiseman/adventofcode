import java.io.File

class Machine(private val instructions: List<String>) {
	private val registers = mutableMapOf<Char, Long>()
	private var lastPlayed: Long? = null

	fun runUntilRecovered(): Long? {
		var iterations = 0
		var instructionIndex = 0
		while (instructionIndex < instructions.size) {
			val instruction = instructions[instructionIndex]
			println("Running instruction #$instructionIndex: ${instruction}")
			val tokens = instruction.split(' ')
			val command = tokens.first()
			val arguments = tokens.drop(1)

			when (command) {
				"snd" -> {
					lastPlayed = getArgumentValue(arguments[0])
					println("\tPlayed sound: $lastPlayed")
				}
				"set" -> {
					registers[arguments[0].single()] = getArgumentValue(arguments[1])
					println("\tSet register ${arguments[0]} to ${getRegisterValue(arguments[0])}")
				}
				"add" -> {
					registers[arguments[0].single()] = getRegisterValue(arguments[0]) + getArgumentValue(arguments[1])
					println("\tAdded ${arguments[1]} to ${arguments[0]} resulting in ${registers[arguments[0].single()]}")
				}
				"mul" -> {
					registers[arguments[0].single()] = getRegisterValue(arguments[0]) * getArgumentValue(arguments[1])
					println("\tMultiplied ${arguments[0]} by ${arguments[1]}, resulting in ${registers[arguments[0].single()]}")
				}
				"mod" -> {
					registers[arguments[0].single()] = getRegisterValue(arguments[0]) % getArgumentValue(arguments[1])
					println("\tDivided ${arguments[0]} by ${arguments[1]}, with remainder ${registers[arguments[0].single()]}")
				}
				"rcv" -> {
					if (getRegisterValue(arguments[0]) != 0L) {
						println("\tReceived ${lastPlayed} sound")
						return lastPlayed
					} else
						println("\tRegister ${arguments[0]} is == 0, no receive attempted.")
				}
				"jgz" -> {
					if (getRegisterValue(arguments[0]) > 0) {
						instructionIndex = instructionIndex + getArgumentValue(arguments[1]).toInt() - 1
						println("\tJumped by ${arguments[1]}, arriving at ${instructionIndex + 1}")
					} else
						println("\tRegister ${arguments[0]} is <= 0, no jump performed.")
				}
			}
			instructionIndex += 1
			iterations += 1
		}
		println("Ran out of instructions! ($instructionIndex out of ${instructions.size}")
		return null
	}

	fun getRegisterValue(registerName: String): Long {
		return registers[registerName.single()] ?: 0
	}

	fun getArgumentValue(argument: String): Long {
		if (argument.length == 1 && argument.single().isLetter()) {
			return getRegisterValue(argument)
		} else {
			return argument.toLong()
		}
	}
}

val instructions = File("day18-input.txt")
		.readLines()
		.map { it.trim() }
val firstRecovered = Machine(instructions).runUntilRecovered()
println("First recovered sound: $firstRecovered")