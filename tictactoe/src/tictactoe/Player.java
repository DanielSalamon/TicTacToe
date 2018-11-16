package tictactoe;

/**
 * Created by pwilkin on 08-Nov-18.
 */
public enum Player {

    CROSS("X"),
    CIRCLE("O");

    private String sign;

    private Player(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }


}
