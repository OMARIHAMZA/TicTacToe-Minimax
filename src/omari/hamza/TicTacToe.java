package omari.hamza;

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

    //Check if there is winner , 10 -> X, -10 -> Y, None -> 0
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

    public int minimax(char board[][], int depth, boolean isMaxTurn) {
        int evaluate = evaluateBoard(board);
        if (evaluate == 10 || evaluate == -10) return evaluate;
        if (checkComplete(board)) return 0;
        char[][] mBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mBoard[i][j] = board[i][j];
            }
        }
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mBoard[i][j] == '_') {
                    if (isMaxTurn) {
                        mBoard[i][j] = 'x';
                        int minimax = minimax(mBoard, depth, !isMaxTurn);
                        if (minimax > maxValue){
                            maxValue = minimax;
                            bestMove = new Cell(i, j);

                        }
                        mBoard[i][j] = '_';
                    } else {
                        mBoard[i][j] = 'o';
                        minValue = Math.min(minValue, minimax(mBoard, depth, !isMaxTurn));
                        int minimax = minimax(mBoard, depth, !isMaxTurn);
                        if (minimax < minValue){
                            minValue = minimax;
                            bestMove = new Cell(i, j);

                        }
                        mBoard[i][j] = '_';
                    }
                }

            }
        }
        return isMaxTurn ? maxValue : minValue;
    }

    Cell bestMove(char board[][]) {
        minimax(board, 4, false);
        return bestMove;
    }
}
