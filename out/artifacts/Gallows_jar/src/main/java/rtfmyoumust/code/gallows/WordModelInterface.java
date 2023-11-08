package rtfmyoumust.code.gallows;

import java.util.List;

public interface WordModelInterface {
    void initialize() throws Exception;
    boolean checkLetter(char symbol);
    void openLetters(char symbol);
    String getMaskedTargetWord();
    List<Character> getInputLetters();
    String getGameMessage();
    boolean isWordSolved();
    int getMistakeCount();
    boolean isGameLost();

    String getTargetWord();
    boolean isRightLetter();

    void registeredObserver(Observer o);
    void notifyObservers();
}
