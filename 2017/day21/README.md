# 2017, Day 21: Fractal Art

## Part 1

Phew. Looks tough. Just splitting and rejoining has its own sort of pain.  

Trying to decide if I should handle this as a multi-dimensional array of 
numbers (Byte?), or an array of strings, or something else entirely. The
structure needs to support splitting in 2x2 or 3x3, and rejoining into a
grid. The 2x2/3x3 portions need to support matching with rotation and
flipping -- which could be handled by figuring out all the permutations
of the matches up front and then just doing straight matching during
iteration, which might be less painful computationally.


