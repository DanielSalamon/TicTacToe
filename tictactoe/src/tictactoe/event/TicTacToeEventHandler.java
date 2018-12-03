package tictactoe.event;

/**
 * Created by pwilkin on 22-Nov-18.
 */
public interface TicTacToeEventHandler<T extends TicTacToeEvent> {

    public void handleEvent(T event);

}
