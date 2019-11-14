# Conway's Game of Life

This is a visual representation of Conway's Game of Life. The yellow boxes represent animals. Animals can starve due to isolation, be smothered by overpopulation, or survive and repopulate if the conditions are right. The simulation counts the number of iterations in the console and can be started or stopped at any time. The board can be randomized but only if the simulation is currently stopped. The counter only resets after restarting the program.

## Rules

Each cell in the grid determines its next state (either occupied or unoccupied) by the following criteria:

* Any occupied cell with fewer than two occupied neighbors becomes unoccupied (dies of underpopulation)
* Any occupied cell with two or three occupied neighbors stays occupied (survives to the next generation)
* Any occupied cell with more than three occupied neighbors becomes unoccupied (dies of overpopulation)
* Any Unoccupied cell with exactly three neighbors becomes occupied (born due to ideal reproductive conditions)

## Controls

* **Start/Stop** - Enter
* **Randomize** - R
