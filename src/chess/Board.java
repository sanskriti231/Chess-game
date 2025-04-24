package chess;


import pieces.Piece;
import pieces.Rook;
import pieces.Knight;
import pieces.Bishop;
import pieces.Queen;
import pieces.King;
import pieces.Pawn;
import javafx.util.Pair;
import java.util.List;
import java.util.ArrayList;

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

    public void setPieceAt(int x, int y, Piece piece) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Invalid board position");
        }
        board[x][y] = piece;
    }

    public Piece getPieceAt(int x, int y) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Invalid board position");
        }
        return board[x][y];
    }

    public void movePiece(int fromX, int fromY, int toX, int toY) {
        Piece piece = board[fromX][fromY];
        board[toX][toY] = piece;
        board[fromX][fromY] = null;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public Pair<Integer, Integer> getKingPosition(String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null; // King not found
    }

    public List<Pair<Integer, Integer>> getPossibleMoves(int x, int y) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            throw new IllegalArgumentException("Invalid board position");
        }

        Piece piece = board[x][y];
        if (piece == null) {
            return new ArrayList<>(); // No piece at the given position
        }

        return piece.getValidMoves(x, y, board);
    }
}