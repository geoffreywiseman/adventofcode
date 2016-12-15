import scala.io.Source
import scala.collection.mutable._

class Simulator(filename:String) {

	val BotAssignmentInstruction = "value (\\d+) goes to bot (\\d+)".r
	val ComparisonInstruction = "bot (\\d+) gives low to (bot|output) (\\d+) and high to (bot|output) (\\d+)".r 

	var bots: Map[Integer,Bot] = HashMap[Integer,Bot]()
	var outputs: Map[Integer,Output] = HashMap[Integer,Output]()

	Source.fromFile(filename).getLines.foreach { line =>
		line match {
			case BotAssignmentInstruction(chipIndex,botIndex) => bot(botIndex.toInt).giveChip(chipIndex.toInt)
			case ComparisonInstruction(source,lowType,lowIndex,highType,highIndex) => 
				bot(source.toInt).setComparison(destination(lowType,lowIndex.toInt), destination(highType,highIndex.toInt))
		}
	}

	def bot(index:Integer): Bot = {
		bots.getOrElseUpdate(index, { new Bot(index.toString) })
	}

	def output(index:Integer): Output = {
		outputs.getOrElseUpdate(index, { new Output(index.toString) })
	}


	def simulate() {
		printState()
		var nextBot = bots.values.find(_.ready)
		while( nextBot.isDefined ) {
			nextBot.foreach(_.process)
			nextBot = bots.values.find(_.ready)
		}
	}

	def printState() {
		println( s"Bots:    ${bots}" )
		println( s"Outputs: ${outputs}" )
	}

	def destination(destinationType:String, index:Integer): Destination = {
		destinationType match {
			case "bot" => bot(index)
			case "output" => output(index)
		}
	}
}

trait Destination {
	def giveChip(index:Integer)
}

class Bot(val name:String) extends Destination {
	var chips: Buffer[Integer] = ListBuffer[Integer]()
	var comparison: Option[(Destination,Destination)] = None
	var history: Buffer[(Integer,Integer)] = ListBuffer[(Integer,Integer)]()

	def giveChip(index:Integer) {
		chips.append(index)
		if( chips.size > 1 )
			chips = chips.sorted
	}

	override def toString: String = {
		return s"Bot[$name]"
	}

	def setComparison( low: Destination, high:Destination ) {
		comparison = Option( (low,high) )
	}

	def ready: Boolean = {
		chips.size == 2
	}

	def process {
		comparison.foreach { case (lowDest, highDest) =>
			val low = chips.head
			val high = chips.last
			lowDest.giveChip(low)
			highDest.giveChip(high)
			chips.clear()
			history.append( (low,high) )
			println( s"Bot $name gave $low to $lowDest and $high to $highDest" )
		}
	}
}

class Output(val name:String) extends Destination {
	var chips: Buffer[Integer] = ListBuffer[Integer]()

	def giveChip(index:Integer) {
		chips.append(index)
	}

	override def toString: String = {
		return s"Output[$name]"
	}
}

new Simulator("puzzleInput.txt").simulate()