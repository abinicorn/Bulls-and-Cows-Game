import java.io.*;
import java.util.*;
import java.util.Iterator;


public class GameLibrary {
    private List<List<String>> historyPlayer;
    private List<List<String>> historyAi;
    private String winner;

    public GameLibrary() {
        historyPlayer = new ArrayList<List<String>>();
        historyAi = new ArrayList<List<String>>();
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void addAiHistory(List<String> history) {
        historyAi.add(history);
    }

    public void addPlayerHistory(List<String> history) {
        historyPlayer.add(history);
    }


    // When the player chooses automatic mode and writes the correct file name "random.txt"
    // then it will read from a file
    public static List<String> readFile(int numOfGuess, int length) {
        List<String> result = new ArrayList<>();
        System.out.println("Please enter an file name: ");
        String filename = Keyboard.readInput();
        File inputFile = new File(filename);
        if (!inputFile.exists()) {
            System.out.println("The file doesn't exist, try again!");
            result = readFile(numOfGuess, length);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int count = 0;
            String line;
            while ((line = reader.readLine()) != null && count < numOfGuess) {
                result.add(line.substring(0, length));
                count++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return result;

    }


    // When the game is over and the player wishes to save the result, this method save the result
    public void saveToFile(String fileName, String pSec, String aSec, int numTurn) {
        File outFile = new File(fileName);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
            bw.write("Bulls & Cows game result.\n");
            bw.write("Your code: " + pSec + "\n");
            bw.write("Computer code: " + aSec + "\n");

            Iterator<List<String>> aiHistoryIter = historyAi.iterator();
            Iterator<List<String>> playerHistoryIter = historyPlayer.iterator();

            int count = 0;
            while (aiHistoryIter.hasNext() || playerHistoryIter.hasNext()) {
                bw.write("----\n");
                bw.write("Turn " + (count + 1) + "\n");

                if (playerHistoryIter.hasNext()) {
                    List<String> element = playerHistoryIter.next();
                    bw.write("You guessed " + element.get(0) + ", scoring " + element.get(1) + " bulls and " + element.get(2) + " cows\n");

                }
                if (aiHistoryIter.hasNext()) {
                    List<String> element = aiHistoryIter.next();
                    bw.write("Computer guessed " + element.get(0) + ", scoring " + element.get(1) + " bulls and " + element.get(2) + " cows\n");
                }
                count++;
            }
            bw.write("----\n");

            if (winner != null) {
                bw.write(winner + " won.\n");
            } else {
                bw.write("It is a draw.\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
