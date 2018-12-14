# TicTacToe
Alpha-beta pruning Implementation in Tic-Tac-Toe Game

## Heuristic Evaluation Function:

1. +100 for each 3 in-a-line for the Maximizer
2. +10 for each 2 in-a-line with an empty cell for the Maximizer
3. +1 for each 1 in-a-line with two empty cells for the Maximizer
4. Negative scores for opponent
5. 0 Otherwise

We compute the score for each line of the 8 lines (3 rows, 3 columns, 2 diagonals) and obtain the sum.



