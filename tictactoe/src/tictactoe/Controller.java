package tictactoe;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import tictactoe.event.GameWonEvent;
import tictactoe.event.RequestNewGameEvent;
import tictactoe.event.RequestNewGameWithPCEvent;

public class Controller {

    protected ApplicationController mainController;

    public void setMainController(ApplicationController mainController) {
        this.mainController = mainController;
        mainController.registerHandler(RequestNewGameEvent.class, event -> startNewGame()); // jak dostaniesz to zdarzenie (RequestNewGameEvent), to rozpocznij nowa gre
        mainController.registerHandler(RequestNewGameWithPCEvent.class, event -> startNewGameWithPC());
    }

    @FXML
    protected GridPane grid;

    protected boolean gameEnded;
    protected Board board;

    public void initialize() {
        for (Node child : grid.getChildren()) { // jak juz stworzysz plansze, to dla kazdego pola
            Integer row = Optional.ofNullable(GridPane.getRowIndex(child)).orElse(0); 
            Integer column = Optional.ofNullable(GridPane.getColumnIndex(child)).orElse(0);
            child.setOnMouseClicked(event -> handleMoveWithoutPC(row, column)); // dodaj handler, który obsluzy ruch jak sie w niego kliknie
        }
        startNewGame(); // rozpocznij now¹ grê
    }
    
  

    private void handleMove(Integer row, Integer column) {
        if (!gameEnded) {
            if (board.canYouMakeAMove(row, column)) {
                board.makeMove(row, column);
                if (!checkVictoryShowAndRegister()) {
                    board.makeComputerMove();
                }
            }
            drawBoard(); // rysujemy plansze po ruchu
            if (!gameEnded) {
                checkVictoryShowAndRegister();
            }
        }
    }
    
    private void handleMoveWithoutPC(Integer row, Integer column) {
    	if (!gameEnded) {
    		if (board.canYouMakeAMove(row, column)) {
    			board.makeMove(row, column);
    		}
    	}
    	drawBoard();
    	if(!gameEnded) {
    		checkVictoryShowAndRegister();
    	}
    }

    private boolean checkVictoryShowAndRegister() {
        Player wins = board.checkVictory();
        if (wins != null) {
            gameEnded = true;
            showVictoryMessage(wins);
            mainController.handleEvent(new GameWonEvent(wins));
            return true;
        }
        return false;
    }

    private void drawBoard() {
        for (Node child : grid.getChildren()) {
            Integer row = Optional.ofNullable(GridPane.getRowIndex(child)).orElse(0);
            Integer column = Optional.ofNullable(GridPane.getColumnIndex(child)).orElse(0);
            Pane pane = (Pane) child;
            pane.getChildren().clear();
            Player inCell = board.getPlayerForField(row, column);   // sprawdzamy czy ktos cos wstawil do komorki (klik¹³)
            if (inCell != null) {									// sprawdzamy wszystkie warunki
                Label label = new Label(inCell.getSign());
                label.setPrefWidth(30.0);
                label.setPrefHeight(30.0);
                label.setAlignment(Pos.CENTER);
                pane.getChildren().add(label); // dodajemy krzyzyk lub kolko do komórki
            }
        }
    }

    private void showVictoryMessage(Player wins) {
        Alert alert = new Alert(AlertType.WARNING, "Player " + wins + " wins!", ButtonType.OK);
        alert.showAndWait(); //okno modalne zatrzymuje dzia³anie programu
    }

    private void startNewGame() {
        gameEnded = false;
        board = new Board();
        for (Node child : grid.getChildren()) { // jak juz stworzysz plansze, to dla kazdego pola
            Integer row = Optional.ofNullable(GridPane.getRowIndex(child)).orElse(0); 
            Integer column = Optional.ofNullable(GridPane.getColumnIndex(child)).orElse(0);
            child.setOnMouseClicked(event -> handleMoveWithoutPC(row, column));
        }
        drawBoard();
        
        // Tworzenie siatki 19x19
        GridPane gp = new GridPane();
        for(int i = 0; i <= 19; i++) {
        	for(int j = 0; j <= 19; j++) {
        		Pane pane = new Pane();
        		gp.add(pane, j, i);
        		int row = i; // wa¿ne, ¿eby zamroziæ wartoœci i i j
        		int col = j;
        		pane.setOnMouseClicked(click -> handleMove(row, col));
        	}
        }
    }
    
    private void startNewGameWithPC() {
    	gameEnded = false;
    	board = new Board();
    	for (Node child : grid.getChildren()) {
    		Integer row = Optional.ofNullable(GridPane.getRowIndex(child)).orElse(0); 
            Integer column = Optional.ofNullable(GridPane.getColumnIndex(child)).orElse(0);
    		child.setOnMouseClicked(event -> handleMove(row, column));
    	}
    	drawBoard();
    }
}
