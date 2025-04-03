
package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Bishop extends Piece {

    public Bishop(String color) {
        super(color, "Bishop");
    }

    @Override
    public List<Pair<Integer, Integer>> getValidMoves(int row, int col, Piece[][] board) {
        List<Pair<Integer, Integer>> validMoves = new ArrayList<>();
        // All possible directions for the king
        int[][] directions = {
                { 1, 1 },   // Down-Right
                { 1, -1 },  // Down-Left
                { -1, 1 },  // Up-Right
                { -1, -1 }, // Up-Left
        };

        for (int[] direction : directions) {
            int newRow = row;
            int newCol = col;

            while (true) {
                newRow += direction[0];
                newCol += direction[1];

                if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
                    break;
                }

                Piece targetPiece = board[newRow][newCol];
                // Check if the target square is empty or occupied by an opponents piece
                if (targetPiece == null) {
                    validMoves.add(new Pair<>(newRow, newCol));
                } else {
                    if (!targetPiece.getColor().equals(this.getColor())) {
                        validMoves.add(new Pair<>(newRow, newCol)); // Capture
                    }
                    break; // Stop at the first piece encountered
                }
            }
        }
        return validMoves;
    }
}