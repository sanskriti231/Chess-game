package pieces;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(String color) {
        super(color, "Pawn");
    }

    @Override
    public List<String> getValidMoves(String currentPosition, Piece[][] board) {
        List<String> validMoves = new ArrayList<>();
        int row = currentPosition.charAt(1) - '1';
        int col = currentPosition.charAt(0) - 'a';

        int direction = this.getColor().equals("white") ? 1 : -1; // White moves up, black moves down

        // Move forward
        if (
            row + direction >= 0 &&
            row + direction < 8 &&
            board[row + direction][col] == null
        ) {
            validMoves.add("" + (char) (col + 'a') + (row + direction + 1));

            // First move can go two squares
            if (
                (this.getColor().equals("white") && row == 1) ||
                (this.getColor().equals("black") && row == 6)
            ) {
                if (board[row + 2 * direction][col] == null) {
                    validMoves.add(
                        "" + (char) (col + 'a') + (row + 2 * direction + 1)
                    );
                }
            }
        }

        // Capture diagonally
        for (int dc : new int[] { -1, 1 }) {
            int newCol = col + dc;
            if (
                newCol >= 0 &&
                newCol < 8 &&
                row + direction >= 0 &&
                row + direction < 8
            ) {
                Piece target = board[row + direction][newCol];
                if (
                    target != null && !target.getColor().equals(this.getColor())
                ) {
                    validMoves.add(
                        "" + (char) (newCol + 'a') + (row + direction + 1)
                    );
                }
            }
        }

        return validMoves;
    }
}
