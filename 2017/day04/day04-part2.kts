import java.io.File

val valids = File("input.txt").readLines().mapIndexed { index, line ->
	val valid = validPassphrase(line)
	print( "line $index valid? $valid\n" )
	valid
}
val validCount = valids.count { it }

print( "There are $validCount valid passphrases." )

fun validPassphrase( passphrase: String ): Boolean {
	var letterCounts = mutableSetOf<Map<Char,Int>>()
	return passphrase.splitToSequence(" ")
		.filter { it.length > 0 }
		.map { word ->
			letterCounts.add( getLetterCount(word))
		}.all { it }
}

fun getLetterCount(word:String): Map<Char,Int> {
	return word.fold(mutableMapOf<Char,Int>()) { map, char ->
		val newValue = map.get(char)?.plus(1) ?: 1 
		map.put(char,newValue)
		map
	}
}