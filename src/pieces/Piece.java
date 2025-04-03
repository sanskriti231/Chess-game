package pieces;

import java.util.List;
import javafx.util.Pair;

public abstract class Piece {

    private String color; // white or blac
    private String type; //

    public Piece(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public String getType() {
        return type;
    }

    public abstract List<Pair<Integer, Integer>> getValidMoves(
        int row,
        int col,
        Piece[][] board
    );

    @Override
    public String toString() {
        return color + " " + type;
    }
}
