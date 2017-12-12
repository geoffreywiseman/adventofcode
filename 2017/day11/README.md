# 2017, Day 11: Hex Ed

## Part 1

This sent me down a quick research path to see how people handle hex grids in
software. There's a great reference on [red blob games][rbg-hex], which led me
to want to use the cube co-ordinate system -- simple and reasonably elegant.

[rbg-hex]: https://www.redblobgames.com/grids/hexagons/

After that, the rest was pretty easy.

## Part 2
I wasn't saving the whole path, so I had to write a little extra code here to 
fold a pair, both the current position and the farthest. I considered storing 
the whole path than running a maxBy() on it, but ... that seemed unnecessary.