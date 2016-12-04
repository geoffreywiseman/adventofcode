import scala.io.Source

case class Room(name:String, sector:Int, checksum:String) {
	def real: Boolean = {
		calculatedChecksum == checksum
	}

	def calculatedChecksum: String = {
		val charFreq = name.filterNot(_=='-')
			.foldLeft(Map[Char,Int]()) { (map,char) =>
			map + (char -> (map.getOrElse(char,0) + 1))
		}
		charFreq.toSeq
			.sorted( Ordering.by[(Char,Int),(Int,Char)] {
				case (c,f) => (-f,c)
			})
			.take(5)
			.map(_._1)
			.mkString( "" )
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
val sumValid = lines.flatMap(Room.fromString).filter(_.real).map(_.sector).sum

printf( "Sum of valid room sector ids: " + sumValid )
