package rtfmyoumust.code.gallows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends GallowsView{

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Scene preScene;
    @FXML
    Button button_start;
    @FXML
    Button button_exit;

    @FXML
    protected void switchToGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onExitButtonClick() {
        playSound(MENU_EXIT_SOUND);
        mediaPlayer.setOnEndOfMedia(() -> {
            System.exit(0);
        });

    }

    @FXML
    public void playSoundOnStartButton() {
        playSound(MENU_SOUND);
    }

    // esc key closure
    public void closeMenu(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            if (this.preScene != null) {
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(preScene);
                stage.show();
            }
        }
    }

    public void setPrescene(Scene preScene) {
        this.preScene = preScene;
    }

    public void resumeGame(ActionEvent event) {
        if (this.preScene != null) {
            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            // save the previous scene so you can return to it
            stage.setScene(preScene);
            stage.show();
        }
    }
}