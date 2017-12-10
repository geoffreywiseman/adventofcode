import java.io.File

val linePattern = Regex("([a-z]+) (inc|dec) (-?\\d+) if ([a-z]+) (>|<|<=|>=|==|!=) (-?\\d+)")
val registers = mutableMapOf<String, Int>()

fun compare(comparisonRegister: String, comparison: String, comparisonAmount: Int): Boolean {
	val registerValue = registers.getOrDefault(comparisonRegister, 0)
	return when (comparison) {
		">" -> registerValue > comparisonAmount
		"<" -> registerValue < comparisonAmount
		"<=" -> registerValue <= comparisonAmount
		">=" -> registerValue >= comparisonAmount
		"==" -> registerValue == comparisonAmount
		"!=" -> registerValue != comparisonAmount
		else -> throw IllegalArgumentException("Unknown comparison: $comparison")
	}
}

fun operate(targetRegister: String, operator: String, targetOperand: Int) {
	val originalValue = registers.getOrDefault(targetRegister, 0)
	val revisedValue = when (operator) {
		"inc" -> originalValue + targetOperand
		"dec" -> originalValue - targetOperand
		else -> throw IllegalArgumentException("Unknown operator: $operator")
	}
	registers[targetRegister] = revisedValue
}

fun parseLine(text: String) {
	val result = linePattern.matchEntire(text)
	if (result == null) {
		print("Cannot parse line: $text\n")
	} else {
		print("Considering instruction: $text\n")
		val (targetRegister, operator, targetOperand, comparisonRegister, comparison, comparisonAmount) = result.destructured
		if (compare(comparisonRegister, comparison, comparisonAmount.toInt())) {
			print("Comparison successful: $comparisonRegister $comparison $comparisonAmount\n")
			operate(targetRegister, operator, targetOperand.toInt())
			print("Operation performed: $targetRegister $operator $targetOperand\n")
		} else {
			print("Comparison failed: $comparisonRegister $comparison $comparisonAmount\n")
		}
	}
}

var allTimeMax: Int? = null
var currentMax: Int? = null
File("day08-input.txt").readLines().forEach { line ->
	parseLine(line)
	currentMax = registers.values.max()
	allTimeMax = if( allTimeMax == null ) currentMax else maxOf(currentMax!!, allTimeMax!!)
}
print("Registers: $registers\n")
print("Largest value at any time during run: $allTimeMax\n")
print("Largest value at end of run: $currentMax\n")