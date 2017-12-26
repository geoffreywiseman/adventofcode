import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

fun Triple<Long, Long, Long>.manhattanSum(): Long = this.first.absoluteValue + this.second.absoluteValue + this.third.absoluteValue

data class Particle(
		var position: Triple<Long, Long, Long>,
		var velocity: Triple<Long, Long, Long>,
		val acceleration: Triple<Long, Long, Long>
)

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

fun getIndefinitelyNearest(particles: List<Particle>): Int {
	val byAcceleration = particles.groupBy { it.acceleration.manhattanSum() }
	val lowAcceleration = byAcceleration[byAcceleration.keys.min()!!]!!
	println("Lowest ${lowAcceleration.size} acceleration particles have an acceleration manhattan sum of ${byAcceleration.keys.min()}")
	if (lowAcceleration.size == 1) {
		return particles.indexOf(lowAcceleration.single())
	}
	val byVelocity = lowAcceleration.groupBy { it.velocity.manhattanSum() }
	val lowVelocity = byVelocity[byVelocity.keys.min()!!]!!
	println("Lowest ${lowVelocity.size} velocity particles (after low-acceleration filtering) have a velocity manhattan sum of ${byVelocity.keys.min()}")
	if (lowVelocity.size == 1) {
		return particles.indexOf(lowVelocity.single())
	}
	val byPosition = lowVelocity.groupBy { it.position.manhattanSum() }
	val lowPosition = byPosition[byPosition.keys.min()!!]!!
	println("Lowest ${lowPosition.size} position particles (after low-accel/low-velo filtering) have a position manhattan sum of ${byPosition.keys.min()}")
	if (lowVelocity.size == 1) {
		return particles.indexOf(lowVelocity.single())
	} else {
		throw IllegalStateException("More than one ${lowPosition.size} low-acceleration, low-velocity, low-position particle!")
	}
}

val particles = Parser().parse("input.txt")
println("Parsed ${particles.size} particles")

val index = getIndefinitelyNearest(particles)
println( "Indefinitely nearest particle is: ${index}" )
