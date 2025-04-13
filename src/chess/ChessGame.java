package chess;

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
        List<Pair<Integer, Integer>> validMoves = selectedPiece.getValidMoves(
                row, col,
                board.getBoard());
        for (Pair<Integer, Integer> move : validMoves) {
            int targetRow = move.getKey();
            int targetCol = move.getValue();

            Rectangle highlight = new Rectangle(TILE_SIZE, TILE_SIZE);
            highlight.setFill(Color.LIGHTGREEN);
            highlight.setOpacity(0.5);
            highlight.setMouseTransparent(true);
            gridPane.add(highlight, targetCol, targetRow);
        }
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
            board.movePiece(fromX, fromY, row, col);
            isWhiteTurn = !isWhiteTurn;
            turnLabel.setText("Turn: " + (isWhiteTurn ? "White" : "Black"));
        }

        selectedPiece = null;
        renderBoard();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
