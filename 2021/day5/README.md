# Day 5, in Ruby

This was a harder puzzle, but Ruby ended up feeling like a good choice for it anyway. I used JetBrains RubyMine, which I still find to be ... an ok tool. There were lots of times where I struggled to get what I wanted out of it failing to highlight obvious problems, failing to call up reference documentation. I can't reproduce those problems right now, so maybe there was a state where RubyMine wasn't sure which Ruby I was using initially, and that's been resolved? Not sure. Will have to try again, and/or try another day's work in VSCode / Ruby to see how that goes.

## Part 1
I spent a little time looking at ways of calculating line intersections and honestly that seemed complex in its own right, so I settled back into calculating an array much like the one AoC used for visualization. That was easier, although I did struggle a little coming back up to speed on little things to do with Ruby, which I haven't written much of in the past year or two.

## Part 2
This went pretty smoothly -- rebuilt the way I was generating points for each line so that it would work with diagonals, and honestly it's better than what I had before, so even without diagonals, i'm happier with it. I briefly had a bug where I'd always "count up" each index for calculating points, which only failed with diagonals that were ascending x and descending y, or ascending y and descending x, but took me a little bit of debugging to figure where the problem lay.

## Time
I didn't time it, but this was certainly one of the longer puzzles for me -- Day 4 might have been longer, but Day 4 required more mechanical go code, Ruby is still fairly intention-revealing to me.

## See Also
On a quick scan of the Ruby solutions in the Reddit megathread, [this solution](https://github.com/ciscou/aoc/blob/master/2021/05.rb) is nice. Using `<=>` to get dx/dy and iterating that way is clever. I had to refresh my memory of `<=>`, but it does help keep things compact. I do tend to over-engineer a little on the class side of things for AoC, which adds to the weight of the code, but it's the way I'm used to working.