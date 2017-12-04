import java.io.File

val valids = File("input.txt").readLines().map { line ->
	validPassphrase(line)
}
val validCount = valids.count { it }

print( "There are $validCount valid passphrases." )

fun validPassphrase( passphrase: String ): Boolean {
	var uniques = mutableSetOf<String>()
	return passphrase.splitToSequence(" ")
		.filter { it.length > 0 }
		.map { word -> uniques.add(word) }
		.all { it }
}