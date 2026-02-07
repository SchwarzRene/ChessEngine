package Pieces;

public class King extends Piece{
    public King( String name, String playerName, String imgPath, Position pos ){
        super( name, playerName, imgPath, pos );
    }

    @Override
    public Position[] valid_moves( Board b ) {
        Position currentPos = this.get_pos();

        int x = currentPos.get_row();
        int y = currentPos.get_col();

        Position[] valid_moves = new Position[0];
        //Check going to the right bottom
        for ( int i = -1; i < 1; i++ ){
            for ( int j = -1; j < 1; j++ ){
                if ( i != 0 || j != 0 ){
                    valid_moves = this.check_position( b, x + i, y + j, valid_moves );
                }
            }
        }

        //Check if there is a piece in the way where the enemy could move
        for ( Piece p : b.getPieces() ){
            if (!( p.getPlayerName().equals( this.playerName ) ) && !p.getType().contains("King")){
                Position[] vm = p.valid_moves( b );

                Position[] new_valid_moves = new Position[0];
                for ( Position valid_move : valid_moves ){
                    for ( Position pos : vm ){
                        if ( pos.get_col() == valid_move.get_col() && pos.get_row() == valid_move.get_row() ){
                            continue;
                        }
                        Position[] new_new_valid_moves = new Position[new_valid_moves.length+1];
                        new_new_valid_moves[new_valid_moves.length] = valid_move;
                        System.arraycopy( new_valid_moves, 0, new_new_valid_moves, 0, new_valid_moves.length );
                        new_valid_moves = new_new_valid_moves;
                    }
                }
                valid_moves = new_valid_moves;
            }
        }

        return valid_moves;
    }

    @Override
    public String getPlayerName(){
        return this.playerName;
    }
}
