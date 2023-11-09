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
    private boolean containsLetter;
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
    private final String INVALID_INPUT_SYMBOL = "YOU MUST ENTER A LETTER OF THE RUSSIAN ALPHABET!";
    private final String DICTIONARY_FILE_NAME = "dict.txt";
    private Random random = new Random();

    @Override
    public void initialize() {
        loadDictionary(DICTIONARY_FILE_NAME);
        this.setTargetWord(dictionary.get(random.nextInt(dictionary.size() - 1)));
        notifyObservers();
    }

    public void loadDictionary(String fileName) {
        ClassLoader classLoader = WordModel.class.getClassLoader();
        Path path = Paths.get(classLoader.getResource(fileName).getPath());
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

    public boolean isContainsLetter() {
        return containsLetter;
    }

    public boolean checkLetter(char symbol) {
        return isContainsLetter(symbol) || !isValidCharacter(symbol) || isDuplicate(symbol);
    }

    public boolean isValidCharacter(char c) {
        boolean res = String.valueOf(c).matches("[А-Яа-я]");
        if (!res) setGameMessage(INVALID_INPUT_SYMBOL);
        return res;
    }

    public boolean isDuplicate(char c) {
        boolean res = this.inputLetters.contains(c);
        if (res) setGameMessage(DUPLICATE_LETTER);
        return res;
    }

    public boolean isContainsLetter(char c) {
        return containsLetter = targetWord.indexOf(c) != -1;
    }

    public void addToInputLetters(char c) {
        if (!isDuplicate(c) && isValidCharacter(c)) {
            this.inputLetters.add(c);
        }
    }

    public void openLetters(char symbol) {

        if (!isDuplicate(symbol)) {
            if (checkLetter(symbol)) {
                for (int i = 0; i < maskedTargetWord.length(); i++) {
                    if (targetWord.charAt(i) == symbol) {
                        setGameMessage(GAME_IS_ON);
                        maskedTargetWord.setCharAt(i, symbol);
                    }
                }
            } else {
                mistakeCount++;
                setGameMessage(NEGATIVE_LETTER);
            }
        }

        addToInputLetters(symbol);
        gameAnalize();
        notifyObservers();
    }

    public void gameAnalize() {
        if (mistakeCount == END_GAME_VALUE) {
            gameLost = true;
            setGameMessage(NEGATIVE_GAME_RESULT);
        }

        if (isWordSolved()) {
            setGameMessage(POSITIVE_GAME_RESULT);
        }
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
