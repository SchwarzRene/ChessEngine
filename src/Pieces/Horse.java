package Pieces;

public class Horse extends Piece{
    public Horse( String name, String playerName, String imgPath, Position pos ){
        super( name, playerName, imgPath, pos );
    }

    @Override
    public Position[] valid_moves( Board b ){
        //A Horse can move any number of square which add to 3
        //So it can move one up and two to the right, or two up and one to the right
        //   1 2 3 4 5 6 7 8
        // 1    
        // 2   h
        // 3       .
        // 4       
        // 5         
        // 6
        // 7
        // 8
        //Does the Horse reach a figure of the same team then it can't move further
        //Does the Horse reach a figure of the other team then it can take that figure

        Position currentPos = this.get_pos();

        int x = currentPos.get_row();
        int y = currentPos.get_col();

        Position[] valid_moves = new Position[0];

        for ( int i = -2; i < 3; i++ ){
            for ( int j = -2; j < 3; j++ ){
                if ( i + j == 3 || -i + j == 3 || -i - j == 3 || i - j == 3 ){
                    valid_moves = this.check_position( b, x + i, y + j, valid_moves );
                }
            }
        }
        return valid_moves;
    }

    @Override
    public String getPlayerName(){
        return this.playerName;
    }
}
