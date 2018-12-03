package tictactoe;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * Created by pwilkin on 15-Nov-18.
 */
public class Calosc {

    @FXML
    protected GridPane grid;

    protected ApplicationController controller;

    public ApplicationController getController() {
        return controller;
    }

    public void initialize() {
        try {
            controller = new ApplicationController(); // tworzymy nowy application controler i przypisujemy do zmiennej
            FXMLLoader ticTacToeLoader = new FXMLLoader(getClass().getResource("tictactoe.fxml"));  // tworzymy nowy loader (laduje z pliku fxml elementy graficzne)
            Node plansza = ticTacToeLoader.load();
            Controller boardController = ticTacToeLoader.getController();
            boardController.setMainController(controller); // do kontrolera tablicy podpinamy main application controller
            FXMLLoader statsLoader = new FXMLLoader(getClass().getResource("stats.fxml"));
            Node statystyki = statsLoader.load(); // to samo co z plansza
            Stats statsController = statsLoader.getController();
            statsController.setMainController(controller);
            grid.add(plansza, 0, 0); // wk³adamy do siatki po prawej plansze, po lewej statystyki
            grid.add(statystyki, 1, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
