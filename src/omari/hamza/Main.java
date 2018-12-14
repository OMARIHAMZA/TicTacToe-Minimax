package omari.hamza;

public class Main {

    public static void main(String[] args) {



        char board[][] =
                {
                        {'x', 'o', 'x'},
                        {'o', '_', '_'},
                        {'_', '_', '_'}
                };

        TicTacToe ticTacToe = new TicTacToe();
        Cell bestCell = ticTacToe.bestMove(board);

        System.out.println("The Optimal Move is :");
        System.out.println("Row : " + bestCell.getX() + ", Col : " + bestCell.getY());
    }

}
