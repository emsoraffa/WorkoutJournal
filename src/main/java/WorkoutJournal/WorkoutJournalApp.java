package WorkoutJournal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WorkoutJournalApp extends Application {

    public static void main(String[] args) {
        WorkoutJournal.getInstance();
        DataHandler.initialize();
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        Scene loginScene = new Scene(root);

        primaryStage.setScene(loginScene);
        primaryStage.show();

    }
}
