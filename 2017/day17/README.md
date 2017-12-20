# 2017, Day 17: Spinlock

# Part 1

Don't see any easy way to bypass the work, so I'm just going to do a basic
implementation with a mutable list and some modulo arithmetic.

# Part 2

Part 2, on the other hand, it's clearly quite plausible to bypass the work,
by simply keeping track of the length of the buffer and the value at position
1, which will always be the position after the zero value.

If you're actually building the buffer, the program is still doable, but it's 
much slower. Without building the buffer the answer is essentially instant.
