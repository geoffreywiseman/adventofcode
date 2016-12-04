require_relative 'solver'

class TriangleParser

	def enumerate
		Enumerator.new do |y|
			File.open( "puzzleInput.txt" ) do |f|
				f.each do |line|
					y << parse(line)
				end
			end
		end
	end

	def parse(line)
		match = /^\s*(\d+)\s+(\d+)\s+(\d+)$/.match line
		match.captures.map { |s| s.to_i }
	end

end

solver = Solver.new( TriangleParser.new.enumerate )
solver.solve()