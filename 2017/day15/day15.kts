val mask = 0xffffL

fun generator(seed: Int, factor: Int): Sequence<Long> {
	return generateSequence(seed.toLong()) { lastValue ->
		lastValue * factor % Int.MAX_VALUE
	}
}

fun duel(rounds: Int, generators: Pair<Sequence<Long>, Sequence<Long>>): Int {
	return generators.first
			.zip(generators.second)
			.take(rounds)
			.filter { (first, second) -> (first and mask) == (second and mask) }
			.count()
}

// Part 1
val first = generator(618, 16807)
val second = generator(814, 48271)
val unfilteredMatches = duel(40_000_000, Pair(first, second))
println("Found ${unfilteredMatches} unfiltered matches") // 577

// Part 2
val filteredMatches = duel(5_000_000, Pair(
		first.filter { it % 4 == 0L },
		second.filter { it % 8 == 0L }
))
println("Found ${filteredMatches} filtered matches")
