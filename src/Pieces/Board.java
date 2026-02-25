package Pieces;

//The board class stores the chessboard and the pieces on it
public class Board {
    //The board is a 2D array of pieces
    private Piece[][] chessboard;
    //this.pieces stores all pieces on the board
    private Piece[] pieces;

    private Piece lastMovedPiece;

    //Initialize the board with a given size
    public Board( int rows, int cols ){
        //Initialize the board with null values
        this.chessboard = new Piece[rows][cols];
        this.pieces = new Piece[0];
    }

    //Generates a copy of the board with all its values
    //The Pieces are copied by reference and the board is copied by value
    //So when a piece is changed in the board, it is also changed in the copy
    //But when a piece is removed from the board, it is not removed from the copy
    public Board copy(){
        //Generate a new board with the same size and manually copy all the values
        Board board_copy = new Board( this.chessboard.length, this.chessboard[0].length );
        for ( int row_idx = 0; row_idx < this.chessboard.length; row_idx++ ){
            for ( int col_idx = 0; col_idx < this.chessboard[0].length; col_idx++ ){
                board_copy.chessboard[row_idx][col_idx] = this.chessboard[row_idx][col_idx];
            }
        }

        //Copy the pieces but generate a clone of the pieces and not the actual pieces
        board_copy.pieces = new Piece[this.pieces.length];
        System.arraycopy( this.pieces, 0, board_copy.pieces, 0, this.pieces.length );
        return board_copy;
    }

    public Piece[][] getBoard(){
        return this.chessboard;
    }

    //Removing a piece from the board
    public boolean removePiece( int row, int col ){
        //Check if the position is valid
        Piece piece = this.chessboard[row][col];
        if ( piece == null ){ return false; }

        //Remove the piece from the board
        this.chessboard[row][col] = null;

        //Remove the piece from the pieces array
        Piece[] pieces_new = new Piece[this.pieces.length - 1];
        int idx = 0;
        for (Piece value : this.pieces) {
            if ( !value.getType().equals( piece.getType() ) ) {
                pieces_new[idx] = value;
                idx++;
            }
        }
        this.pieces = pieces_new;
        return true;

    }

    //Setting a piece to its initial position
    public String setPiece( Piece piece ){
        //Setting a piece to a specific position
        //Each piece gets a position when being initialized, this position is used when setting the piece onto the board
        //When setting a piece, check if the position is empty and set the piece on the board

        int x = piece.get_pos().get_row();
        int y = piece.get_pos().get_col();

        //Check if the position is empty
        if ( this.chessboard[x][y] == null ){
            //Add piece to the pieces array
            Piece[] pieces_new = new Piece[ this.pieces.length + 1];
            System.arraycopy( this.pieces, 0, pieces_new, 0, this.pieces.length );
            pieces_new[ this.pieces.length ] = piece;
            this.pieces = pieces_new;

            //Store the piece on the board array
            this.chessboard[piece.get_pos().get_row()][piece.get_pos().get_col()] = piece;
            return "Successfully set";
        }

        return "Position already filled";
    }

    //Check if a position is inside the boards lengths, returns true if else false
    public boolean valid_position( Position pos ){
        if ( pos.get_col() >= 0 && pos.get_col() < this.chessboard.length ){
            return pos.get_row() >= 0 && pos.get_row() < this.chessboard[0].length;
        }
        return false;
    }

    public boolean empty_position( Position pos ){
        return this.chessboard[pos.get_row()][pos.get_col()] == null;
    }

    //Moves a piece from one position to another with the Move class
    public String movePiece( Move move ){
        //Moves a piece from its original position to a new position
        int fromRow = move.from.get_row();
        int fromCol = move.from.get_col();
        int toRow = move.to.get_row();
        int toCol = move.to.get_col();


        //Check if the move is in the board
        if ( !valid_position( move.from ) || !valid_position( move.to ) ){ return "Invalid move"; }

        //Check if the move has a piece at its starting position
        if ( this.chessboard[fromRow][fromCol] == null ){ return "Invalid move"; }

        //Check if the piece is from the player
        if ( !this.chessboard[fromRow][fromCol].getPlayerName().equals( move.playerName ) ){ return "Invalid move"; }

        //Check if the position to move to is empty or belongs to the other player
        if ( this.chessboard[toRow][toCol] != null ){
            Piece to_piece = this.chessboard[toRow][toCol];
            if ( to_piece.getPlayerName().equals( move.playerName ) ){ return "Invalid move"; }
        }

        //Check if a piece is on the position
        if ( this.chessboard[toRow][toCol] != null ){
            Piece to_piece = this.chessboard[toRow][toCol];

            Piece[] pieces_new = new Piece[ this.pieces.length - 1];
            int idx = 0;
            for ( Piece p : this.pieces ){
                if ( !( to_piece.get_pos().get_col() == p.get_pos().get_col() && to_piece.get_pos().get_row() == p.get_pos().get_row() ) ){
                    if ( ( idx >= pieces_new.length ) ){
                        throw new ArrayIndexOutOfBoundsException();
                    }
                    pieces_new[idx] = p;
                    idx++;
                }
            }
            this.pieces = pieces_new;
        }

        //Set the last moved piece to the piece that was moved
        this.lastMovedPiece = this.chessboard[fromRow][fromCol];

        this.chessboard[toRow][toCol] = this.chessboard[fromRow][fromCol];
        this.chessboard[fromRow][fromCol] = null;

        this.chessboard[toRow][toCol].set_pos( new Position( toRow, toCol ) );
        return "Valid move";
    }

    //Returns the piece of a position
    public Piece getPiece( int row, int col ){
        return this.chessboard[row][col];
    }

    //Get the maximum number of rows of the board
    public int get_max_row(){
        return this.chessboard.length;
    }

    //Get the maximum number of columns of the board
    public int get_max_col(){
        return this.chessboard[0].length;
    }

    //Prints the possible moves of a piece
    public void printMoves( Position[] moves ){
        //Iterate over all moves in the positions array and prints a X if it is a valid position
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

    public Piece getLastMovedPiece() {
        return lastMovedPiece;
    }

    public Position getLastMovedPiecePos() {
        return lastMovedPiece.get_pos();
    }

    //Get the pieces on the board
    public Piece[] getPieces(){
        return this.pieces;
    }

    //Prints the pieces on the board
    public void printPieces(){
        for ( Piece piece : this.pieces ) {
            System.out.println( piece.getPlayerName() + ": " + piece.getType() );
        }
    }

    //Prints the chessboard
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
