package rtfmyoumust.code.gallows;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Launcher extends Application {
    Parent root;
    FXMLLoader loader;
    @Override
    public void start(Stage stage) throws IOException {
        loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(Launcher.class.getResourceAsStream("img/icon.png")));
        stage.setTitle("Gallows");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException, IOException  {
        launch();
    }
}