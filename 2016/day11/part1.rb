class Floor
	def initialize(name,generators,microchips)
		@name = name
		@generators = generators
		@microchips = microchips
	end

	def to_s
		return "#{@name.rjust(7)}: #{contents}"
	end

	def describe(elevator)
		prefix = elevator ? "E " : ". "
		return "#{@name.rjust(7)}: #{contents(prefix)}"
	end

	def contents(prefix="")
		return prefix +
			( @generators.map { |element| "#{element[0].upcase}G" } + @microchips.map { |element| "#{element[0].upcase}M" } )
			.join( " " )
	end

end

class Arrangement
	def initialize( floors )
		@elevator_index = 0
		@floors = floors
	end

	def to_s
		@floors
			.each_with_index
			.map { |floor,index| floor.describe( index == @elevator_index ) }
			.reverse
			.join( "\n" )
	end
end

class ArrangementParser

	def initialize(filename)
		@floors = File.new(filename).each_line.map(&method(:parse_floor))
	end

	def parse_floor(line)
		if /The (?<floor_name>.*) floor contains (?<contents>.*)./ =~ line then
			(generators,microchips) = parse_contents(contents)
			Floor.new(floor_name,generators,microchips)
		else
			raise "Line doesn't match pattrn: #{line}"
		end
	end

	def parse_contents(contents)
		generators = []
		microchips = []

		if contents != "nothing relevant" then
			contents.split(/ and |, (and )?/).each do |item|
				case item
				when /a (\w+) generator/ then
					generators << $1
				when /a (\w+)-compatible microchip/ then
					microchips << $1
				else
					raise "Unexpected item: #{item}"
				end
			end
		end

		return generators,microchips
	end

	def initialArrangement
		return Arrangement.new(@floors)
	end
end

initial = ArrangementParser.new("sampleInput.txt").initialArrangement
print("Initial arrangement:\n#{initial}")