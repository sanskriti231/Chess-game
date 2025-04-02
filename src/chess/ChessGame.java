package chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pieces.Piece;

import java.util.List;

public class ChessGame extends Application {

    private static final int TILE_SIZE = 80;
    private static final int BOARD_SIZE = 8;
    private GridPane gridPane;
    private Board board;

    private Piece selectedPiece = null;
    private int selectedRow = -1;
    private int selectedCol = -1;

    @Override
    public void start(Stage primaryStage) {
        board = new Board(); // Initialize the board
        gridPane = new GridPane();

        // Render the chessboard
        renderBoard();

        Scene scene = new Scene(gridPane, TILE_SIZE * BOARD_SIZE, TILE_SIZE * BOARD_SIZE);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void renderBoard() {
        gridPane.getChildren().clear();

        for (int row = 0; row < BOARD_SIZE; row++) {
            final int currentRow = row; // Create a final copy of row
            for (int col = 0; col < BOARD_SIZE; col++) {
                final int currentCol = col; // Create a final copy of col
                // Create a tile (rectangle)
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill((currentRow + currentCol) % 2 == 0 ? Color.BEIGE : Color.BROWN);

                // Add the tile to the grid
                gridPane.add(tile, col, currentRow);

                // Add a piece if one exists at this position
                Piece piece = board.getPieceAt("" + (char) ('a' + col) + (8 - currentRow));
                if (piece != null) {
                    Image pieceImage = new Image("file:src/assets/piece/" + piece.getColor() + "_" + piece.getType().toLowerCase() + ".png");
                    ImageView pieceView = new ImageView(pieceImage);
                    pieceView.setFitWidth(TILE_SIZE);
                    pieceView.setFitHeight(TILE_SIZE);

                    // Center the piece on the tile
                    GridPane.setColumnIndex(pieceView, col);
                    GridPane.setRowIndex(pieceView, currentRow);
                    gridPane.add(pieceView, col, currentRow);

                    pieceView.setOnMouseClicked(e -> handlePieceClick(piece, currentRow, currentCol));
                }
            }
        }
    }

    private void handlePieceClick(Piece piece, int row, int col) {
    if (selectedPiece == null) {
        // Select the piece
        selectedPiece = piece;
        selectedRow = row;
        selectedCol = col;

        System.out.println("Selected " + piece.getType() + " at " + (char) ('a' + col) + (8 - row));

        // Highlight valid moves
        List<String> validMoves = piece.getValidMoves("" + (char) ('a' + col) + (8 - row), board.getBoard());
        for (String move : validMoves) {
            int targetRow = 8 - Character.getNumericValue(move.charAt(1));
            int targetCol = move.charAt(0) - 'a';

            // Highlight the tile (e.g., change its color)
            Rectangle highlight = new Rectangle(TILE_SIZE, TILE_SIZE);
            highlight.setFill(Color.LIGHTGREEN);
            highlight.setOpacity(0.5);
            gridPane.add(highlight, targetCol, targetRow);
        }
    } else {
        // Attempt to move the selected piece
        movePiece(row, col);
    }
}

private void movePiece(int targetRow, int targetCol) {
    if (selectedPiece != null) {
        // Calculate the target position in algebraic notation
        String targetPosition = "" + (char) ('a' + targetCol) + (8 - targetRow);
        System.out.println("Attempting to move to: " + targetPosition);

        // Check if the move is valid
        List<String> validMoves = selectedPiece.getValidMoves("" + (char) ('a' + selectedCol) + (8 - selectedRow), board.getBoard());
        System.out.println("Valid moves: " + validMoves);

        if (validMoves.contains(targetPosition)) {
            // Update the board
            board.movePiece("" + (char) ('a' + selectedCol) + (8 - selectedRow), targetPosition);
            System.out.println("Moved " + selectedPiece.getType() + " to " + targetPosition);

            // Clear the selection
            selectedPiece = null;
            selectedRow = -1;
            selectedCol = -1;

            // Re-render the board
            renderBoard();
        } else {
            System.out.println("Invalid move!");

            // Deselect the piece
            selectedPiece = null;
            selectedRow = -1;
            selectedCol = -1;

            // Re-render the board to remove highlights
            renderBoard();
        }
    }
}

    public static void main(String[] args) {
        launch(args);
    }
}