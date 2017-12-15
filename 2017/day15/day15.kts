class Generator(seed: Int, val factor: Int, val filter: (Long) -> Boolean = { true }) {
	private var lastValue = seed.toLong()
	fun next(): Long {
		var value = lastValue
		do {
			value = (value * factor) % 2147483647L
		} while( !filter(value) )
		lastValue = value
		return value
	}
}

fun duel(generators: Array<Generator>, rounds: Int): Int {
	var matches = 0
	repeat(rounds) {
		val values = generators.map { it.next() }
				.map { it.toString(2) }
				.map { it.padStart(32, '0') }
				.map { it.takeLast(16) }
		if (values.first() == values.last())
			matches += 1
	}
	return matches
}

// Part 1
val unfiltered = arrayOf(
		Generator(618, 16807),
		Generator(814, 48271)
)
val unfilteredMatches = duel(unfiltered, 40_000_000))
println("Found ${unfilteredMatches} unfiltered matches") // 577

// Part 2
val filtered = arrayOf(
		Generator(618, 16807) { it % 4 == 0L },
		Generator(814, 48271) { it % 8 == 0L }
)
val filteredMatches = println(duel(filtered, 5_000_000))
println("Found ${filteredMatches} filtered matches")
