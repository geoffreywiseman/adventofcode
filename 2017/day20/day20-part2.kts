import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

fun Triple<Long, Long, Long>.manhattanSum(): Long = this.first.absoluteValue + this.second.absoluteValue + this.third.absoluteValue

data class Particle(
		var position: Triple<Long, Long, Long>,
		var velocity: Triple<Long, Long, Long>,
		val acceleration: Triple<Long, Long, Long>
) {
	val fullyDetermined: Boolean
		get() {
			return fullyDetermined(position.first, velocity.first, acceleration.first) &&
					fullyDetermined(position.second, velocity.second, acceleration.second) &&
					fullyDetermined(position.third, velocity.third, acceleration.third)
		}

	fun fullyDetermined(position: Long, velocity: Long, acceleration: Long): Boolean {
		return (velocity == 0L || position.sign == velocity.sign) &&
				(acceleration == 0L || velocity.sign == acceleration.sign)
	}

	fun move() {
		velocity = Triple(
				velocity.first + acceleration.first,
				velocity.second + acceleration.second,
				velocity.third + acceleration.third
		)
		position = Triple(
				position.first + velocity.first,
				position.second + velocity.second,
				position.third + velocity.third
		)
	}
}

class Parser {
	private val LINE_PATTERN = Regex("\\s*p=<([\\-0-9,]+)>,\\s*v=<([\\-0-9,]+)>,\\s*a=<([\\-0-9,]+)>\\s*")

	fun buildParticle(text: String): Particle {
		val result = LINE_PATTERN.matchEntire(text)
		if (result == null) throw IllegalStateException("Line doesn't match expected pattern: $text")
		var (pos, vel, acc) = result.destructured
		return Particle(
				parseTriple(pos),
				parseTriple(vel),
				parseTriple(acc)
		)
	}

	fun parseTriple(text: String): Triple<Long, Long, Long> {
		val values = text.split(',')
				.map { it.toLong() }
		if (values.size != 3)
			throw IllegalArgumentException("Tried to parse triple from '$text' but found ${values.size} values")
		return Triple(values[0], values[1], values[2])
	}

	fun parse(filename: String): List<Particle> {
		return File(filename).readLines().map(this::buildParticle)
	}
}

fun getSurviving(particles: List<Particle>): Int {
	val survivors = particles.toMutableList()
	var moves = 0
	while (survivors.any { !it.fullyDetermined }) {
		val positions = mutableMapOf<Triple<Long, Long, Long>, Int>()
		survivors.forEach {
			it.move()
			positions[it.position] = positions.getOrDefault(it.position, 0) + 1
		}
		moves += 1
		val collided = survivors.groupBy { it.position }
				.values
				.filter { it.size > 1 }
				.flatten()
		survivors.removeAll(collided)
		println( "After move $moves, ${survivors.size} particles remain")
	}
	println("Moved $moves times before resolving all collisions.")
	return survivors.size
}

val particles = Parser().parse("input.txt")
println("Parsed ${particles.size} particles")

val surviving = getSurviving(particles)
println("There are $surviving surviving particles.")
