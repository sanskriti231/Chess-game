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
    // Place white pieces
    board[0][0] = new Rook("white");
    board[0][1] = new Knight("white");
    board[0][2] = new Bishop("white");
    board[0][3] = new Queen("white");
    board[0][4] = new King("white");
    board[0][5] = new Bishop("white");
    board[0][6] = new Knight("white");
    board[0][7] = new Rook("white");
    for (int i = 0; i < 8; i++) {
        board[1][i] = new Pawn("white");
    }

    // Place black pieces
    board[7][0] = new Rook("black");
    board[7][1] = new Knight("black");
    board[7][2] = new Bishop("black");
    board[7][3] = new Queen("black");
    board[7][4] = new King("black");
    board[7][5] = new Bishop("black");
    board[7][6] = new Knight("black");
    board[7][7] = new Rook("black");
    for (int i = 0; i < 8; i++) {
        board[6][i] = new Pawn("black");
    }
}

    public Piece getPieceAt(String position) {
        int row = position.charAt(1) - '1';
        int col = position.charAt(0) - 'a';
        return board[row][col];
    }

    public void movePiece(String from, String to) {
    int fromRow = 8 - Character.getNumericValue(from.charAt(1));
    int fromCol = from.charAt(0) - 'a';
    int toRow = 8 - Character.getNumericValue(to.charAt(1));
    int toCol = to.charAt(0) - 'a';

    // Move the piece
    board[toRow][toCol] = board[fromRow][fromCol];
    board[fromRow][fromCol] = null;
}
    public Piece[][] getBoard() {
        return board;
    }
}