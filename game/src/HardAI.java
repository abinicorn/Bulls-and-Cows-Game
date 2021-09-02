import javax.imageio.ImageTranscoder;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class HardAI extends Gamer {

    private String history = "";

    private List<String> potentialGuess = new ArrayList<>();
    private int bullNum;
    private int cowNum;

    public HardAI(int length) {
        this.length = length;
        this.secret = getRandomString(length);
        createNewList();
    }

    //To get the num of bulls and cows
    public void passBullCow(int bull, int cow) {
        bullNum = bull;
        cowNum = cow;
        keepSameBullCow();
    }


    // To modify the number list, which makes numbers in the list have the same bulls and cows as the history guessnumber
    private void keepSameBullCow() {
        if (potentialGuess.contains(history) && potentialGuess.size()!=1) {
            potentialGuess.remove(history);
        }
        Iterator<String> myIterator = potentialGuess.iterator();
        while (myIterator.hasNext()) {
            String element = myIterator.next();
            int guessBullNum = 0;
            int guessCowNum = 0;
            for (int i = 0; i < length; i++) {
                if (element.charAt(i) == history.charAt(i)) {
                    guessBullNum++;
                }
                if (element.contains(history.charAt(i) + "") && element.indexOf(history.charAt(i)) != i) {
                    guessCowNum++;
                }
            }
            if ((guessBullNum != bullNum || guessCowNum != cowNum) && potentialGuess.size() != 1) {
                myIterator.remove();
            }
        }
    }



    private void createNewList() {
        for (int i = 0; i < 10000; i++) {
            String element = getRandomString(length);
            if (!potentialGuess.contains(element)) {
                potentialGuess.add(element);
            }
        }
    }

    @Override
    public String guess() {
        if (history.equals("")) {
            history = getRandomString(length);
        } else {
            if (potentialGuess.size() == 1) {
                history = potentialGuess.get(0);
            }
            int rand = (int) (Math.random() * potentialGuess.size());
            history = potentialGuess.get(rand);
        }
        return history;

    }

}
