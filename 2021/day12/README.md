# Day 11
Harder than it ought to have been -- I tripped over a few things in my own code and ended up having to spend a fair amount of time debugging.

## Part 1

I modeled the path as an array of strings, the connections as a map of source room to destination rooms, and then pretty much did a breadth-first walk.  Got the right answer fairly quickly and moved on.

## Part 2

Didn't want to have to keep checking the entire array for duplicates, so ... felt better to turn the array into a class that could store more information. I tried a couple of paths, but settled on something that knew if it could support duplicates and, if it could, which duplicate it had already used if any.

Ended up in a situation where I'd modify the class I was checking with the duplicate before passing it on to the child which broke the algorithm, because checking the next possible exit, it would think the duplicate had already been settled. Anyway, it doesn't really matter, but there was a bug and it took me a bit to find. Once I fixed that bug, the rest went smoothly.

## Tools

TypeScript again. Less tripping over the mechanics because I'd done it recently. WebStorm still doesn't notice things that I feel like it ought to -- but I don't think VS Code would be better. Basically, I'm used to having more tool support than I'm getting and it's sometimes annoying that the tool isn't telling me when i'm doing something clearly wrong.

It's working fine, but I think it could be better.