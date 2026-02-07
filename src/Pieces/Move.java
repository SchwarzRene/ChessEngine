package Pieces;

public class Move {
    Position from;
    Position to;
    String playerName;

    public Move(Position from, Position to, String playerName){
        this.from = from;
        this.to = to;
        this.playerName = playerName;
    }
}