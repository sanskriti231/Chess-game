package pieces;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(String color) {
        super(color, "King");
    }

    @Override
    public List<String> getValidMoves(String currentPosition, Piece[][] board) {
        List<String> validMoves = new ArrayList<>();
        int row = currentPosition.charAt(1) - '1';
        int col = currentPosition.charAt(0) - 'a';

        // All possible directions for the king
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] direction : directions) {
            int r = row + direction[0];
            int c = col + direction[1];

            if (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (board[r][c] == null || !board[r][c].getColor().equals(this.getColor())) {
                    validMoves.add("" + (char) (c + 'a') + (r + 1));
                }
            }
        }

        return validMoves;
    }
}