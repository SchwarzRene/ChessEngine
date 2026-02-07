package Pieces;

public class Board {
    private Piece[][] chessboard;
    private Piece[] pieces;

    public Board copy(){
        Board board_copy = new Board( this.chessboard.length, this.chessboard[0].length );
        for ( int row_idx = 0; row_idx < this.chessboard.length; row_idx++ ){
            for ( int col_idx = 0; col_idx < this.chessboard[0].length; col_idx++ ){
                board_copy.chessboard[row_idx][col_idx] = this.chessboard[row_idx][col_idx];
            }
        }
        board_copy.pieces = this.pieces;;
        return board_copy;
    }

    public Board( int rows, int cols ){
        this.chessboard = new Piece[rows][cols];
        this.pieces = new Piece[0];
    }

    public Piece[][] getBoard(){
        return this.chessboard;
    }

    public String setPiece( Piece piece ){
        int x = piece.get_pos().get_row();
        int y = piece.get_pos().get_col();

        if ( this.chessboard[x][y] == null ){
            Piece[] pieces_new = new Piece[ this.pieces.length + 1];
            System.arraycopy( this.pieces, 0, pieces_new, 0, this.pieces.length );
            pieces_new[ this.pieces.length ] = piece;
            this.pieces = pieces_new;


            this.chessboard[piece.get_pos().get_row()][piece.get_pos().get_col()] = piece;
            return "Successfully set";
        }

        return "Position already filled";
    }

    public boolean valid_position( Position pos ){
        if ( pos.get_col() >= 0 && pos.get_col() < this.chessboard.length ){
            return pos.get_row() >= 0 && pos.get_row() < this.chessboard[0].length;
        }
        return false;
    }

    public String movePiece( Move move ){
        int fromRow = move.from.get_row();
        int fromCol = move.from.get_col();
        int toRow = move.to.get_row();
        int toCol = move.to.get_col();




        //Check if the move is in the board
        if ( !valid_position( move.from ) || !valid_position( move.to ) ){ return "Invalid move"; }

        //Check if position of the move is not empty
        if ( this.chessboard[fromRow][fromCol] == null ){ return "Invalid move"; }

        //Check if the piece is from the player
        if ( !this.chessboard[fromRow][fromCol].getPlayerName().equals( move.playerName ) ){ return "Invalid move"; }

        //Check if the position to move to is empty or belongs to the other player
        if ( this.chessboard[toRow][toCol] != null ){
            Piece to_piece = this.chessboard[toRow][toCol];
            if ( to_piece.getPlayerName().equals( move.playerName ) ){ return "Invalid move"; }
        }


        if ( this.chessboard[toRow][toCol] != null ){
            Piece to_piece = this.chessboard[toRow][toCol];

            Piece[] pieces_new = new Piece[ this.pieces.length - 1];
            int idx = 0;
            for ( Piece p : this.pieces ){
                if ( !( to_piece.get_pos().get_col() == p.get_pos().get_col() && to_piece.get_pos().get_row() == p.get_pos().get_row() ) ){
                    pieces_new[idx] = p;
                    idx++;
                }
            }
            this.pieces = pieces_new;
        }

        this.chessboard[toRow][toCol] = this.chessboard[fromRow][fromCol];
        this.chessboard[fromRow][fromCol] = null;

        this.chessboard[toRow][toCol].set_pos( new Position( toRow, toCol ) );
        return "Valid move";
    }

    public Piece getPiece( int row, int col ){
        return this.chessboard[row][col];
    }

    public int get_max_row(){
        return this.chessboard.length;
    }
    public int get_max_col(){
        return this.chessboard[0].length;
    }

    public void printMoves( Position[] moves ){
        System.out.println("Chessboard:");
        System.out.println( " | 1  2  3  4  5  6  7  8" );
        System.out.println( " |--+--+--+--+--+--+--+--|");


        for ( int row_idx = 1; row_idx <= this.chessboard.length; row_idx++ ){
            System.out.print( row_idx + "|" );

            for ( int col_idx = 1; col_idx <= this.chessboard[0].length; col_idx++ ) {
                String symbol = "  ";

                Piece piece = this.chessboard[row_idx-1][col_idx-1];
                if ( piece != null ){ symbol = piece.getImgPath(); }

                for ( Position pos : moves ) {
                    if ( pos.get_row() == row_idx - 1 && pos.get_col() == col_idx - 1 ){ symbol = " x"; break; }
                }

                System.out.print( symbol + "|");
            }
            System.out.println();
            System.out.println( " |-----------------------|");
        }
    }

    public Piece[] getPieces(){
        return this.pieces;
    }

    public void printPieces(){
        for ( Piece piece : this.pieces ) {
            System.out.println( piece.getPlayerName() + ": " + piece.getType() );
        }
    }

    public void printBoard(){
        System.out.println("Chessboard:");
        System.out.println( " | 1  2  3  4  5  6  7  8" );
        System.out.println( " |--+--+--+--+--+--+--+--|");

        int row_idx = 1;
        for ( Piece[] row : this.chessboard ){
            System.out.print( row_idx + "|" );
            for ( Piece piece : row ) {
                String symbol = "  ";
                if ( piece != null ){ symbol = piece.getImgPath(); }
                System.out.print( symbol + "|");
            }
            System.out.println();
            System.out.println( " |-----------------------|");
            row_idx++;
        }
    }
}
