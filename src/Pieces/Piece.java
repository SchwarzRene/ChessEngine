package Pieces;

/*
Main class for all pieces
 Piece
 --> Piece( String name, String playerName, String imgPath, Position pos )

 --> Position get_pos() [Return the current position of the piece]
 --> void set_pos( Position pos ) [Set the new position of the piece]

 --> String getType() [Return the type of the piece]
 --> String getImgPath() [Return the path to the image of the piece]
 --> String getPlayerName() [Return the name of the player]

 --> boolean hasMoved() [Return true if the piece has moved]

 --> Position[] valid_moves( Board b ) [Return an array of all valid positions for the piece]
 */
public abstract class Piece {
    private Position pos;
    final String imgPath;
    final String name;
    final String playerName;
    public Position startPos;
    public Position lastPos;

    private boolean hasMoved = false;

    public Piece(String name, String playerName, String imgPath, Position pos) {
        this.pos = pos;
        this.imgPath = imgPath;
        this.name = name;
        this.playerName = playerName;

        this.startPos = pos;
        this.lastPos = pos;
    }

    public Position get_pos() {
        return this.pos;
    }

    public void set_pos(Position pos) {
        this.lastPos = this.pos;
        this.pos = pos;
        hasMoved = true;
    }

    public boolean hasMoved(){
        return this.hasMoved;
    }

    public String getImgPath(){
        return this.imgPath;
    }

    public String getType(){
        return this.name;
    }

    public Position[] check_position( Board b, int row, int col, Position[] valid_moves ){
        /*
        Check if the position is valid, so if a piece does not replace pieces from its own player
        Therefore check if a position is inside the board
        Then check if the piece at the position (row|col) is empty or on the same team
        If it is empty or on the same team add it to the valid moves array
         */

        Position position = new Position( row, col );
        //Check if the position is inside the board
        if ( b.valid_position( position ) ){ return valid_moves; }

        //Check if the position is empty
        //If it is not empty check if it is on the same team
        if ( b.empty_position( position ) ){
            if ( b.getPiece(row,col).getPlayerName().equals( this.playerName) ){ return valid_moves; }
        }

        //If empty or enemy piece add it to the valid moves
        Position p = new Position( row, col );
        Position[] new_valid_moves = new Position[valid_moves.length+1];
        new_valid_moves[valid_moves.length] = p;
        System.arraycopy( valid_moves, 0, new_valid_moves, 0, valid_moves.length );
        valid_moves = new_valid_moves;

        return valid_moves;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public abstract Position[] valid_moves(Board b);
}