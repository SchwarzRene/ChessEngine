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

    public Position[] check_position( Board b, int x, int y, Position[] valid_moves ){
        if ( x < 0 || x >= b.get_max_row() || y < 0 || y >= b.get_max_col() ){ return valid_moves; }

        if ( b.getPiece(x,y) != null ){
            if ( b.getPiece(x,y).getPlayerName().equals( this.playerName) ){ return valid_moves; }
        }

        Position p = new Position( x, y );
        Position[] new_valid_moves = new Position[valid_moves.length+1];
        new_valid_moves[valid_moves.length] = p;
        System.arraycopy( valid_moves, 0, new_valid_moves, 0, valid_moves.length );
        valid_moves = new_valid_moves;

        //Check if the piece is on the other team
        if ( b.getPiece(x,y) != null ) {
            if (!b.getPiece(x, y).getPlayerName().equals(this.playerName)) {
                return valid_moves;
            }
        }
        return valid_moves;
    }

    public abstract String getPlayerName();
    public abstract Position[] valid_moves(Board b);
}