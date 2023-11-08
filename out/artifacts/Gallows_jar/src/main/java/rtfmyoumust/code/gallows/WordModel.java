package rtfmyoumust.code.gallows;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordModel implements WordModelInterface {
    private List<Observer> gameObservers = new ArrayList<>();
    private List<String> dictionary = new ArrayList<>();
    private ArrayList<Character> inputLetters = new ArrayList<>();
    private boolean gameLost = false;
    private boolean isRightLetter;
    private boolean duplicate;
    private String targetWord;
    private StringBuilder maskedTargetWord;
    private int mistakeCount = 0;
    private final int END_GAME_VALUE = 6;
    private String gameMessage;
    private final String POSITIVE_GAME_RESULT = "YOU WIN!";
    private final String NEGATIVE_GAME_RESULT = "YOU LOSE!";
    private final String NEGATIVE_LETTER = "NICE TRY, BUT NO";
    private final String DUPLICATE_LETTER = "THAT LETTER WAS ALREADY THERE, WRITE ANOTHER ONE!";
    private final String GAME_IS_ON = "OK! WHAT'S THE NEXT LETTER?";
    private Random random = new Random();
    private final String DICTIONARY_FILE_NAME = "/dict.txt";

    @Override
    public void initialize() throws Exception {
        loadDictionary(DICTIONARY_FILE_NAME);
        this.setTargetWord(dictionary.get(random.nextInt(dictionary.size() - 1)));
        notifyObservers();
    }

    // this method is needed to solve the problem of loading a file from a resources folder
    public static Path getPathFile(String fileName) {
        return Paths.get("src", "main", "resources", "dict.txt")
                .toAbsolutePath().normalize();
    }

    public void loadDictionary(String fileName) throws Exception {
        Path path = getPathFile(fileName);
        try (Scanner scanner = new Scanner(path))  {
            while(scanner.hasNextLine()){
                this.dictionary.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e){
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTargetWord(String targetWord) {
        this.targetWord = targetWord;
        this.maskedTargetWord = new StringBuilder("*".repeat(targetWord.length()));
    }

    public ArrayList<Character> getInputLetters() {
        return inputLetters;
    }

    public String getMaskedTargetWord() {
        return maskedTargetWord.toString();
    }

    public int getMistakeCount() {
        return mistakeCount;
    }

    public boolean isRightLetter() {
        return isRightLetter;
    }

    public boolean checkLetter(char symbol) {

        if (!inputLetters.contains(symbol)) {
            duplicate = false;
            inputLetters.add(symbol);
        } else {
            duplicate = true;
            setGameMessage(DUPLICATE_LETTER);
        }

        isRightLetter = (targetWord.indexOf(symbol) != -1);

        //I don't count duplicate letter errors.
        return (targetWord.indexOf(symbol) != -1) || duplicate;
    }

    public void openLetters(char symbol) {
        setGameMessage(GAME_IS_ON);

        if (checkLetter(symbol)) {
            for (int i = 0; i < maskedTargetWord.length(); i++) {
                if (targetWord.charAt(i) == symbol) {
                    maskedTargetWord.setCharAt(i, symbol);
                }
            }
        } else {
            setGameMessage(NEGATIVE_LETTER);
            mistakeCount++;
        }

        if (mistakeCount == END_GAME_VALUE) {
            gameLost = true;
            setGameMessage(NEGATIVE_GAME_RESULT);
        }

        if (isWordSolved()) {
            setGameMessage(POSITIVE_GAME_RESULT);
        }

        notifyObservers();
    }

    public boolean isWordSolved() {
        return maskedTargetWord.indexOf("*") == -1;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public String getGameMessage() {
        return gameMessage;
    }

    @Override
    public void registeredObserver(Observer o) {
        gameObservers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : gameObservers) {
            try {
                o.update();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setGameMessage(String gameMessage) {
        this.gameMessage = gameMessage;
    }

    public String getTargetWord() {
        return targetWord;
    }
}
