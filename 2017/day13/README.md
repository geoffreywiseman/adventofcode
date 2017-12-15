# 2017, Day 13: Packet Scanners

# Part 1
Looks easy enough
- Parse the input into a map
- Find the deepest layer
- Iterate picoseconds until past deepest layer
- For each layer, check if caught (picoseconds % range)
- If caught, add security
- Can probably do with a range (picoseconds) and fold.

Ah -- scanner doesn't wrap around at the end of the range, it bounces back. So the scanner's position is not `picosecond % range`, it's ...

Was somewhat painful to come up with a good way of calculating the scanner position. I have something that works, but I don't really like it. Seems like there ought to be something that's pure math, no conditional logic.

# Part 2
Mostly just a minor reorganization of the work I'd already done for part 1. Easy.  