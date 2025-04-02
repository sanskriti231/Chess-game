package pieces;

import java.util.List;

public abstract class Piece {
    private String color; // "white" or "black"
    private String type;  // e.g., "Bishop", "King", etc.

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

    /**
     * Abstract method to be implemented by each specific piece.
     * It calculates all valid moves for the piece based on its current position and the board state.
     *
     * @param currentPosition The current position of the piece (e.g., "e4").
     * @param board The 2D array representing the chessboard.
     * @return A list of valid moves in algebraic notation (e.g., "d5", "f6").
     */
    public abstract List<String> getValidMoves(String currentPosition, Piece[][] board);

    @Override
    public String toString() {
        return color + " " + type;
    }
}