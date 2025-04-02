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

public class ChessGame extends Application {

    private static final int TILE_SIZE = 80;
    private static final int BOARD_SIZE = 8;
    private GridPane gridPane;
    private Board board;

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
                    Image pieceImage = new Image("file:assets/piece/" + piece.getColor() + "_" + piece.getType().toLowerCase() + ".svg");
                    ImageView pieceView = new ImageView(pieceImage);
                    pieceView.setFitWidth(TILE_SIZE * 0.8);
                    pieceView.setFitHeight(TILE_SIZE * 0.8);

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
        System.out.println("Clicked on " + piece.getType() + " at " + (char) ('a' + col) + (8 - row));
        // TODO: Implement piece selection and movement logic
    }

    public static void main(String[] args) {
        launch(args);
    }
}