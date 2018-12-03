package tictactoe.event;

import tictactoe.Player;

/**
 * Created by pwilkin on 22-Nov-18.
 */
public class GameWonEvent implements TicTacToeEvent {

    protected Player whoWon;

    public GameWonEvent(Player whoWon) {
        this.whoWon = whoWon;
    }

    public Player getWhoWon() {
        return whoWon;
    }
}
