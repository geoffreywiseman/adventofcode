module Decompressor
	def Decompressor.decompress(input)
		state = :copy
		output = ""
		buffer_size = 0
		repetitions = 0
		buffer = ""

		input.each_char do |c|
			if state == :copy then
				if c == '(' then
					buffer_size = 0
					state = :buffer_count
				else
					output << c
				end
			elsif state == :buffer_count then
				if c == 'x' then
					raise "No buffer count found." if buffer_size == 0
					state = :repetition_count
					repetitions = 0
				else
					buffer_size = buffer_size * 10 + c.to_i
				end
			elsif state == :repetition_count then
				if c == ')' then
					raise "No repetition count found." if repetitions == 0
					state = :buffering
					buffer = ""
				else
					repetitions = repetitions * 10 + c.to_i
				end
			elsif state == :buffering then
				buffer << c
				if buffer.length == buffer_size then
					output << buffer * repetitions
					state = :copy
				end
			end
		end
		output
	end

	def Decompressor.estimate(input)
		state = :estimate
		length = 0
		buffer_size = 0
		repetitions = 0
		buffer = ""

		input.each_char do |c|
			if state == :estimate then
				if c == '(' then
					buffer_size = 0
					state = :buffer_count
				else
					length += 1
				end
			elsif state == :buffer_count then
				if c == 'x' then
					raise "No buffer count found." if buffer_size == 0
					state = :repetition_count
					repetitions = 0
				else
					buffer_size = buffer_size * 10 + c.to_i
				end
			elsif state == :repetition_count then
				if c == ')' then
					raise "No repetition count found." if repetitions == 0
					state = :buffering
					buffer = ""
				else
					repetitions = repetitions * 10 + c.to_i
				end
			elsif state == :buffering then
				buffer << c
				if buffer.length == buffer_size then
					length += repetitions * Decompressor.estimate(buffer)
					state = :estimate
				end
			end
		end
		length
	end
end
