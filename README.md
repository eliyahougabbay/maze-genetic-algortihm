# Maze resolved by genetic algorithm
The goal is to sovle a maze by a genetic algorithm. Aim to simulate natural selection.

<p align="center">
  <img src="screenshots\window.png" />
</p>


## The maze generation
The maze is drawn by setting each side a value such as : North = 1, Est = 2, South = 4, West = 8.
<br>To generate the maze, we first set the size and so the limits. Then, for each square of the maze, we randomly select a value in the set {1, 2, 4, 8} coresponding to either the North, Est, South, West, and try to remove the wall. If the next square is out of bounds or has already been visited, a new value is selected until success, otherwise, we turned back to the previous case and so on, until completion.

## The solution
In order to solve the labyrinth, first, a random path is created but instead of real DNA sequence such as 
```console
A C G T A C T G G
```
One may be : 
```console
Est West Est Est North South South North
```
corresponding to each direction the path should go. <br>
<br>
Then the genetic algorithm compute the right sequence to solve the maze.

### Before resolution

> Sequences and scores are not related to the labyrinth above.

First, random sequences are generated, and scores are computed by a an Euclidean norm. <br>
*The better the score, the better the sequence.* 

<p align="center">
  <img src="screenshots\genetic_DNA.png" />
</p>

<p align="center">
  <img src="screenshots\scores.png" />
</p>

### After resolution

After resolution, each individual share the same DNA sequence (the path to the end square) and scores (euclidean distances) are low. <br>

<p align="center">
  <img src="screenshots\solved_genetic_DNA.png" />
</p>

<p align="center">
  <img src="screenshots\solved_scores.png" />
</p>

> The euclidean norm should be equal to 0, however the sequence path stop one square further the labyrinth end.
