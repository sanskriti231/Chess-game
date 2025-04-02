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
        char file = currentPosition.charAt(0);
        int rank = Integer.parseInt(currentPosition.substring(1));
        
        int direction = this.getColor().equals("white") ? -1 : 1; // White moves up (ranks decrease), black down
        int currentRow = 8 - rank;
        int currentCol = file - 'a';

        // Forward moves
        int nextRank = rank + direction;
        if (isValidSquare(file, nextRank) && board[8 - nextRank][currentCol] == null) {
            validMoves.add("" + file + nextRank);
            
            // Initial two-square move
            if ((rank == 2 && this.getColor().equals("black")) || 
                (rank == 7 && this.getColor().equals("white"))) {
                int doubleRank = rank + (2 * direction);
                if (isValidSquare(file, doubleRank) && board[8 - doubleRank][currentCol] == null) {
                    validMoves.add("" + file + doubleRank);
                }
            }
        }

        // Captures
        for (int side : new int[]{-1, 1}) {
            char captureFile = (char) (file + side);
            int captureRank = rank + direction;
            if (captureFile >= 'a' && captureFile <= 'h' && isValidSquare(captureFile, captureRank)) {
                int targetRow = 8 - captureRank;
                int targetCol = captureFile - 'a';
                Piece targetPiece = board[targetRow][targetCol];
                if (targetPiece != null && !targetPiece.getColor().equals(this.getColor())) {
                    validMoves.add("" + captureFile + captureRank);
                }
            }
        }

        return validMoves;
    }

    private boolean isValidSquare(char file, int rank) {
        return file >= 'a' && file <= 'h' && rank >= 1 && rank <= 8;
    }
}