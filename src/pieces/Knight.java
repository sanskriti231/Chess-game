package pieces;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

    public Knight(String color) {
        super(color, "Knight");
    }

    @Override
    public List<String> getValidMoves(String currentPosition, Piece[][] board) {
        List<String> validMoves = new ArrayList<>();
        int row = currentPosition.charAt(1) - '1';
        int col = currentPosition.charAt(0) - 'a';

        // All possible L shaped moves
        int[][] moves = {
            { 2, 1 },
            { 2, -1 },
            { -2, 1 },
            { -2, -1 },
            { 1, 2 },
            { 1, -2 },
            { -1, 2 },
            { -1, -2 },
        };

        for (int[] move : moves) {
            int r = row + move[0];
            int c = col + move[1];

            if (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (
                    board[r][c] == null ||
                    !board[r][c].getColor().equals(this.getColor())
                ) {
                    validMoves.add("" + (char) (c + 'a') + (r + 1));
                }
            }
        }

        return validMoves;
    }
}
