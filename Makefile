build:
	@javac --module-path lib/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -d bin src/**/*.java

run: build
	@java --module-path lib/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp bin chess.ChessGame
