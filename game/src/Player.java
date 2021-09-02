import java.util.Iterator;
import java.util.List;

public class Player extends Gamer {
    private List<String> autoGuess;
    private boolean isAuto;
    private Iterator<String> myIterator;

    public Player(String secret) {
        this.isAuto = isAuto;
        this.secret = secret;
    }

    public Player(boolean isAuto, int numOfGuess, int length, String secret) {
        this.isAuto = isAuto;
        this.length = length;
        this.secret = secret;
        autoGuess = GameLibrary.readFile(numOfGuess, length);
        myIterator = autoGuess.iterator();

    }

    @Override
    public String guess() {
        String guess = "";
        if (isAuto) {
            if (myIterator.hasNext()) {
                guess = myIterator.next();

            }

        } else {
            guess = Keyboard.readInput();
        }
        return guess;
    }

}
