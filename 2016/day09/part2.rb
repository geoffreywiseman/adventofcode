require_relative 'decompressor'

File.open( "puzzleInput.txt", 'r' ) do |f|
	estimate = Decompressor.estimate(f)
	print( "Decompressed length: #{estimate}")
end