def parse(filename)
  lines = File.readlines(filename)
  raise "Not just one line?" if lines.size != 1
  lines[0].split(',').map(&:to_i)
end

def simulate(name, filename, days)
  input = parse(filename)
  school = Array.new(9, 0)
  input.each { |x| school[x] += 1 }
  days.times do |day|
    spawning = school.shift
    school[6] += spawning
    school.push(spawning)
  end
  print("#{name}: @#{days}d there are #{school.sum()} fish\n")
end

simulate( 'Part 1', 'puzzle.txt', 80)
simulate( 'Part 2', 'puzzle.txt', 256)