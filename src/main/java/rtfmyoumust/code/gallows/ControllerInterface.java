package rtfmyoumust.code.gallows;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public interface ControllerInterface {

    void setLetter();
    void clear();
    void restartGame(ActionEvent event) throws IOException;
    void start();
    void openMenu(KeyEvent event);
}
