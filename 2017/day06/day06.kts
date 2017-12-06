print("Day 06, Part 1\n\n")

class Allocator(var state: Array<Int>) {
	fun getLargestIndex(): Int {
		return state.indices.maxBy { state[it] }!!
	}

	fun reallocate(): Array<Int> {
		val newState = Array<Int>(state.size) { index -> state[index] }
		val largestIndex = getLargestIndex()
		val largest = state[largestIndex]
		val quotient = largest / state.size
		val remainder = largest % state.size
		val remainderRange = (largestIndex + 1)..(largestIndex + remainder)
		val remainderIndicies = remainderRange.toList().map { it % state.size }

		newState[largestIndex] = 0
		newState.indices.forEach {
			newState[it] += quotient
			if (remainderIndicies.contains(it))
				newState[it] += 1
		}

		state = newState
		return state
	}
}

class CycleMonitor(val allocator: Allocator) {
	val history = mutableListOf(allocator.state)

	fun reallocateUntilCycle(): Int {
		var reallocationsPerformed = 0
		do {
			val newState = allocator.reallocate()
			reallocationsPerformed += 1
			val cycleFound = history.any { it contentEquals newState }
			history.add(newState)
		} while (!cycleFound)
		return reallocationsPerformed
	}
}

//val initialState = arrayOf(0,2,7,0)
val initialState = arrayOf(4,10,4,1,8,4,9,14,5,1,14,15,0,15,3,5)//arrayOf(0,2,7,0)
val allocator = Allocator(initialState)
val monitor = CycleMonitor(allocator)

val reallocations = monitor.reallocateUntilCycle()
print("Cycle found after $reallocations reallocations:\n")
//monitor.history.forEach { print("\t${it.contentToString()}\n") }
val last = monitor.history.last()
val cycleStart = monitor.history.indexOfFirst { it contentEquals last }
val cycleLength = monitor.history.size - cycleStart
print("Cycle start index is ${cycleStart}, length of ${cycleLength} so cycle had ${cycleLength-1} reallocations")