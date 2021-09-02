import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Gamer {
    protected String secret;
    protected int length;


    public abstract String guess();


    public int checkBulls(String guess) {
        int result = 0;

        if (secret.length() != guess.length())
            return -1;

        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i))
                result++;
        }
        return result;
    }

    public int checkCows(String guess) {
        int result = 0;

        if (secret.length() != guess.length())
            return -1;
        for (int i = 0; i < guess.length(); i++) {
            if (secret.contains(guess.charAt(i) + "") && secret.indexOf(guess.charAt(i)) != i)
                result++;
        }
        return result;
    }

    protected String getRandomString(int length) {
        String number = "";
        Random random = new Random();
        while (number.length() != length) {
            String num = random.nextInt(10) + "";
            if (number.length() == 0) {
                if ("0".equals(num)) {
                    continue;
                }
            }
            if (number.indexOf(num) == -1) {
                number += num;
            }

        }
        return number;
    }

    public String getSecret() {
        return secret;
    }



}
