# 2017, Day 18: Duet

## Part 1

Not too bad. Screwed up twice:

- once by seeking "recv" instead of "rcv" and not double-checking that the case was exhaustive.
- once by using an Int for the register and having one overflow

## Part 2

Neat. Threading and blocking. Need to figure out how best to handle that with Kotlin and that requires a sharper mind, so ... tomorrow.

Ended up doing it with Kotlin's experimental coroutine support. Worked great, although I didn't end up writing the deadlock detection code -- that's possible to write, I just didn't do it. ;)

Mostly because I hit a really serious error where I didn't notice that "jgz 1 3" was there. My "jgz" instruction always assumed the first parameter was a register, which ... was a faulty assumption which it failed to detect, and caused my program not to deadlock.

Took me a while to track that down.

Also, using a dependency with a kotlin script requires a little finagling -- I'm using "kotlin-script", which works, but it's ... a little less than ideal, and then supporting that in IDEA is possible but also not ideal.
