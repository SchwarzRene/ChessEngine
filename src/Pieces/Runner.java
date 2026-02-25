package Pieces;

public class Runner extends Piece{
    public Runner( String name, String playerName, String imgPath, Position pos ){
        super( name, playerName, imgPath, pos );
    }

    @Override
    public Position[] valid_moves( Board b ){
        //A runner can move any number of square diagonally
        //   1 2 3 4 5 6 7 8
        // 1 .   .
        // 2   r
        // 3 .   .
        // 4       .
        // 5         t
        // 6
        // 7
        // 8
        //Does the runner reach a figure of the same team then it can't move further
        //Does the runner reach a figure of the other team then it can take that figure

        Position currentPos = this.get_pos();

        int x = currentPos.get_row();
        int y = currentPos.get_col();

        Position[] valid_moves = new Position[0];
        //Check going to the right bottom
        while ( x < b.get_max_col() - 1 && y < b.get_max_row() - 1 ){
            y++;
            x++;

            valid_moves = this.check_position( b, x, y, valid_moves );

            if ( b.getPiece(x,y) != null ){
                break;
            }
        }

        x = currentPos.get_row(); y = currentPos.get_col();
        //Check going to the left bottom
        while ( x > 0 && y < b.get_max_row() - 1 ){
            x--;
            y++;
            valid_moves = this.check_position( b, x, y, valid_moves );

            if ( b.getPiece(x,y) != null ){
                break;
            }

        }

        x = currentPos.get_row(); y = currentPos.get_col();
        //Check going to the right top
        while ( x < b.get_max_col() - 1 && y > 0 ){
            y--;
            x++;
            valid_moves = this.check_position( b, x, y, valid_moves );

            if ( b.getPiece(x,y) != null ){
                break;
            }
        }

        x = currentPos.get_row(); y = currentPos.get_col();
        //Check going to the left top
        while ( x > 0 && y > 0 ){
            x--;
            y--;
            valid_moves = this.check_position( b, x, y, valid_moves );

            if ( b.getPiece(x,y) != null ){
                break;
            }
        }
        return valid_moves;
    }
}
