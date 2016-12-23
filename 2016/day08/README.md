# Day 8

123 lines of non-comment Swift.

## Part 1

Part 1 was the hardest yet for sme, for a few reasons:
- I did it in Swift, but not in a playground, just in Sublime Text, which meant:
    - No code completion
    - No warnings without running interpreter
    - Useless stack traces in the case of failure
    - I forgot to `import Foundation`, which meant that I got weird errors about missing methods, probably because they're in extension classs in Foundation?
- NSRegularExpression has always been a clumsy API, but with Swift 3.x it is worse than ever -- I had to make an extension just to get the job done. I really regretted using Swift when I decided to use regexp.

## Part 2

Once I finished Part 1, Part 2 was already done -- I had been displaying the pixel screen all along, in part as a means of validating that the code was working and in part because I was curious to see it.