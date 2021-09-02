import java.util.ArrayList;
import java.util.List;

public class MediumAI extends Gamer {
    private List<String> history = new ArrayList<>();

    public MediumAI(int length) {
        this.length = length;
        this.secret = getRandomString(length);
    }


    @Override
    public String guess() {
        String randGuess = getRandomString(length);

        while (history.contains(randGuess)) {
            randGuess = getRandomString(length);
        }

        history.add(randGuess);

        return randGuess;
    }
}
