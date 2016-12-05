// Oh yeah, Scala scripts don't really have an include option :/ 
// Kinda sucks. I could set up a classpath, but ... that feels like overkill.

import scala.io.Source

case class Room(name:String, sector:Int, checksum:String) {
	def decodedName:String = {
		def decode(in:Char): Char = {
			in match {
				case '-' => ' '
				case _   	=> rotate(in,sector)
			}
		}
		def rotate(in:Char, times:Int): Char = ((((in - 'a') + times) % 26) + 'a').toChar
		
		name.map(decode).mkString( "" )
	}
}

object Room {
	val Pattern = """([a-z\-]+)-(\d+)\[([a-z]{5})\]""".r
	def fromString( string: String ): Option[Room] = {
		string match {
			case Pattern(name,sector,checksum) =>
				Option(new Room(name,sector.toInt,checksum))
			case _ =>
				None
		}
	}
}

val lines = Source.fromFile( "puzzleInput.txt" ).getLines
val sumValid = lines.flatMap(Room.fromString).foreach { room =>
	println( s"${room.decodedName} in sector ${room.sector}" )
}
