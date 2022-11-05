# Number-Maze
## Description

This game is written in **JAVA** language

with the help of **ENIGMA** library,

using **Object oriented** and **stack** and **queue** structures.

To run the project:
- project --> configure built path --> libraries --> classpath --> select enigma lib and apply.

## Game Information

- The game is played in a 23*55 game field including walls.
- Game characters are numbers (1-9).
- Human player is represented by a blue number.
- Computer controls other numbers.

The aim of the game is reaching the highest score by collecting and matching the computer numbers.
## Game Chracters

`Blue number` Human number   
- Cursor keys: To move human player number 
- Q: Transfer one item from right backpack to left backpack
- W: Transfer one item from left backpack to right backpack

`Other numbers` Computer numbers
- Green numbers: 1, 2, 3. These numbers do not move (static numbers).  
- Yellow numbers: 4, 5, 6. These numbers move randomly.  
- Red numbers: 7, 8, 9. These numbers do **path finding** to reach the blue number.  
- Red numbers' targeted path will be marked in the game area.

## Input List

Computer numbers (1-9) are inserted into the maze area from an input list. The input list (size of 10 numbers) is always full of numbers, and shows the next item which will be inserted into the maze. During the game, an item is inserted into the maze every 5 seconds.

| Item | Generation probability | Score factor|
| ---- | ---- | ---- |
| 1, 2, 3 | %75 | 1 |
| 4, 5, 6 | %20 | 5 |
| 7, 8, 9 | %5 | 25 |

## Backpacks

Human player have two backpacks: Left and right. These are used to put collected numbers. Each backpack size is 8. 

## Game Initilization

The game area is loaded from "maze.txt" file at the beginning of the game. Blue human number (5) and 25 computer numbers (from the input queue) are inserted into the maze area at random positions. 

## Game Playing Information

When the human player meets a computer number at the same square,
- if computer number is greater than the human number, human player dies 
- if computer number is less or equal than the human number, human player collects the computer number. 

Collected numbers are put in the left backpack directly. Human player can transfer the top item from a backpack to the other one, if there is a place to put.

When numbers at the two backpacks are the same, at the same backpack level, they are matched. Matched numbers disappear from backpacks and turn into score. Also each matching operation adds 1 to the human player's number.

If human player number exceeds 9, it turns into 1 again. Human player cannot move when his/her number is 1. There are two options to move again;
- Another matching operation makes human player number 2
- Otherwise, after 4 seconds, human number will be 2

If the left backpack is full when the human player collects a new number, the top item of the left backpack disappears, then new collected number is inserted into the left backpack.

## Scoring

Score is calculated as follows when the human player succeeds a matching operation. 

	`Score  =  Matched_Number  *  Matched_Number_Score_Factor`
