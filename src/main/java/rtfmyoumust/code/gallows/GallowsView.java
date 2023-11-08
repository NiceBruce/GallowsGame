package rtfmyoumust.code.gallows;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class GallowsView implements Observer {
    @FXML
    ImageView pic;
    private Image image = new Image(getClass().getResourceAsStream("img/gallows_animation.png"));
    private String lives = "♥♥♥♥♥♥";
    @FXML
    private Label targetWord;
    @FXML
    private Label usefullLetters;
    @FXML
    private Label gameMessage;
    @FXML
    private Label score;
    @FXML
    TextField inputText;

    Timeline timeline;

    private final String GOOD_SOUND = "sound/8-bit-coin-fx_G_minor.wav";
    private final String BAD_SOUND = "sound/8-bit-wide-shot_E.wav";
    private final String WINNER_SOUND = "sound/8-bit-sawtooth_130bpm_D_major.wav";
    private final String LOOSER_SOUND = "sound/kl-peach-game-over-iii-142453.mp3";
    public final String MENU_SOUND = "sound/blipSelect.wav";
    public final String MENU_EXIT_SOUND = "sound/8-bit-spaceship-startup-102666.mp3";

    public MediaPlayer mediaPlayer;

    WordModelInterface wordModel = new WordModel();

    public GallowsView() {
        wordModel.registeredObserver(this);
    }

    public void playSound(String music) {
        Media sound = new Media(getClass().getResource(music).toExternalForm());
        this.mediaPlayer = new MediaPlayer(sound);
        if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING){
            this.mediaPlayer.play();
        }
    }

    void displayUsefullLetters(List<Character> list) {

        if (list.size() > 0) {
            if (!wordModel.isRightLetter()) {
                playSound(BAD_SOUND);
                timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), evt -> targetWord.setStyle("-fx-effect: innershadow( gaussian, rgba(246,2,39,0.91), 98, 0, 0, 0 ); -fx-border-color: #ff0025;")),
                        new KeyFrame(Duration.seconds( 0.5), evt -> targetWord.setStyle("-fx-effect: innershadow( gaussian, rgba( 40, 40, 40, 0.5 ), 10, 0, 5, 5 );")));
                timeline.setCycleCount(2);
                timeline.play();
            } else {
                playSound(GOOD_SOUND);
                timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> targetWord.setStyle("-fx-effect: innershadow( gaussian, rgb(18,246,2), 98, 0, 0, 0 ); -fx-border-color: #2fff00;")),
                        new KeyFrame(Duration.seconds( 2), evt -> targetWord.setStyle("-fx-effect: innershadow( gaussian, rgba( 40, 40, 40, 0.5 ), 10, 0, 5, 5 );")));
                timeline.setCycleCount(1);
                timeline.play();
            }
        }

        this.usefullLetters.setText("USED LETTERS: " + list.toString());
    }

    void printMaskedTargetWord(String targetWord) {
        String wordWithSpaces = "";
        for (char c : targetWord.toCharArray()) {
            wordWithSpaces += c + "  ";
        }
        this.targetWord.setText(wordWithSpaces);
    }

    void congradulateUser(boolean win, boolean loose){

        if (win) {
            targetWord.setTextFill(Color.GREEN);
            gameMessage.setTextFill(Color.GREEN);
            inputText.setOpacity(0);
            inputText.setDisable(true);
            playSound(WINNER_SOUND);
            mediaPlayer.setOnEndOfMedia(() -> {
                showMenu();
            });
        }

        if (loose) {
            targetWord.setTextFill(Color.RED);
            targetWord.setText("THE TARGER WORD WAS: " + wordModel.getTargetWord());
            gameMessage.setTextFill(Color.RED);
            inputText.setOpacity(0);
            inputText.setDisable(true);
            playSound(LOOSER_SOUND);
            mediaPlayer.setOnEndOfMedia(() -> {
                showMenu();
            });
        }
    }

    void drawGallows(int offset_x) {
        this.score.setText(this.lives.substring(offset_x, lives.length()));
        pic.setImage(image);
        pic.setViewport(new Rectangle2D(500 * (offset_x - 1),0,500,500));
    }

    void printCurrentGameMessage(String s) {
        gameMessage.setText(s);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), gameMessage);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.play();
    }

    public void clearTextField() {
        inputText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    inputText.clear();
                }
            }
        });
    }

    public char getInputText() {
        return inputText.getText().charAt(0);
    }

    public void showMenu() {
        GameController mc = new GameController();
        try {
            mc.showMenuWhenEnd();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update() {
        printCurrentGameMessage(wordModel.getGameMessage());
        displayUsefullLetters(wordModel.getInputLetters());
        printMaskedTargetWord(wordModel.getMaskedTargetWord());
        drawGallows(wordModel.getMistakeCount());
        congradulateUser(wordModel.isWordSolved(), wordModel.isGameLost());
    }
}
