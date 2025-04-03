package pieces;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(String color) {
        super(color, "Rook");
    }

    @Override
    public List<String> getValidMoves(String currentPosition, Piece[][] board) {
        List<String> validMoves = new ArrayList<>();
        int row = currentPosition.charAt(1) - '1';
        int col = currentPosition.charAt(0) - 'a';

        // Horizontal and vertical directions
        int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

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
