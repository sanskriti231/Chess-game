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
        final int currentRow = row;
        for (int col = 0; col < BOARD_SIZE; col++) {
            final int currentCol = col;

            // Create tile with click handler FIRST
            Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
            tile.setFill((currentRow + currentCol) % 2 == 0 ? Color.BEIGE : Color.BROWN);
            tile.setOnMouseClicked(e -> handleTileClick(currentRow, currentCol));
            gridPane.add(tile, col, currentRow); // Add tile first

            // Add piece on top of tile
            String position = convertToPosition(currentRow, currentCol);
            Piece piece = board.getPieceAt(position);
            if (piece != null) {
                ImageView pieceView = createPieceImageView(piece);
                gridPane.add(pieceView, col, currentRow); // Add on top of tile
            }
        }
    }
}

private ImageView createPieceImageView(Piece piece) {
    Image image = new Image("file:src/assets/piece/" + piece.getColor() + "_" + piece.getType().toLowerCase() + ".png");
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(TILE_SIZE);
    imageView.setFitHeight(TILE_SIZE);
    imageView.setMouseTransparent(true); // Crucial: let clicks pass through to tile
    return imageView;
}

    private String convertToPosition(int gridRow, int gridCol) {
    char file = (char) ('a' + gridCol);
    int rank = 8 - gridRow; // Grid row 0 = rank 8, grid row 7 = rank 1
    return "" + file + rank;
}

    private void handleTileClick(int row, int col) {
        String position = convertToPosition(row, col);
        if (selectedPiece == null) {
            // Select the piece if present
            Piece piece = board.getPieceAt(position);
            if (piece != null) {
                selectedPiece = piece;
                selectedRow = row;
                selectedCol = col;
                System.out.println("Selected " + piece.getType() + " at " + position);
                highlightValidMoves(position);
            }
        } else {
            // Attempt to move the selected piece
            System.out.println("Attempting to move " + selectedPiece.getType() + " from " + convertToPosition(selectedRow, selectedCol) + " to " + position);
            movePiece(position);
        }
    }

    private void highlightValidMoves(String position) {
        List<String> validMoves = selectedPiece.getValidMoves(position, board.getBoard());
        for (String move : validMoves) {
            int targetRank = Integer.parseInt(move.substring(1, 2));
            int targetRow = 8 - targetRank;
            int targetCol = move.charAt(0) - 'a';

            Rectangle highlight = new Rectangle(TILE_SIZE, TILE_SIZE);
            highlight.setFill(Color.LIGHTGREEN);
            highlight.setOpacity(0.5);
            gridPane.add(highlight, targetCol, targetRow);
        }
    }

    private void movePiece(String targetPosition) {
        String fromPosition = convertToPosition(selectedRow, selectedCol);
        List<String> validMoves = selectedPiece.getValidMoves(fromPosition, board.getBoard());

        if (validMoves.contains(targetPosition)) {
            board.movePiece(fromPosition, targetPosition);
            selectedPiece = null;
            renderBoard();
        } else {
            selectedPiece = null;
            renderBoard();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}