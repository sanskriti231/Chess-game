
package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Queen extends Piece {

    public Queen(String color) {
        super(color, "Queen");
    }

    @Override
    public List<Pair<Integer, Integer>> getValidMoves(int row, int col, Piece[][] board) {
        List<Pair<Integer, Integer>> validMoves = new ArrayList<>();
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
            int newRow = row;
            int newCol = col;

            while (true) {
                newRow += direction[0];
                newCol += direction[1];

                if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
                    break;
                }

                Piece targetPiece = board[newRow][newCol];
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