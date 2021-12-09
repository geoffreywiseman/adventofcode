# Day 8 in Python

Something about Day 8 immediately screamed "Python" to me, so that's what I used. Although my python is reasonably current, I still had to remind myself about some pieces of basic syntax (using `in` and `not in` instead of something like `has_key`, remembering that dict and set both use curly braces, etc.)

## The Puzzle
I had heard that the puzzle was hard to parse -- that the description of the puzzle wasn't particularly clear. I didn't find that part to be too problematic for me. Part 1 was pretty easy. Part 2 took time, in part because I was convinced there was a clever route (and there probably is), but honestly the brute-force method (trying every permutation for translation) was fast enough and easier than figuring out the clever way.

## Tools
I used Python 3.7, venv, pyenv and PyCharm. There's a tiny bit of `itertools` but mostly using pretty basic python logic. For a larger project, I do like Poetry, but that seemed like overkill for an Advent of Code puzzle.

## Reviewing Other Solutions
It does look like many people used the approach I didn't bother to finish -- working through the digits one by one to figure out which is which by looking at common segments between digits.



