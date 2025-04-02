import chess.ChessGame;
import javafx.stage.Stage;

public class Main {

    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        game.start(new Stage());
    }
}
