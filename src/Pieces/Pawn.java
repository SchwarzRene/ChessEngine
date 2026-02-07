package Pieces;

public class Pawn extends Piece{
    final int direction;

    public Pawn( String name, String playerName, String imgPath, Position pos, int direction ){
        //Direction can be one for upwards and -1 for downwards
        super( name, playerName, imgPath, pos );

        if ( direction != 1 && direction != -1 ){ throw new IllegalArgumentException("Direction must be 1 or -1"); }
        this.direction = direction;
    }

    @Override
    public Position[] valid_moves( Board b ) {
        Position currentPos = this.get_pos();

        int x = currentPos.get_row();
        int y = currentPos.get_col();

        //Check going to the right bottom
        int leftEdge = x - 1;
        int rightEdge = x + 1;

        Position[] valid_moves = new Position[0];
        if ( y + direction < b.get_max_row() && y + direction >= 0 ){
            //Check if there is a piece in front of the pawn
            if ( b.getPiece(x,y + direction) == null ){
                valid_moves = this.check_position( b, x, y + direction, valid_moves );
            }

            //Check if there is a piece in front of the pawn that is on the other team
            //This is the left side
            if ( leftEdge >= 0 ){
                if ( b.getPiece(leftEdge,y + direction) !=  null && !b.getPiece(leftEdge,y + direction).getPlayerName().equals(this.playerName) ){
                    valid_moves = this.check_position( b, leftEdge, y + direction, valid_moves );
                }
            }

            //This is the right side
            if ( rightEdge < b.get_max_col( )){
                if ( b.getPiece(rightEdge,y + direction) !=  null && !b.getPiece(rightEdge,y + direction).getPlayerName().equals(this.playerName) ){
                    valid_moves = this.check_position( b, rightEdge, y + direction, valid_moves );
                }
            }

        }

        //When the pawn is in the start position it can move two squares forward
        if ( x == startPos.get_row() && y == startPos.get_col() ){
            valid_moves = this.check_position( b, x, y + direction * 2, valid_moves );
        }

        return valid_moves;
    }

    @Override
    public String getPlayerName(){
        return this.playerName;
    }
}
