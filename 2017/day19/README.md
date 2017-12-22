# 2017, Day 19: A Series of Tubes

## Part One

Read file as two-dimensional char array (or list of strings), as long as you 
can read it like a char array.

Find the pipe symbol on the top row. Follow it around, only turning at 
interactions ('+') , tracking your direction and crossed letters.  Sounds
feasible.

Hit a few bugs, but basically the plan worked. A couple of things that caused me pain:
- when selecting a turn, not considering a letter to be a turn candidate
- when selecting a turn, not eliminating a 180-degree turn (back-tracking)

## Part Two

Very mild enhancement to Part 1. Went very quickly. Maybe if took shortcuts 
in part 1, would go slower?