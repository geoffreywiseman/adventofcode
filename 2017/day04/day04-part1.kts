import java.io.File

val valids = File("input.txt").readLines().map { line ->
	validPassphrase(line)
}
val validCount = valids.count { it }

print( "There are $validCount valid passphrases." )

fun validPassphrase( passphrase: String ): Boolean {
	var uniques = mutableListOf<String>()
	return passphrase.splitToSequence(" ")
		.filter { it.length > 0 }
		.map { word ->
			if(uniques.contains(word))
				false
			else {
				uniques.add(word)
				true
			}
		}.all { it }
}