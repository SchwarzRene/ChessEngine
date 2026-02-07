package Pieces;

public class Tower extends Piece{
    public Tower( String name, String playerName, String imgPath, Position pos ){
        super( name, playerName, imgPath, pos );
    }



    @Override
    public Position[] valid_moves( Board b ){
        //A Tower can move any number of square vertically or horizontally
        //   1 2 3 4 5 6 7 8
        // 1
        // 2     r
        // 3     .
        // 4     .
        // 5     .
        // 6     .
        // 7 . . t . . . . .
        // 8     .
        //Does the Tower reach a figure of the same team then it can't move further
        //Does the Tower reach a figure of the other team then it can take that figure

        Position currentPos = this.get_pos();

        int x = currentPos.get_row();
        int y = currentPos.get_col();

        Position[] valid_moves = new Position[0];
        //Check going to the right bottom
        while ( x < b.get_max_col() - 1 ){
            x++;

            valid_moves = check_position( b, x, y, valid_moves );

            if ( b.getPiece(x,y) != null ){
                break;
            }
        }

        x = currentPos.get_row();
        //Check going to the left bottom
        while ( x > 0 ){
            x--;
            valid_moves = check_position( b, x, y, valid_moves );

            if ( b.getPiece(x,y) != null ){
                break;
            }
        }

        x = currentPos.get_row();
        //Check going to the right top
        while ( y < b.get_max_row() - 1 ){
            y++;
            valid_moves = check_position( b, x, y, valid_moves );

            if ( b.getPiece(x,y) != null ){
                break;
            }
        }

        y = currentPos.get_col();
        //Check going to the left top
        while ( y > 0 ){
            y--;
            valid_moves = check_position( b, x, y, valid_moves );

            if ( b.getPiece(x,y) != null ){
                break;
            }
        }
        return valid_moves;
    }

    @Override
    public String getPlayerName(){
        return this.playerName;
    }
}
