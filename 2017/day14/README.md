# 2017, Day 14: Disk Defragmentation

# Part 1

Phew, building on top of the Knot Hashes from Day 10. That would be a lot of
work if you hadn't already done the knot hashes, but hopefully not too bad
since we have.

So -- basically take the input string and 0-127, combined them together in
a sequence, map to hashes, flat-map to bits and sum?

Seems ... achiveable.

Wasn't too bad -- took the opportunity to improve some of my KnotHash code.

# Part 2

Phew. I don't see any way around turning this into a big-ass array and walking
around it, marking what we've already examined. Sounds painful, but ...


 
