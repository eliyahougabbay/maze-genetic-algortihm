# Maze resolved by genetic algorithm
The goal is to sovle a maze by a genetic algorithm. Aim to simulate natural selection.
## The maze
The maze is drawn by setting each side a value such as : North = 0, Est = 1, South = 2, West = 3.
<br>To generate the maze, we first set the size and so the limits. Then, for each square (each path) of the maze, we randomly select a value in the set {0, 1, 2, 3} coresponding to either the North, Est, South, West, and try if we can remove the wall. If it is out of bounds, a new value is selected until success, otherwise, we turned back to the previous case and so on, until completion.


<p align="center">
  <img src="screenshots\maze.png" />
</p>


## The solution
In order to solve the labyrinth, first, a random path is created but instead of real DNA sequence such as 
```console
A C G T A C T G G
```
One may be : 
```console
Est West Est Est North South South North
```
corresponding to each direction the path should go. <br>Then the genetic algorithm compute the right sequence to solve the maze.
