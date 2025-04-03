
package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Knight extends Piece {

    public Knight(String color) {
        super(color, "Knight");
    }

    @Override
    public List<Pair<Integer, Integer>> getValidMoves(int row, int col, Piece[][] board) {
        List<Pair<Integer, Integer>> validMoves = new ArrayList<>();
        int[][] directions = {
                { 2, 1 },   // Right 2, Down 1
                { 2, -1 },  // Right 2, Up 1
                { -2, 1 },  // Left 2, Down 1
                { -2, -1 }, // Left 2, Up 1
                { 1, 2 },   // Down 2, Right 1
                { -1, 2 },  // Up 2, Right 1
                { 1, -2 },  // Down 2, Left 1
                { -1, -2 }   // Up 2, Left 1
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Piece targetPiece = board[newRow][newCol];
                if (targetPiece == null || !targetPiece.getColor().equals(this.getColor())) {
                    validMoves.add(new Pair<>(newRow, newCol));
                }
            }
        }
        return validMoves;
    }
}
