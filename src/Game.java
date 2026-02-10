import Pieces.*;

public class Game {
    private Board chessboard;
    private HumanPlayer whitePlayer;

    public Game(){
        classical_chess();
    }

    public void setupWhitePlayer(){
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

        for ( int i = 0; i < 8; i++ ){
            String name = "Pawn_" + i + "_white";
            String imgPath = "w" + i;
            Pawn pw = new Pawn( name, "White", imgPath, new Position( 1, i ), 1 );
            this.chessboard.setPiece(pw);
        }
    }

    public void setupBlackPlayer(){
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

        for ( int i = 0; i < 8; i++ ){
            String name = "Pawn_" + i + "_black";
            String imgPath = "b" + i;
            Pawn pb = new Pawn( name, "Black", imgPath, new Position( 6, i ), -1 );
            this.chessboard.setPiece(pb);
        }
    }

    public void classical_chess(){
        this.chessboard = new Board( 8, 8 );
        this.whitePlayer = new HumanPlayer( "White" );

        setupWhitePlayer();
        setupBlackPlayer();

        while ( true ){
            String valid = "";
            Move m;

            while ( !valid.equals( "Valid move" ) ){
                m = this.whitePlayer.player_move( this.chessboard );
                valid = chessboard.movePiece( m );

                System.out.println( valid );

                boolean pawn_promotion_possible = this.pawn_promotion_allowed( this.chessboard, m );
                if ( pawn_promotion_possible ){
                    String pawn_promotion_user_input = this.whitePlayer.pawn_promotion(m.to);
                    this.chessboard = this.handle_pawn_promotion( this.chessboard, m, pawn_promotion_user_input, this.whitePlayer.getPlayerName() );
                }

                System.out.println( "Pawn promotion allowed: " + pawn_promotion_possible );
                System.out.println( "The king is in check: " + check_king_in_check( kb1, this.chessboard ) );
                System.out.println( "Checkmate: " + check_checkmate( kb1 ) );
            }
        }
    }



    public boolean pawn_promotion_allowed( Board board, Move move ){
        //To check if someone could promote a pawn to a piece

        //Check if the move was into the last or first row of the board
        int row = move.to.get_row();
        if ( row == 0 || row == board.get_max_row() - 1 ){

            //Check if the piece was a pawn
            Position mp = move.to;
            return board.getPiece(mp.get_row(), mp.get_col()).getType().toLowerCase().contains("pawn");
        }
        return false;
    }

    public Board handle_pawn_promotion( Board board, Move move, String piece_type, String playerName ){
        //Check which option the player chose when promoting the pawn and switch the piece to the chosen piece
        Piece switched_piece;
        int id = 1;
        for ( Piece p : board.getPieces() ){
            if (p.getType().toLowerCase().contains(piece_type)){
                id++;
            }
        }
        switch ( piece_type ){
            case "queen" -> switched_piece = new Queen( "Queen_" + id + "_" + playerName.toLowerCase(), playerName, "qw", move.to );
            case "rook"  -> switched_piece = new Tower( "Tower_" + id + "_"  + playerName.toLowerCase(), playerName, "qw", move.to );
            case "knight"-> switched_piece = new Horse( "Horse_" + id + "_"  + playerName.toLowerCase(), playerName, "qw", move.to );
            case "bishop"-> switched_piece = new Runner( "Runner_" + id + "_" + playerName.toLowerCase(), playerName, "qw", move.to );
            default -> switched_piece = board.getPiece( move.to.get_row(), move.to.get_col() );
        }

        //Remove the pawn and replace it with the chosen piece
        board.removePiece( move.to.get_row(), move.to.get_col() );
        board.setPiece( switched_piece );
        return board;
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