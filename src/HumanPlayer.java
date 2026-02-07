import Pieces.Move;
import Pieces.Board;
import Pieces.Piece;
import Pieces.Position;

import java.util.Scanner;

public class HumanPlayer {
    private String playerName;

    public HumanPlayer( String playerName ){
        this.playerName = playerName;
    }

    public Move player_move( Board b ){
        Scanner scanner = new Scanner(System.in);

        Position from;
        int toRow, toCol;

        boolean validPosition = false;
        b.printBoard();
        do {
            validPosition = false;
            //Ask the player which piece to move and if it does not belong to the player, ask again
            Piece p;
            boolean again;
            do {
                System.out.println("Enter your move:");
                System.out.println("Enter row and column of the piece you want to move:");
                int fromRow = scanner.nextInt() - 1;
                int fromCol = scanner.nextInt() - 1;
                from = new Position(fromRow, fromCol);

                //Get the piece
                p = b.getPiece(fromRow, fromCol);

                again = false;
                if (p == null) {
                    System.out.println("This place is empty. Please try again.");
                    again = true;
                } else if (!p.getPlayerName().equals(this.playerName)){
                    System.out.println("This piece does not belong to you. Please try again.");
                    again = true;
                }

            } while (again);

            //Get the valid moves for the piece
            System.out.println("You chose the piece: " + p.getType());
            b.printMoves( p.valid_moves( b ) );

            System.out.println("To choose a different piece enter a number outside of the range of valid moves.");
            System.out.println("Enter row and column of the destination:");
            toRow = scanner.nextInt() - 1;
            toCol = scanner.nextInt() - 1;


            for ( Position pos : p.valid_moves( b ) ) {
                if ( pos.get_row() == toRow && pos.get_col() == toCol ){ validPosition = true; break; }
            }
            if ( !validPosition ){ System.out.println("This position is not valid. Please try again."); }

        } while ( ( toRow > b.get_max_row() || toCol > b.get_max_col()  ) || !validPosition );

        Position to = new Position( toRow, toCol );

        return new Move( from, to, this.playerName );
    }
}
