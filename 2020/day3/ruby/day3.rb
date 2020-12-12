@lines = File.readlines('../input.txt', chomp: true)
@max_col = @lines[0].size
@max_row = @lines.size

def calc_slope(row_delta, col_delta)
  col = 0
  row = 0
  trees = 0

  while row < @max_row do
    trees += 1 if @lines[row][col] == '#'
    col = (col + col_delta) % @max_col
    row += row_delta
  end

  print("#{trees} trees hit on slope (#{row_delta}, #{col_delta})\n")
  trees
end

trees = []
trees << calc_slope(1, 1)
trees << calc_slope(1, 3)
trees << calc_slope(1, 5)
trees << calc_slope(1, 7)
trees << calc_slope(2, 1)
print("product: #{trees.inject(:*)}\n")

