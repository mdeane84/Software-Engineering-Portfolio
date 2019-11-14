# Square Sums Problem

This is a project I made out of pure curiosity. It is a recursive algorithm that has a worst case complexity of O(N!). Given a naturel number n, find a string of numbers including all natural numbers from 0 to n such that any adjacent numbers add to be a perfect square. I used graph theory to solve this. The algorithm constructs an adjacency matrix representing a graph of all possible adjacencies. It the finds a Hamiltonian path through the graph using a Stack and a recursive formula. A Hamiltonian path is a path that passes through each vertex exactly once. The algorithm uses the 'brute force' method so it quickly slows down with large numbers.

### Notes

* This program has a 'run.bat' file for easy execution. The JAR file needs to be executed from the terminal so that it doesn't immediately hide itself.
* Keep values at 42 or smaller to avoid long processing times
* There are some magic numbers that, out of blind luck, find a solution fairly quickly (52 only takes 4.7 million iterations)
* I got the idea to make this from this Numberphile video: https://www.youtube.com/watch?v=G1m7goLCJDY
