import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private Gamer player;
    private Gamer ai;
    private String mode;
    private String save;
    private GameLibrary gl = new GameLibrary();

    //Two configurable values: turns and length.
    private final int MAX_TURNS = 7;
    private final int MAX_LENGTH=4;

    public void start() {
        System.out.println("Welcome to the Cows and Bulls Game!");

        //Choose the game mode and difficulty level for this game
        getGameDifficulty();
        getGameMode();

        //Get the secret code from player, if the mode is automatic, then the following guessing process will be automatic
        if (mode.equals("automatic")) {
            player = new Player(true, MAX_TURNS, MAX_LENGTH, getSecretCode());
        } else {
            player = new Player(getSecretCode());
        }

        //Start the Guessing process
        for (int i = 0; i < MAX_TURNS; i++) {
            System.out.println("---");
            System.out.print("You guess: ");
            String playerGuess = player.guess();
            while(!checkUserInput(playerGuess)){
                System.out.print("You guess: ");
                playerGuess=player.guess();
            }
            if (mode.equals("automatic")) {
                System.out.println(playerGuess);
            }

            //Save the player guessing and checking record to GameLibrary
            List<String> historyPlayer = new ArrayList<>();
            historyPlayer.add(playerGuess);
            int cow = ai.checkCows(playerGuess);
            int bull = ai.checkBulls(playerGuess);
            historyPlayer.add("" + bull);
            historyPlayer.add("" + cow);
            gl.addPlayerHistory(historyPlayer);

            //Print the bulls and cows result to the console
            printResult(bull, cow);
            if (bull == MAX_LENGTH) {
                System.out.println("You win! :)");
                gl.setWinner("You");
                break;
            }
            System.out.println();

            //Print the AI guess to the console
            String aiGuess = ai.guess();
            System.out.println("AI guess: " + aiGuess);

            //Save the AI guessing and checking record to the GameLibrary
            List<String> historyAi = new ArrayList<>();
            historyAi.add(aiGuess);
            int aiCow = player.checkCows(aiGuess);
            int aiBull = player.checkBulls(aiGuess);
            if (ai instanceof HardAI) {
                ((HardAI) ai).passBullCow(aiBull, aiCow);
            }
            historyAi.add("" + aiBull);
            historyAi.add("" + aiCow);

            //Print the cows and bulls result to the console
            printResult(aiBull, aiCow);
            gl.addAiHistory(historyAi);
            if (aiBull == MAX_LENGTH) {
                System.out.println("AI win! :(");
                gl.setWinner("AI");
                break;
            }

            if (i == (MAX_TURNS - 1)) {
                System.out.println("It's a draw!");
            }
        }

        //Let the player chooses whether saving the result or not
        saveResult();
        System.out.println("Thank you for playing the game! Goodbye!");
    }


    private String getSecretCode() {
        String result = "";
        System.out.println("Please enter your secret code (Digits should be all different): ");
        result = Keyboard.readInput();
        // To avoid the invalid input from the user
        if(!checkUserInput(result)){

            result = getSecretCode();
        }
        return result;
    }

    private void getGameDifficulty() {
        System.out.println("Please choose which AI will you want to play with: Easy, Medium and Hard");
        String result = Keyboard.readInput().toLowerCase();
        switch (result) {
            case "easy":
                ai = new EasyAI(MAX_LENGTH);
                break;
            case "medium":
                ai = new MediumAI(MAX_LENGTH);
                break;
            case "hard":
                ai = new HardAI(MAX_LENGTH);
                break;
            case "":
                getGameDifficulty();
                break;
            default:
                System.out.println("Unrecognised command");
                getGameDifficulty();
        }
    }

    private void getGameMode() {
        System.out.println("Do you wish to enter your guesses manually, or automatically:  manual Or automatic");
        String result = Keyboard.readInput().toLowerCase();
        switch (result) {
            case "automatic":
                mode = "automatic";
                break;
            case "manual":
                mode = "manual";
                break;
            case "":
                getGameMode();
                break;
            default:
                System.out.println("Invalid input, try again");
                getGameMode();
                break;
        }
    }


    private void saveResult() {
        System.out.println("Do you wish to save the results? yes or no");
        save = Keyboard.readInput().toLowerCase();
        switch (save) {
            case "yes":
                System.out.println("Enter file name");
                gl.saveToFile(Keyboard.readInput(), player.getSecret(), ai.getSecret(), MAX_TURNS);
                break;
            case "no":
                break;
            case "":
                saveResult();
                break;
            default:
                System.out.println("Invalid input, try again");
                saveResult();
                break;
        }
    }


    private boolean checkUserInput (String strNum) {
        int num=0;

        //To check whether the user input is numeric or not
        try {
            num = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Invalid input, please try again");
            return false;
        }

        //To check whether the user input is negative or not matching the length
        if(num<=0|| strNum.length()!=MAX_LENGTH)
        {
            System.out.println("Invalid input, please try again");
            return false;
        }

        //To check whether the user input have repeat digits or not
        for (int i = 0; i < strNum.length() ; i++) {
            int count =0;
            for (int j = 0; j < strNum.length(); j++) {
                if (strNum.charAt(i) == strNum.charAt(j)) {
                    count++;
                }
            }
            if(count>1) {
                System.out.println("Digits should be all different, please try again");
                return false;
            }
        }

        return true;
    }


    private void printResult(int bulls, int cows) {
        System.out.println("Result: " + bulls + " bulls and " + cows + " cows");
    }


    public static void main(String[] args) {
        GameManager gm = new GameManager();
        gm.start();
    }


}
