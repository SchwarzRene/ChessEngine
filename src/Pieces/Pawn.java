package Pieces;

//Class of the Pawn piece
public class Pawn extends Piece{
    final int direction;

    public Pawn( String name, String playerName, String imgPath, Position pos, int direction ){
        //Direction can be one for upwards and -1 for downwards
        //So 1 when moving from 0 to 8 and -1 when moving from 8 to 1
        super( name, playerName, imgPath, pos );

        if ( direction != 1 && direction != -1 ){ throw new IllegalArgumentException("Direction must be 1 or -1"); }
        this.direction = direction;
    }

    @Override
    public Position[] valid_moves( Board b ) {
        //Checks if a position is empty
        //Also checks if the piece is from the same player
        //If a piece is not from the same player add it to the valid moves


        Position currentPos = this.get_pos();

        //Get the current position
        int row = currentPos.get_row();
        int col = currentPos.get_col();

        int new_row = row + direction;
        
        //Get left and right edge of the pawn
        int left_col = col - 1;
        int right_col = col + 1;

        Position[] valid_moves = new Position[0];
        if ( new_row < b.get_max_row() && new_row >= 0 ){
            //Check if there is a piece in front of the pawn
            //A pawn can only move forward when there is no piece so it can not replace a enemy piece directly
            if ( b.getPiece( new_row, col ) == null ){
                valid_moves = this.check_position( b, new_row, col, valid_moves );
            }


            //Check if there is a piece on the left side in front of the pawn

            //Check if the left side is outside the board
            if ( left_col >= 0 ){
                //Check if there is a piece on the left side
                if ( b.getPiece(new_row, left_col) != null ){

                    //If there is a piece on the left side check if it is a valid move
                    valid_moves = this.check_position( b, new_row, left_col, valid_moves );
                }
            }

            //Check if there is a piece on the right side in front of the pawn

            //Check if the right side is outside the board
            if ( right_col < b.get_max_col( )){
                //Check if there is a piece on the right side
                if ( b.getPiece(new_row, right_col) != null ){
                    valid_moves = this.check_position( b, new_row, right_col, valid_moves );
                }
            }
        }

        //When the pawn is in the start position it can move two squares forward
        //Check if the pawn is still in the start position
        if ( row == startPos.get_row() && col == startPos.get_col() ){
            valid_moves = this.check_position( b, row + direction * 2, col, valid_moves );
        }

        //Check for en passant

        //Check if the place left next to a pawn is empty
        valid_moves = check_en_passant(b, row, left_col, valid_moves);
        valid_moves = check_en_passant(b, row, right_col, valid_moves);


        return valid_moves;
    }

    private Position[] check_en_passant(Board b, int row, int column, Position[] valid_moves) {
        Piece side_piece = b.getPiece( row, column );
        if ( side_piece != null ){
            //Check if the piece is from the enemy
            if ( !side_piece.getPlayerName().equals( this.getPlayerName() ) ){
                //Check if the piece is a pawn
                if ( side_piece.getType().toLowerCase().contains( "pawn" ) ){

                    //Check if the last moved piece was that pawn
                    if ( b.getLastMovedPiece().getType().toLowerCase().contains( "pawn" ) ){
                        if ( b.getLastMovedPiece().get_pos().get_row() == row && b.getLastMovedPiece().get_pos().get_col() == column ){
                            valid_moves = this.check_position( b, row + direction, column, valid_moves );
                        }
                    }
                }
            }
        }
        return valid_moves;
    }
}
