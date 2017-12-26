# 2017, Day 20: Particle Swarm

## Part 1

Plan A:
- Create particle class
- Parse input into particles
- Define method that returns min distance to origin
- Define method that returns min finality, such that final if:
  - velocity and acceleration have same sign for both axes
  - velocity and position have same sign for both axes
- Collect finality, if no false, get min distance, otherwise step, repeat.

In other words, we know the minimum distance for a particle when it is accelerating away from origin permanently -- when the velocity, acceleration and position all match.


Plan B:
- Apparently I misread the puzzle:
  - It's not a question of which particle will be at the closest point during the run
  - It's a question of which particle will be the closest if you let it run indefinitely, essentially
- Acceleration trumps all the other factors -- position and velocity will eventually fall to acceleration
- Similarly, no matter how far out you are, if your velocity is slower, you will eventually be passed by something with faster acceleration
- So smallest acceleration first, then smallest velocity, then closest position?

Yeah, that works fine.

## Part 2

Ok, so having already figured out how to decide if particles are "fully determined" and move during Part 1, Plan A,
I simply reused that logic to decide when to stop checking collisions, although technically it is insufficient -- it
is still possible to have collissions after each particle is accelerating away from origin -- one might start
farther out but slower. But I got the right answer, so I didn't try and look for a better routine.

For collisions, I used a simple survivor collection, group by position, filter by value size, flatten and remove.
