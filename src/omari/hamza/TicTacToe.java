package omari.hamza;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class TicTacToe {

    private Cell bestMove;

    boolean checkComplete(char board[][]) {
        int spotsCount = 0;
        for (char[] aBoard : board) {
            for (char anABoard : aBoard) {
                if (anABoard == 'x' || anABoard == 'o') spotsCount += 1;
            }
        }
        return spotsCount == 9;
    }

    //Check if there is winner , 10 -> X, -10 -> O, None -> 0
    public int evaluateBoard(char board[][]) {
        if (board[0][0] == 'x' && board[1][1] == 'x' && board[2][2] == 'x') return 10;
        if (board[0][0] == 'o' && board[1][1] == 'o' && board[2][2] == 'o') return -10;
        int xCount = 0, oCount = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'x') xCount++;
                else if (board[i][j] == 'o') oCount++;
            }
            if (xCount == 3) return 10;
            else if (oCount == 3) return -10;
            else {
                xCount = oCount = 0;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[j][i] == 'x') xCount++;
                else if (board[j][i] == 'o') oCount++;
            }
            if (xCount == 3) return 10;
            else if (oCount == 3) return -10;
            else {
                xCount = oCount = 0;
            }
        }
        return 0;
    }

    public int[] minimax(char board[][], int depth, int alpha, int beta, boolean isMaxTurn) {

        ArrayList<int[]> nextMoves = generateMoves(board, isMaxTurn);
        char currentPlayerSeed = (isMaxTurn ? 'x' : 'o');
        char oppositePlayerSeed = (currentPlayerSeed == 'x' ? 'o' : 'x');
        int bestScore = isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestColumn = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            bestScore = evaluateScore(board, isMaxTurn);
            return new int[]{bestScore, bestRow, bestColumn};
        } else {
            for (int[] move : nextMoves) {
                if (isMaxTurn) {
                    board[move[0]][move[1]] = 'x';
                    currentScore = minimax(board, depth - 1, alpha, beta, !isMaxTurn)[0];
                    if (currentScore > alpha) {
                        alpha = currentScore;
                        bestRow = move[0];
                        bestColumn = move[1];
                    }
                } else {
                    board[move[0]][move[1]] = 'o';
                    currentScore = minimax(board, depth - 1, alpha, beta, !isMaxTurn)[0];
                    if (currentScore < beta) {
                        beta = currentScore;
                        bestRow = move[0];
                        bestColumn = move[1];
                    }
                }
                // Undo move
                board[move[0]][move[1]] = '_';

                if (alpha >= beta) break;
            }
        }
        return new int[]{isMaxTurn ? alpha : beta, bestRow, bestColumn};
    }

    Cell bestMove(char board[][]) {
        int[] result = minimax(board, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        return new Cell(result[1], result[2], result[0]);
    }

    private int evaluateScore(char[][] board, boolean isMaxPlayer) {
        int score = 0;

        //Row 1
        score += evaluateLine(board, new int[]{0, 0, 0}, new int[]{0, 1, 2}, isMaxPlayer);
        //Row 2
        score += evaluateLine(board, new int[]{1, 1, 1}, new int[]{0, 1, 2}, isMaxPlayer);
        //Row 3
        score += evaluateLine(board, new int[]{2, 2, 2}, new int[]{0, 1, 2}, isMaxPlayer);
        //Col 1
        score += evaluateLine(board, new int[]{0, 1, 2}, new int[]{0, 0, 0}, isMaxPlayer);
        //Col 2
        score += evaluateLine(board, new int[]{0, 1, 2}, new int[]{1, 1, 1}, isMaxPlayer);
        //Col 3
        score += evaluateLine(board, new int[]{0, 1, 2}, new int[]{2, 2, 2}, isMaxPlayer);
        //Diagonal
        score += evaluateLine(board, new int[]{0, 1, 2}, new int[]{0, 1, 2}, isMaxPlayer);
        //Alternate diagonal
        score += evaluateLine(board, new int[]{0, 1, 2}, new int[]{2, 1, 0}, isMaxPlayer);

        return score;
    }


    private int evaluateLine(char[][] board, int[] rows, int[] columns, boolean isMaxTurn) {
        int score = 0;
        char currentPlayerSeed = (isMaxTurn ? 'x' : 'o');
        char oppositePlayerSeed = (currentPlayerSeed == 'x' ? 'o' : 'x');

        //First cell
        if (board[rows[0]][columns[0]] == currentPlayerSeed) {
            score = 1;
        } else if (board[rows[0]][columns[0]] == oppositePlayerSeed) {
            score = -1;
        }

        //Second cell
        if (board[rows[1]][columns[1]] == currentPlayerSeed) {
            if (score == 1) {
                score = 10;
            } else if (score == -1) {
                return 0;
            } else {
                score = 1;
            }
        } else if (board[rows[1]][columns[1]] == oppositePlayerSeed) {
            if (score == -1) {
                score = -10;
            } else if (score == 1) {
                return 0;
            } else {
                score = -1;
            }
        }

        //Third cell
        if (board[rows[2]][columns[2]] == currentPlayerSeed) {
            if (score > 0) {
                score *= 10;
            } else if (score < 0) {
                return 0;
            } else {
                score = 1;
            }
        } else if (board[rows[2]][columns[2]] == oppositePlayerSeed) {
            if (score < 0) {
                score *= 10;
            } else if (score > 1) {
                return 0;
            } else {
                score = -1;
            }
        }

        return score;
    }


    private ArrayList<int[]> generateMoves(char[][] board, boolean isMaxPlayer) {
        ArrayList<int[]> nextMoves = new ArrayList<>();

        int evaluation = evaluateScore(board, isMaxPlayer);

        if (evaluation == 100 || evaluation == -100) {
            return nextMoves;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_')
                    nextMoves.add(new int[]{i, j});
            }
        }

        return nextMoves;
    }

}
