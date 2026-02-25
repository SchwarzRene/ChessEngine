package Pieces;

public class Move {
    /*
    Storing the move of a player
    The move consists of a from and a to position
    Also a move contains the playerName
    */

    Position from;
    public Position to;
    String playerName;

    public Move(Position from, Position to, String playerName){
        //Set the vields of the move on the initialization of the Move
        this.from = from;
        this.to = to;
        this.playerName = playerName;
    }
}