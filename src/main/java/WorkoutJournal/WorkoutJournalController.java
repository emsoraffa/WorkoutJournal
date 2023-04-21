package WorkoutJournal;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class WorkoutJournalController{
    private Stage stage;
    private Scene scene;
    private Parent root;

    //All fxml elements for home.fxml
    @FXML
    private DatePicker datePicker = new DatePicker();
    @FXML
    private TableView<Exercise> exerciseTableView = new TableView<>();
    @FXML
    private TableColumn<Exercise, String> name = new TableColumn<>();
    @FXML
    private TableColumn<Exercise, Integer> sets = new TableColumn<>();
    @FXML
    private TableColumn<Exercise, Integer> reps = new TableColumn<>();
    @FXML
    private TableColumn<Exercise, Integer> weight = new TableColumn<>();
    @FXML
    private TableColumn<Exercise, Character> load = new TableColumn<>();

    //Initialising of home.fxml, loads in the tableview
    @FXML
    public void initialize() {
        exerciseTableView.setPlaceholder(
        new Label("No exercises added for this date yet"));

        name.setCellValueFactory(
            new PropertyValueFactory<Exercise, String>("name"));

        sets.setCellValueFactory(
            new PropertyValueFactory<Exercise, Integer>("sets"));

        reps.setCellValueFactory(
            new PropertyValueFactory<Exercise, Integer>("reps"));

        weight.setCellValueFactory(
            new PropertyValueFactory<Exercise, Integer>("weight"));

        load.setCellValueFactory(
            new PropertyValueFactory<Exercise, Character>("load"));

        //Makes it possible to select several lines in the TableView
        exerciseTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        exerciseTableView.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            Node node = event.getPickResult().getIntersectedNode();
        
            // go up from the target node until a row is found or it's clear the
            // target node wasn't a node.
            while (node != null && node != exerciseTableView && !(node instanceof TableRow)) {
                node = node.getParent();
            }
        
            if (node instanceof TableRow) {
                event.consume();
                
                @SuppressWarnings("unchecked") 
                TableRow<Exercise> row = (TableRow<Exercise>) node;
                TableView<Exercise> tv = row.getTableView();
        
                // focus the tableview
                tv.requestFocus();
        
                if (!row.isEmpty()) {
                    // handle selection for non-empty nodes
                    int index = row.getIndex();
                    if (row.isSelected()) {
                        tv.getSelectionModel().clearSelection(index);
                    } else {
                        tv.getSelectionModel().select(index);
                    }
                }
            }
        });
    }

    //Logic for the button "add Exercise" opens addExercise.fxml
    @FXML
    public void onAddExerciseButtonPress(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("fxml/addExercise.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    //Method to update the tableview with the correct data
    public void setTable(LocalDate date) {
        exerciseTableView.getItems().clear();
        exerciseTableView.refresh();

        //Checks if there exists a instance of a WorkoutSession for the given date. If not, creates one.
        if(!WorkoutJournal.getActiveUser().getSessions().isEmpty()){
            WorkoutJournal.getActiveUser().getSessions().get(date.toString()).getWorkout().stream().forEach(a -> exerciseTableView.getItems().add(a));
        }
        exerciseTableView.refresh();
    }
    
    //Logic for the "logout" button and the corresponding pop-up. The button opens login.fxml
    @FXML
    private void onLogoutButtonPress(ActionEvent event)  throws IOException {
        
        //The popup:
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to log out");
        alert.setContentText("Do you want to log out");
       
        //if the popup is accepted, opens login.fxml
        if(alert.showAndWait().get() == ButtonType.OK){
            root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    //Method that generates two random Exercises and adds to the WorkoutSession.
    @FXML
    private void onGenerateExercisesButtonPress(ActionEvent event) {
            WorkoutJournal.generateExercises();
            setTable(WorkoutJournal.getActiveDate());

            Alert generateExercisealert = new Alert(AlertType.INFORMATION);
            generateExercisealert.setTitle("Information");
            generateExercisealert.setHeaderText("Session Generated");
            generateExercisealert.setContentText("Choose weight according to own strength");
            generateExercisealert.show();
        }
    

    //Method that handles the datepicker.
    @FXML
    private void handleDateSelected(ActionEvent event) {
        WorkoutJournal.setActiveSession(WorkoutJournal.getActiveUser().getName(), datePicker.getValue());
        

        //Checks if there exists a instance of a WorkoutSession for the given date. If not, creates one.
        if(WorkoutJournal.getActiveUser().getSessions().get(WorkoutJournal.getActiveDate().toString()) == null) {
            WorkoutJournal.newSession(WorkoutJournal.getActiveUser(), WorkoutJournal.getActiveDate().toString());
        }
        
        setTable(WorkoutJournal.getActiveDate());
    }

    public void setDatePicker(){
        datePicker.setValue(WorkoutJournal.getActiveDate());
    }

    @FXML
    public void onRemoveExerciseButtonPress(ActionEvent event){
        exerciseTableView.getSelectionModel().getSelectedItems().stream().forEach(e -> WorkoutJournal.deleteExercises(e));
        setTable(WorkoutJournal.getActiveDate());
    }
}
