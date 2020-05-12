# [Google FooBar](https://foobar.withgoogle.com/)
My solutions to Google FooBar challenge! I named the files different from the tasks and forgot the names :'(

## Salute
We are given a string of >-<, and we want to find how many > are followed by <. An implementation problem. We keep count
 of > so far, and we add to the result the value of the current counter when we encounter <.
## String Factor Search
We are given a string, and we want to find the minimal substring such that the whole string can be cyclically shifted so
 that it becomes a reparation of that substring. For example: for "abcabc", the substring "bca" works.
Try all possible substring such that the length of the substring is a divisor of the length of the given string. This
 works in ![O(N^2 * d(N))](https://render.githubusercontent.com/render/math?math=O(N%5E2%20*%20d(N))) where N is the length of the string and d(X) is the divisors counting function.
## Binary Fuel
We are given a big number, and we have three operations:
1. Divide the number by 2
2. Add 1
3. Subtract 1
Find the minimum number of operations to make the given number equal to 1.

Solution: when we look at the number in binary: 
divide by 2 means erasing a zero,
add 1 means finding the current interval of 1 at the beginning of the number and make them all zero and push a one
 afterwards.
Subtract 1 means replacing the current 1 at the beginning with 0.

So if we have zero: use operation 1. If we have 1 followed by 0: use operation 3, otherwise: use operation 2.
## Integer Cycle
We are given a number in some base let's say "210022", we are going to construct to numbers x and y by sorting the
 digits of the number ascendingly and descendingly in that respective order (i.e. x = "222100" and y = "001222
 ). Replace the given number with the difference between x and y. It's guaranteed that we're going to encounter a cycle
  of numbers. Find the length of that cycle.
  
I created my own subtract on Strings, but I then realized that I could have used BigInteger :'(. The solution is to
 simulate because usually the cycle is very small.
## Map BFS
We are given a map where some cells are blocks, and some are not. We start from cell (0,0) and have to
 reach cell (h-1, w-1). Find the shortest path if we can unblock at most one cell.
 
The solution is to use the regular BFS but add to the state the number of cells that we removed.
## Terminal Probability
We are given a graph with probability on edges (m(u,v) = probability of going to v if we are at u). We start at node
 0 and there are some terminal nodes that don't go to any other nodes. We want to find the probability that we reach
  each of these nodes.

Let P(v) = probability of reach node v at least once. We notice that P(v) = Sum(m(u,v) * P(u) for all nodes u). It
 follows that we can construct a system of linear equations and solve for P(v). 
 A technical note: need to add a dummy start x with P(x) = 1 added to the system as otherwise all equation will be
  equal to 0.
## Bunny flow
We are given a network graph and want to find the maximum flow. That's both the problem and solution.
## Laser
For me this was the hardest, and my solution is most likely not the best.

We are standing in a bounded plane with walls parallel to the axises. There is an enemy at a given point, and we are
 standing at another point. We can fire a bullet, the bullet can reflect/bounce when it hits the walls. However, the
  bullet diminish after moving a given distance. Find the number of directions that we can fire at such that the
   bullets kills the enemy but doesn't kill us.
   
My solution is based on a small trick: when the bullet hit a wall, flip the plane around that wall and continue. This
 way, we avoid calculating reflection and angles. Another thing, the direction vector must be possible to represent
  in integer coordinates (since we are given integer points). So we can generate all co-prime integers and tread them
   as the direction victor and simulate the process.
## Nebula DP
We are given a grid of booleans. In one step, a cell becomes true if EXACTLY 1 of (itself, the ones to its right
, bottom, and bottom right) was true in the previous step, otherwise the cell becomes false. We want to walk backward
 one step. Find how many possible grids can generate the current grid in one step. The height of the grid is small < 9.
 
We use Dynamic Programming: DP\[column\]\[previous column state\], 