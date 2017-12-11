# 2017, Day 10: Knot Hash

# Part 1
Not too bad.

# Part 2
Two problems:
- It's not that easy to externalize Kotlin classes and still use then in a Kotlin script.
  - I don't want to make a whole project structure for simple problems.
- I didn't consider that Int.toString(16) wouldn't zero-prefix, so I almost ended up with a non-working solution until I realized my answer was 30 char instead of 32.