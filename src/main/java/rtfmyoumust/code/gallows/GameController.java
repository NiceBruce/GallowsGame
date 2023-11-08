package rtfmyoumust.code.gallows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController extends GallowsView implements ControllerInterface {

    @FXML
    AnchorPane game;

    @FXML
    Button button_restart;

    @FXML
    Label targetWord;

    @FXML
    Button button_start;
    @FXML
    Button button_exit;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize() throws Exception {
        wordModel.initialize();
    }

    @Override
    public void setLetter() {
        wordModel.openLetters(getInputText());
    }

    public void clear() {
        clearTextField();
    }

    @FXML
    @Override
    public void restartGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Game.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start() {

    }

    @Override
    public void openMenu(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                    scene = new Scene(fxmlLoader.load());
                    MenuController controller = fxmlLoader.getController();
                    controller.setPrescene(((Node)keyEvent.getSource()).getScene());
                    Button reStart = (Button) scene.lookup("#button_restart");
                    reStart.setVisible(true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                this.stage = (Stage)((Node)keyEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
    }

    public void showMenuWhenEnd() throws IOException {
        root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
