# Day 9

If I stick to the pattern, today should be Ruby. Ok with me.

## Part 1

Looks like I can write a decompressor, then pipe the file into it, character by character, stripping whitespace? Not very clear about line feeds, but I assume those are ignored too?

I enjoyed the fact that File and String both support each_char, so my tests (minitest) pass a string and my part1 code passes a file reference, and the decompressor doesn't have to change at all.

## Part 2

I thought perhaps part 2 would require the output, so I went ahead and generated it in Part 1. But Part 2 really doesn't -- I could have solved part 1 with estimation too, but ... I'll just solve part 2 that way.

## Difficulty

Surprisngly easy, actually. This was one of my faster solves.

