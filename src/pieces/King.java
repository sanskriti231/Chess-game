package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class King extends Piece {

    public King(String color) {
        super(color, "King");
    }

    @Override
    public List<Pair<Integer, Integer>> getValidMoves(int row, int col, Piece[][] board) {
        List<Pair<Integer, Integer>> validMoves = new ArrayList<>();
        // All possible directions for the king
        int[][] directions = {
                { 0, 1 },
                { 1, 1 },
                { 1, 0 },
                { 1, -1 },
                { 0, -1 },
                { -1, -1 },
                { -1, 0 },
                { -1, 1 },
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            // Check if the new position is within bounds
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Piece targetPiece = board[newRow][newCol];
                // Check if the target square is empty or occupied by an opponent's piece
                if (targetPiece == null || !targetPiece.getColor().equals(this.getColor())) {
                    validMoves.add(new Pair<>(newRow, newCol));
                }
            }
        }
        return validMoves;
    }
}