# Day 7, in JavaScript

I imagined when reading this problem that the position that would consume the least fuel in Part 1 was the mean. It isn't. My intuition for this problem is clearly off. I then went to a brute-force approach, which was pretty fast, so I left it there.

I didn't know the name of the triangular number formula, so I had to start with "factorial, but addition" and figure out what it was called first.

# Tools
I used JetBrains WebStorm and Node v17. Went ok. Had to poke around to re-locate `readFileSync` because the async version feels painful and isn't necessary for this job. Feels like async line-reading a file should be easier than it looks to be.

# See Also
Seems like the median will work to calculate the position for Part 1. Felt wrong to me, but apparently works, so, again, my intuition isn't right for this problem.  Having said that, that won't help for Part 2, so I'm ok with not having used that path.