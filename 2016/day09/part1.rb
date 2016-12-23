require_relative 'decompressor'

File.open( "puzzleInput.txt", 'r' ) do |f|
	decompressed = Decompressor.decompress(f)
	print( "Decompressed length: #{decompressed.length}")
end