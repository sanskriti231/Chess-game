package chess;

import pieces.Piece;
import pieces.Rook;
import pieces.Knight;
import pieces.Bishop;
import pieces.Queen;
import pieces.King;
import pieces.Pawn;

public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        board[0][0] = new Rook("black");
        board[0][1] = new Knight("black");
        board[0][2] = new Bishop("black");
        board[0][3] = new Queen("black");
        board[0][4] = new King("black");
        board[0][5] = new Bishop("black");
        board[0][6] = new Knight("black");
        board[0][7] = new Rook("black");
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("black");
        }

        board[7][0] = new Rook("white");
        board[7][1] = new Knight("white");
        board[7][2] = new Bishop("white");
        board[7][3] = new Queen("white");
        board[7][4] = new King("white");
        board[7][5] = new Bishop("white");
        board[7][6] = new Knight("white");
        board[7][7] = new Rook("white");
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn("white");
        }

    }

    public Piece getPieceAt(String position) {
        int rank = Integer.parseInt(position.substring(1, 2));
        int row = 8 - rank;
        int col = position.charAt(0) - 'a';
        return board[row][col];
    }

    public void movePiece(String from, String to) {
        int fromRank = Integer.parseInt(from.substring(1, 2));
        int fromRow = 8 - fromRank;
        int fromCol = from.charAt(0) - 'a';

        int toRank = Integer.parseInt(to.substring(1, 2));
        int toRow = 8 - toRank;
        int toCol = to.charAt(0) - 'a';

        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = null;
    }

    public Piece[][] getBoard() {
        return board;
    }
}