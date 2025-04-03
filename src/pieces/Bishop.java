package pieces;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

    public Bishop(String color) {
        super(color, "Bishop");
    }

    @Override
    public List<String> getValidMoves(String currentPosition, Piece[][] board) {
        List<String> validMoves = new ArrayList<>();
        int[] directions = { -1, 1 }; // Diagonal directions

        int row = currentPosition.charAt(1) - '1';
        int col = currentPosition.charAt(0) - 'a';

        for (int dr : directions) {
            for (int dc : directions) {
                int r = row + dr;
                int c = col + dc;

                while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                    if (board[r][c] == null) {
                        validMoves.add("" + (char) (c + 'a') + (r + 1));
                    } else if (
                        !board[r][c].getColor().equals(this.getColor())
                    ) {
                        validMoves.add("" + (char) (c + 'a') + (r + 1));
                        break;
                    } else {
                        break;
                    }
                    r += dr;
                    c += dc;
                }
            }
        }

        return validMoves;
    }
}
