package chess;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pieces.Piece;
import javafx.util.Pair;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ChessGame extends Application {

    private static final int TILE_SIZE = 100;
    private static final int BOARD_SIZE = 8;
    private GridPane gridPane;
    private Board board;
    private Label turnLabel = new Label("Turn: White");
    private boolean isWhiteTurn = true;

    private Piece selectedPiece = null;
    private int selectedRow = -1;
    private int selectedCol = -1;

    @Override
    public void start(Stage primaryStage) {
        board = new Board(); // Initialize the board
        gridPane = new GridPane();

        renderBoard();

        BorderPane root = new BorderPane();
        root.setCenter(gridPane);

        turnLabel.setStyle("-fx-font-size: 18px; -fx-padding: 20px;");
        root.setRight(turnLabel);

        Scene scene = new Scene(
                root,
                TILE_SIZE * BOARD_SIZE + 150,
                TILE_SIZE * BOARD_SIZE);

        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void renderBoard() {
        gridPane.getChildren().clear();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                // clickable tile
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill((row + col) % 2 == 0 ? Color.BEIGE : Color.BROWN);

                final int finalRow = row;
                final int finalCol = col;
                tile.setOnMouseClicked(e -> handleTileClick(finalRow, finalCol));

                gridPane.add(tile, col, row);

                // piece over tile
                Piece piece = board.getPieceAt(row, col);
                if (piece != null) {
                    ImageView pieceView = createPieceImageView(piece);
                    gridPane.add(pieceView, col, row);
                }
            }
        }
        // Highlight king if in check
        String checkedColor = isKingInCheck("white") ? "white" :
        isKingInCheck("black") ? "black" : null;

        if (checkedColor != null) {
        Pair<Integer, Integer> kingPos = board.getKingPosition(checkedColor);
        if (kingPos != null) {
            Rectangle redOverlay = new Rectangle(TILE_SIZE, TILE_SIZE);
            redOverlay.setFill(Color.RED);
            redOverlay.setOpacity(0.5);
            redOverlay.setMouseTransparent(true);
            gridPane.add(redOverlay, kingPos.getValue(), kingPos.getKey());
        }
        }

    }

    private ImageView createPieceImageView(Piece piece) {
        Image image = new Image(
                "file:src/assets/piece/" +
                        piece.getColor() +
                        "_" +
                        piece.getType().toLowerCase() +
                        ".png");
        ImageView iv = new ImageView(image);
        iv.setFitWidth(TILE_SIZE);
        iv.setFitHeight(TILE_SIZE);
        iv.setMouseTransparent(true); // allow the mouse clicks
        return iv;
    }

    private String convertToPosition(int gridRow, int gridCol) {
        char file = (char) ('a' + gridCol);
        int rank = 8 - gridRow;
        return "" + file + rank;
    }

    private void handleTileClick(int row, int col) {
        System.out.println(
                "Clicked on tile at " + row + ", " + col +
                        " (" + convertToPosition(row, col) + ")");
        if (selectedPiece == null) {
            // Select the piece if present
            Piece piece = board.getPieceAt(row, col);
            if (piece != null && piece.getColor().equalsIgnoreCase(isWhiteTurn ? "white" : "black")) {
                selectedPiece = piece;
                selectedRow = row;
                selectedCol = col;
                System.out.println(
                        "Selected " + piece.getType() + " at " + row + ", " + col);
                System.out.println("Valid moves: " + selectedPiece.getValidMoves(row, col, board.getBoard()));
                highlightValidMoves(row, col);
            }
        } else {
            System.out.println(
                    "Attempting to move " +
                            selectedPiece.getType() +
                            " from " +
                            convertToPosition(selectedRow, selectedCol) +
                            " to " +
                            convertToPosition(row, col));
            movePiece(row, col);
        }
    }

    private void highlightValidMoves(int row, int col) {
        List<Pair<Integer, Integer>> allMoves = selectedPiece.getValidMoves(
            row, col,
            board.getBoard());

    List<Pair<Integer, Integer>> safeMoves = new ArrayList<>();

    for (Pair<Integer, Integer> move : allMoves) {
        int targetRow = move.getKey();
        int targetCol = move.getValue();

        // Simulate move
        Piece captured = board.getPieceAt(targetRow, targetCol);
        board.setPieceAt(targetRow, targetCol, selectedPiece);
        board.setPieceAt(row, col, null);

        boolean isSafe = !isKingInCheck(selectedPiece.getColor());

        board.setPieceAt(row, col, selectedPiece);
        board.setPieceAt(targetRow, targetCol, captured);

        if (isSafe) {
            safeMoves.add(move);
        }
    }

    // Draw highlights for only safe moves
    for (Pair<Integer, Integer> move : safeMoves) {
        Rectangle highlight = new Rectangle(TILE_SIZE, TILE_SIZE);
        highlight.setFill(Color.LIGHTGREEN);
        highlight.setOpacity(0.5);
        highlight.setMouseTransparent(true);
        gridPane.add(highlight, move.getValue(), move.getKey());
    }
    }

    private boolean isKingInCheck(String color) {
        Pair<Integer, Integer> kingPosition = board.getKingPosition(color);
        String opponentColor = color.equals("white") ? "black" : "white";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPieceAt(i, j);
                if (piece != null && piece.getColor().equals(opponentColor)) {
                    List<Pair<Integer, Integer>> possibleMoves = board.getPossibleMoves(i, j);
                    for (Pair<Integer, Integer> move : possibleMoves) {
                        if (move.equals(kingPosition)) {
                            return true; // King is under attack
                        }
                    }
                }
            }
        }
        
        return false;
    }

    private void movePiece(int row, int col) {

        int fromX = selectedRow;
        int fromY = selectedCol;
        List<Pair<Integer, Integer>> validMoves = selectedPiece.getValidMoves(
                fromX, fromY,
                board.getBoard());
        System.out.println(
                "Moving " +
                        selectedPiece.getType() +
                        " from " +
                        convertToPosition(fromX, fromY) +
                        " to " +
                        convertToPosition(row, col));
        boolean isValidMove = false;
        System.out.println("Current position: " + fromX + ", " + fromY);
        System.out.println("Target position: " + row + ", " + col);
        for (Pair<Integer, Integer> move : validMoves) {
            if (move.getKey() == row && move.getValue() == col) {
                isValidMove = true;
                break;
            }
        }
        if (isValidMove) {
            // Save original state
            Piece capturedPiece = board.getPieceAt(row, col);
            board.movePiece(fromX, fromY, row, col);

            Piece movedPiece = board.getPieceAt(row, col);
            if (movedPiece != null && movedPiece.getType().equalsIgnoreCase("pawn")) {
                if ((movedPiece.getColor().equals("white") && row == 0) ||
                    (movedPiece.getColor().equals("black") && row == 7)) {
                    board.setPieceAt(row, col, new pieces.Queen(movedPiece.getColor())); // Promote to queen
                    System.out.println("Pawn promoted to Queen at " + convertToPosition(row, col));
                }
                    }
                // Check for self-check (i.e., move puts own king in check)
                if (isKingInCheck(selectedPiece.getColor())) {
                    System.out.println("Move would result in self-check. Move canceled.");
                    // Undo the move
                    board.movePiece(row, col, fromX, fromY);
                    board.setPieceAt(row, col, capturedPiece); // Restore captured piece if any
                } else {
                    isWhiteTurn = !isWhiteTurn;
                    turnLabel.setText("Turn: " + (isWhiteTurn ? "White" : "Black"));

                    if (isKingInCheck(isWhiteTurn ? "white" : "black")) {
                        if (isCheckmate(isWhiteTurn ? "white" : "black")) {
                            turnLabel.setText("Checkmate! " + (isWhiteTurn ? "Black" : "White") + " wins!");
                        } else {
                            turnLabel.setText("Check on " + (isWhiteTurn ? "White" : "Black"));
                        }
                    }                    
            }
        }

        selectedPiece = null;
        renderBoard();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean isCheckmate(String color) {
        // If king is NOT in check, then not a checkmate
        if (!isKingInCheck(color)) return false;
    
        // Go through all pieces of the current color
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPieceAt(row, col);
                if (piece != null && piece.getColor().equals(color)) {
                    List<Pair<Integer, Integer>> possibleMoves = piece.getValidMoves(row, col, board.getBoard());
                    for (Pair<Integer, Integer> move : possibleMoves) {
                        int toRow = move.getKey();
                        int toCol = move.getValue();
    
                        // Simulate move
                        Piece captured = board.getPieceAt(toRow, toCol);
                        board.setPieceAt(toRow, toCol, piece);
                        board.setPieceAt(row, col, null);
    
                        boolean safe = !isKingInCheck(color);
    
                        // Undo move
                        board.setPieceAt(row, col, piece);
                        board.setPieceAt(toRow, toCol, captured);
    
                        if (safe) {
                            return false; // Found a legal escape
                        }
                    }
                }
            }
        }
    
        return true; // No valid moves left, and king is in check
    }    
}


