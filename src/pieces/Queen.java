package pieces;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(String color) {
        super(color, "Queen");
    }

    @Override
    public List<String> getValidMoves(String currentPosition, Piece[][] board) {
        List<String> validMoves = new ArrayList<>();
        int row = currentPosition.charAt(1) - '1';
        int col = currentPosition.charAt(0) - 'a';

        // Directions for rook and bishop combined
        int[][] directions = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Rook-like moves
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Bishop-like moves
        };

        for (int[] direction : directions) {
            int r = row + direction[0];
            int c = col + direction[1];

            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (board[r][c] == null) {
                    validMoves.add("" + (char) (c + 'a') + (r + 1));
                } else if (!board[r][c].getColor().equals(this.getColor())) {
                    validMoves.add("" + (char) (c + 'a') + (r + 1));
                    break;
                } else {
                    break;
                }
                r += direction[0];
                c += direction[1];
            }
        }

        return validMoves;
    }
}