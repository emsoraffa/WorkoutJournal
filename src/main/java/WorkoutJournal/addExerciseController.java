package WorkoutJournal;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addExerciseController{
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    //addEcercise.fxml
    @FXML
    private Label exerciseLabel;
    @FXML
    private TextField exerciseNameField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField setsField;
    @FXML
    private TextField repsField;
    @FXML
    private ChoiceBox<String> loadChoiceBox = new ChoiceBox<>();
    ObservableList<String> loadList = FXCollections.observableArrayList("L", "M", "H");

    @FXML
    public void initialize() {
        loadChoiceBox.setItems(loadList);
        loadChoiceBox.setValue("Load");
    }
    
    @FXML
    public void addExercise(ActionEvent event) throws IOException {
        try{
            String exercisename = exerciseNameField.getText();
            int sets = Integer.parseInt(setsField.getText());
            int reps = Integer.parseInt(repsField.getText());
            int weight = Integer.parseInt(weightField.getText());
            char load = loadChoiceBox.getValue().charAt(0);

            Exercise tmpExercise = new Exercise(exercisename,sets ,reps, weight, load, WorkoutJournal.getActiveUser().getSessions().get(WorkoutJournal.getActiveDate().toString()));
        
            WorkoutJournal.newExercise(tmpExercise, WorkoutJournal.getActiveUser().getSessions().get(WorkoutJournal.getActiveDate().toString()));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
            root = loader.load();

            WorkoutJournalController wjController = loader.getController();
            wjController.setTable(WorkoutJournal.getActiveDate());
            wjController.setDatePicker();
                
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        
        }catch(Exception e){
            exerciseLabel.setText("Ummh, you inputted something wrong :(");
        }   
    }
    @FXML
    public void onBackButtonPress(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
        root = loader.load();

        WorkoutJournalController wjController = loader.getController();
        wjController.setTable(WorkoutJournal.getActiveDate());
        wjController.setDatePicker();
            
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();  
    }
}
