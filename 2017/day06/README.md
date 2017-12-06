# 2017, Day 6

# Part 1

Part 1 wasn't too bad, although it's not quite as elegant as I would like. I didn't want to iterate over the array repeatedly to do the incrementing, and I found a way not to do that, but that way is still a little clunky, I feel like it can be refined further, but I didn't quickly think of a way to make it cleaner.

# Part 2

Part 2 was a minor enhancement on Part 1. I just took the history defined in Part 1 and ran an indexOfFirst, then subtracted it from the history length to get the cycle length, and took one more off that to get the number of reallocations in the cycle.