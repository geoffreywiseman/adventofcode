require_relative 'solver'

class TriangleParser

	def enumerate
		Enumerator.new do |y|
			lines = File.readlines( "puzzleInput.txt" )
			raise "Invalid number of lines #{lines.size}" if lines.size % 3 != 0

			line_index = 0
			until line_index + 3 > lines.size
				rows = [
					parse( lines[ line_index ] ),
					parse( lines[ line_index+1 ] ),
					parse( lines[ line_index+2 ] )
				]
				y << [ rows[0][0], rows[1][0], rows[2][0] ]
				y << [ rows[0][1], rows[1][1], rows[2][1] ]
				y << [ rows[0][2], rows[1][2], rows[2][2] ]

				line_index += 3
			end
		end
	end

	def parse(line)
		match = /^\s*(\d+)\s+(\d+)\s+(\d+)$/.match line
		match.captures.map { |s| s.to_i }
	end

end

sample = TriangleParser.new.enumerate.take(9)
puts( "Triangles: #{sample}" )

solver = Solver.new( TriangleParser.new.enumerate )
solver.solve()