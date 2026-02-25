package Pieces;

public class Position {
    private final int row;
    private final int col;

    public Position( int row, int col ){
        this.row = row;
        this.col = col;
    }

    public int get_row(){
        return this.row;
    }

    public int get_col(){
        return this.col;
    }
}
