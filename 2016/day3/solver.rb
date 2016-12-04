class Solver 

	def initialize(filename)
		@filename = filename
		@parsed = 0
		@valid = 0
	end

	def solve()
		File.open(@filename).each do |line|
			sides = parse(line)
			validate(sides) unless sides.nil?
		end
		print_results
	end

	def parse(line)
		@parsed += 1
		match = /^\s*(\d+)\s+(\d+)\s+(\d+)$/.match line
		match.captures.map { |s| s.to_i }
	end

	def validate(sides)
		@valid += 1 if valid? sides
	end

	def valid?( sides )
		if( sides[0] + sides[1] <= sides[2] )
			false
		elsif( sides[0] + sides[2] <= sides[1] )
			false
		elsif( sides[1] + sides[2] <= sides[0] )
			false
		else
			true
		end
	end

	def print_results
		print("Solver done: parsed #{@parsed}, valid #{@valid}")
	end
end
