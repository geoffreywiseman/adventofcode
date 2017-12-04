# 2017, Day 4

## Part 1

Looks easy enough. Read all the lines, split into tokens, discard any duplicates, count what remains. Discarding duplicates could be done with a fold?

Couldn't figure out how to short-circuit the fold, so ended up breaking it out into a function and using a map/all structure instead.

## Part 2

Confirmed briefly that I could call .contains() on a Set<Map<Char,Int>> and that .add() would return false if .contains() returned true, at which point I knew I could use that as the letter count comparison for the anagrams.

