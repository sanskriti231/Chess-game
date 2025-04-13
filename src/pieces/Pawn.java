
package pieces;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Pawn extends Piece {

    public Pawn(String color) {
        super(color, "pawn");
    }

    @Override
    public List<Pair<Integer, Integer>> getValidMoves(int row, int col, Piece[][] board) {
        List<Pair<Integer, Integer>> validMoves = new ArrayList<>();
        int[][] directions = {
            {1, 0}, // Move forward one square
            {2, 0}, // Move forward two squares (only from starting position)
            {1, -1}, // Capture diagonally left
            {1, 1},  // Capture diagonally right
            {-1, 0},
            {-2, 0}, // Move backward one square
            {-1, -1}, // Capture diagonally left backward
            {-1, 1}   // Capture diagonally right backward
        };

        for (int[] direction: directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Piece targetPiece = board[newRow][newCol];
                if(this.getColor() == "black") {
                if (direction[0] == 1 && direction[1] == 0) { // Move forward one square
                    if (targetPiece == null) {
                        validMoves.add(new Pair<>(newRow, newCol));
                    }
                } else if (direction[0] == 2 && direction[1] == 0) { // Move forward two squares
                    if (row == 1 && targetPiece == null && board[row + 1][col] == null) {
                        validMoves.add(new Pair<>(newRow, newCol));
                    }
                } else if (direction[0] == 1 && Math.abs(direction[1]) == 1) { // Capture diagonally
                    if (targetPiece != null && !targetPiece.getColor().equals(this.getColor())) {
                        validMoves.add(new Pair<>(newRow, newCol));
                    }
                }
            }
            else if(this.getColor() == "white") {
                if (direction[0] == -1 && direction[1] == 0) { // Move forward one square
                    if (targetPiece == null) {
                        validMoves.add(new Pair<>(newRow, newCol));
                    }
                } else if (direction[0] == -2 && direction[1] == 0) { // Move forward two squares
                    if (row == 6 && targetPiece == null && board[row - 1][col] == null) {
                        validMoves.add(new Pair<>(newRow, newCol));
                    }
                } else if (direction[0] == -1 && Math.abs(direction[1]) == 1) { // Capture diagonally
                    if (targetPiece != null && !targetPiece.getColor().equals(this.getColor())) {
                        validMoves.add(new Pair<>(newRow, newCol));
                    }
                }
            }
            }
        }
        
        return validMoves;
    }
}
