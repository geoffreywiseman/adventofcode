# Day 3

Still in Kotlin, tried a new VSCode Extension. Language server crashed immediately, so that didn't really work out. 

Did the work in VSCode, running in Kotlinc and fixing errors as they come up at execution time. Greatly prefer having the support of an IDE with completions and catching errors as I'm writing, rather than when I'm ready to run, but was able to make it through.

Also ran into the issue that `maxByOrNull` doesn't have a way to deal with equality -- so I had to back away from using it on Part 2 and use a `when` instead, which is much more verbose. Curious to see wha others have done here.
