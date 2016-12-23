class Solver 

	def initialize(triangles)
		@triangles = triangles
		@parsed = 0
		@valid = 0
	end

	def solve()
		@triangles.each do |t|
			@parsed += 1
			validate(t)
		end
		print_results
	end

	def validate(triangle)
		@valid += 1 if valid? triangle
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
