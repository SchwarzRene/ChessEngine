import Pieces.*;

public class Game {
    private Board chessboard;
    private HumanPlayer whitePlayer;

    public Game(){
        classical_chess();
    }

    public void classical_chess(){
        this.chessboard = new Board( 8, 8 );
        this.whitePlayer = new HumanPlayer( "White" );

        //White pieces
        Runner rw1 = new Runner( "Runner_1_white", "White", "rw", new Position( 0, 1 ) );
        this.chessboard.setPiece(rw1);

        Runner rw2 = new Runner( "Runner_2_white", "White", "rw", new Position( 0, 6 ) );
        this.chessboard.setPiece(rw2);

        Tower tw1 = new Tower( "Tower_1_white", "White", "tw", new Position( 0, 0 ) );
        this.chessboard.setPiece(tw1);

        Tower tw2 = new Tower( "Tower_2_white", "White", "tw", new Position( 0, 7 ) );
        this.chessboard.setPiece(tw2);

        Horse hw1 = new Horse( "Horse_1_white", "White", "hw", new Position( 0, 2 ) );
        this.chessboard.setPiece(hw1);

        Horse hw2 = new Horse( "Horse_2_white", "White", "hw", new Position( 0, 5 ) );
        this.chessboard.setPiece(hw2);

        Queen qw1 = new Queen( "Queen_1_white", "White", "qw", new Position( 0, 4 ) );
        this.chessboard.setPiece(qw1);

        King kw1 = new King( "King_1_white", "White", "kw", new Position( 0, 3 ) );
        this.chessboard.setPiece(kw1);

        //Black pieces
        Runner rb1 = new Runner( "Runner_1_black", "Black", "rb", new Position( 7, 1 ) );
        this.chessboard.setPiece(rb1);

        Runner rb2 = new Runner( "Runner_2_black", "Black", "rb", new Position( 7, 6 ) );
        this.chessboard.setPiece(rb2);

        Tower tb1 = new Tower( "Tower_1_black", "Black", "tb", new Position( 7, 0 ) );
        this.chessboard.setPiece(tb1);

        Tower tb2 = new Tower( "Tower_2_black", "Black", "tb", new Position( 7, 7 ) );
        this.chessboard.setPiece(tb2);

        Horse hb1 = new Horse( "Horse_1_black", "Black", "hb", new Position( 7, 2 ) );
        this.chessboard.setPiece(hb1);

        Horse hb2 = new Horse( "Horse_2_black", "Black", "hb", new Position( 7, 5 ) );
        this.chessboard.setPiece(hb2);

        Queen qb1 = new Queen( "Queen_1_black", "Black", "qb", new Position( 7, 4 ) );
        this.chessboard.setPiece(qb1);

        King kb1 = new King( "King_1_black", "Black", "kb", new Position( 7, 3 ) );
        this.chessboard.setPiece(kb1);


        while ( true ){
            String valid = "";
            Move m;

            while ( !valid.equals( "Valid move" ) ){
                m = this.whitePlayer.player_move( this.chessboard );
                valid = chessboard.movePiece( m );
                System.out.println( valid );
                System.out.println( "The king is in check: " + check_king_in_check( kb1, this.chessboard ) );
                System.out.println( "Checkmate: " + check_checkmate( kb1 ) );
            }
        }
    }

    public void SwitchPawn( Board b ){
        for ( Piece p : b.getPieces() ){
            if ( p.getPlayerName().contains( "Pawn" ) ){
                if p.
            }
        }
    }

    public boolean check_king_in_check( Piece king, Board board ){
        Position king_pos = king.get_pos();

        //Check every piece
        for ( Piece p : board.getPieces() ){
            //Check if a piece is from the enemy
            if ( !p.getPlayerName().equals( king.getPlayerName() ) ){
                //Check if a possible position of an enemy piece is the same as the king's position'
                Position[] vm = p.valid_moves( board );
                for ( Position pos : vm ){
                    //If yes, the king is in check
                    if ( pos.get_col() == king_pos.get_col() && pos.get_row() == king_pos.get_row() ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean check_checkmate( Piece king ){
        //Check if the king is in check
        if ( check_king_in_check( king, this.chessboard ) ){
            //If the king is in check, check every piece if there is a possiblilty to avoid check
            for ( Piece p : this.chessboard.getPieces() ){
                if ( p.getPlayerName().equals( king.getPlayerName() ) ){
                    Position[] vm = p.valid_moves( this.chessboard );

                    Board board_copy = this.chessboard.copy();
                    for ( Position vm_p : vm ){
                        board_copy.movePiece( new Move( p.get_pos(), vm_p, p.getPlayerName() ) );
                    }

                    if ( !this.check_king_in_check( king, board_copy ) ){
                        return false;
                    }
                }
            }
            return true;
        } else{
            return false;
        }
    }
}