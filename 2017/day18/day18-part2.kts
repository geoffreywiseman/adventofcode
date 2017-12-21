#!/usr/bin/env kscript
//DEPS org.jetbrains.kotlinx:kotlinx-coroutines-core:0.20

import java.io.File
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.channels.*

class Program(
	val programId:Int,
	private val instructions: List<String>, 
	private val sendChannel:Channel<Long>, 
	private val receiveChannel:Channel<Long>) {

	private val registers = mutableMapOf<Char, Long>('p' to programId.toLong())

	suspend fun run(): Int {
		var iterations = 0
		var instructionIndex = 0
		var sends = 0

		while (instructionIndex < instructions.size) {
			val instruction = instructions[instructionIndex]
			println("$programId @ $instructionIndex - $registers")
			//println("$programId: Running instruction #$instructionIndex: ${instruction}")
			//println("$programId:\tregisters: ${registers}")
			val tokens = instruction.split(' ')
			val command = tokens.first()
			val arguments = tokens.drop(1)

			when (command) {
				"snd" -> {
					sends += 1
					println( "$programId:\tAttempting send of value #$sends...")
					sendChannel.send(getArgumentValue(arguments[0]))
					//println("$programId:\tSent ${getArgumentValue(arguments[0])} (${arguments[0]})")
				}
				"set" -> {
					registers[arguments[0].single()] = getArgumentValue(arguments[1])
					//println("$programId:\tSet register ${arguments[0]} to ${getRegisterValue(arguments[0])}")
				}
				"add" -> {
					registers[arguments[0].single()] = getRegisterValue(arguments[0]) + getArgumentValue(arguments[1])
					//println("$programId:\tAdded ${arguments[1]} to ${arguments[0]} resulting in ${registers[arguments[0].single()]}")
				}
				"mul" -> {
					registers[arguments[0].single()] = getRegisterValue(arguments[0]) * getArgumentValue(arguments[1])
					//println("$programId:\tMultiplied ${arguments[0]} by ${arguments[1]}, resulting in ${registers[arguments[0].single()]}")
				}
				"mod" -> {
					registers[arguments[0].single()] = getRegisterValue(arguments[0]) % getArgumentValue(arguments[1])
					//println("$programId:\tDivided ${arguments[0]} by ${arguments[1]}, with remainder ${registers[arguments[0].single()]}")
				}
				"rcv" -> {
					//println("$programId:\tAttempting receive...")
					registers[arguments[0].single()] = receiveChannel.receive()
					//println("$programId:\tReceived ${registers[arguments[0].single()]} and placed in ${arguments[0]}.")
				}
				"jgz" -> {
					if (getArgumentValue(arguments[0]) > 0) {
						instructionIndex = instructionIndex + getArgumentValue(arguments[1]).toInt() - 1
						//println("$programId:\tJumped by ${arguments[1]}, arriving at ${instructionIndex + 1}")
					} //else
						//println("$programId:\tRegister ${arguments[0]} is <= 0, no jump performed.")
				}
			}
			instructionIndex += 1
			iterations += 1
		}
		println("$programId: Ran out of instructions! ($instructionIndex out of ${instructions.size}")
		return sends
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
val oneToZero = Channel<Long>(Channel.UNLIMITED)
val zeroToOne = Channel<Long>(Channel.UNLIMITED)

runBlocking {
	val jobZero = launch {
		val sends = Program(0,instructions,zeroToOne,oneToZero).run()
		println("Program 0 completed after sending $sends values.")
	}
	val jobOne = launch {
		val sends = Program(1,instructions,oneToZero,zeroToOne).run()
		println("Program 1 completed after sending $sends values.")
	}
	//println( "Programs started." )
	jobZero.join()
	jobOne.join()
}

