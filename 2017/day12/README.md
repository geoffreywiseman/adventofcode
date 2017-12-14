# Day 12: Digital Plumber

# Part 1
I parsed the input into a map of int to ints, then just ran through to collect all the transitive connections, avoiding cycles by tracking a collection of unvisited items.

Would like a functional approach to graph walking, but I didn't find one quickly.

# Part 2
Grab all the programs (graph keys), then count up as I take the first program, take all the transitive connections and remove them from the list of programs.

