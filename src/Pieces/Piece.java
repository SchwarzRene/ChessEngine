package Pieces;

public abstract class Piece {
    private Position pos;
    final String imgPath;
    final String name;
    final String playerName;
    public Position startPos;
    public Position lastPos;

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
    }

    public String getImgPath(){
        return this.imgPath;
    }

    public String getType(){
        return this.name;
    }

    public Position[] check_position( Board b, int row, int col, Position[] valid_moves ){
        //Check if the position is valid

        //Check if the position is inside the board
        if ( row < 0 || row >= b.get_max_row() || col < 0 || col >= b.get_max_col() ){ return valid_moves; }

        //Check if the position is empty
        if ( b.getPiece(row,col) != null ){
            //If it is not empty check if it is on the same team
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

    public abstract String getPlayerName();
    public abstract Position[] valid_moves(Board b);
}