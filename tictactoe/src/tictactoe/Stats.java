package tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tictactoe.event.GameWonEvent;
import tictactoe.event.RequestNewGameEvent;
import tictactoe.event.RequestNewGameWithPCEvent;

/**
 * Created by pwilkin on 15-Nov-18.
 */
public class Stats {

    int krzyzykWins = 0;
    int kolkoWins = 0;

    protected ApplicationController mainController;

    public void setMainController(ApplicationController mainController) {
        this.mainController = mainController;
        mainController.registerHandler(GameWonEvent.class, event -> updateWins(event.getWhoWon())); // jesli dostajesz zdarzenie GameWonEvent, to uaktualnij wygrane
    }

    @FXML
    protected Label krzyzyk;

    @FXML
    protected Label kolko;

    public void newGame(ActionEvent actionEvent) {
        mainController.handleEvent(new RequestNewGameEvent()); // kontrolerze, obs³u¿ zdarzenie nowej gry
    }
    
   public void setPC(ActionEvent actionEvent) {
    	mainController.handleEvent(new RequestNewGameWithPCEvent()); // zaraz dopisze
    }

    private void updateWins(Player wins) {
        if (wins == Player.CROSS) {
            krzyzykWins++;
        } else {
            kolkoWins++;
        }
        updateLabels();
    }

    private void updateLabels() {
        krzyzyk.setText("Gracz X: " + krzyzykWins);
        kolko.setText("Gracz O: " + kolkoWins);
    }
}
