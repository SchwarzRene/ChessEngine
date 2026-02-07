package Pieces;

public class Queen extends Piece{
    private Tower queen_tower;
    private Runner queen_runner;

    public Queen( String name, String playerName, String imgPath, Position pos ){
        //A Queen can move like a Tower and a Runner
        //So the queen class is a combination of a Tower and a Runner and inherits from the pieces
        super( name, playerName, imgPath, pos );
        queen_tower = new Tower( "Queen_Tower", playerName, imgPath, pos );
        queen_runner = new Runner( "Queen_Runner", playerName, imgPath, pos );
    }

    @Override
    public Position[] valid_moves( Board b ) {
        queen_tower.set_pos( this.get_pos() );
        queen_runner.set_pos( this.get_pos() );

        Position[] tower_valid_moves = queen_tower.valid_moves( b );
        Position[] runner_valid_moves = queen_runner.valid_moves( b );

        Position[] valid_moves = new Position[tower_valid_moves.length + runner_valid_moves.length];
        System.arraycopy( tower_valid_moves, 0, valid_moves, 0, tower_valid_moves.length );
        System.arraycopy( runner_valid_moves, 0, valid_moves, tower_valid_moves.length, runner_valid_moves.length );

        return valid_moves;
    }

    @Override
    public String getPlayerName(){
        return this.playerName;
    }
}
