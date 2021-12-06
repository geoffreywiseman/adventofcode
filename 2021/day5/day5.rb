class Line
  attr_reader :x1, :y1, :x2, :y2

  def initialize(coords)
    @x1,@y1,@x2,@y2=coords
    x_points = (@x1-@x2).abs+1
    y_points = (@y1-@y2).abs+1
    if horz?
      @points = x_points
    elsif vert?
      @points = y_points
    else
      raise "line isn't horz/vert/diag: #{coords}" if x_points != y_points
      @points = x_points
    end
  end

  def horz?
    @y1==@y2
  end

  def vert?
    @x1==@x2
  end

  def to_s
    "#{@x1},#{@y1}->#{@x2},#{@y2} (#{@points})"
  end

  def points
    indices(@x1,@x2).zip(indices(@y1,@y2))
  end

  private

  def indices(one,two)
    return (one..two).to_a if one<two
    return one.downto(two).to_a if one>two
    Array.new(@points, one)
  end
end

class Map
  attr_reader :lines

  def initialize(lines)
    @lines=lines
  end

  def extents
    mx = (@lines.each.map(&:x1) + @lines.each.map(&:x2)).max()
    my = (@lines.each.map(&:y1) + @lines.each.map(&:y2)).max()
    [mx, my]
  end

  def self.parse(filename,incl_diagonal)
    lines = []
    File.readlines(filename).each do |line|
      match = line.match(/(\d+),(\d+) -> (\d+),(\d+)/)
      if match.nil?
        print("Unexpected line: #{line}")
        return
      end
      lines << Line.new(match.captures.map { |n| n.to_i })
    end
    lines.filter! { |l| l.horz? or l.vert? } unless incl_diagonal
    Map.new(lines)
  end

  def to_s
    "Map [#{@lines.join(' ')}]"
  end

  def to_a
    mx, my = extents()
    array = Array.new(my+1) { Array.new(mx+1, 0) }
    @lines.flat_map(&:points).each do |point|
      array[point.last][point.first] += 1
    end
    array
  end
end

def part1(filename)
  map = Map.parse(filename, false)
  map.to_a.flatten.count { |c| c>1 }
end

def part2(filename)
  map = Map.parse(filename, true)
  map.to_a.flatten.count { |c| c>1 }
end

one = part1('puzzle.txt')
print("1: #{one}\n")

two = part2('puzzle.txt')
print("2: #{two}\n")
