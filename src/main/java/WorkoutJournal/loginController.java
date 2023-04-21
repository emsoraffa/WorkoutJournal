package WorkoutJournal;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController{
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    //Login.fxml
    @FXML
    private Label logInLabel;
    @FXML
    private TextField logInUsernameField;
    @FXML
    private PasswordField logInPasswordField;
    @FXML
    private TextField createUsernameInput;
    @FXML
    private PasswordField createPasswordinput1;
    @FXML
    private PasswordField createPasswordinput2;
    @FXML
    public void initialize() {
    }

    @FXML
    public void onLoginButtonPress(ActionEvent event) throws IOException{
        try{
            if(WorkoutJournal.userData.get(logInUsernameField.getText()).getPassword().equals(logInPasswordField.getText())){
                String username = logInUsernameField.getText();
                
                WorkoutJournal.setActiveSession(username,LocalDate.now());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
                root = loader.load();

                WorkoutJournalController wjController = loader.getController();
                wjController.setTable(WorkoutJournal.getActiveDate());
                wjController.setDatePicker();
                
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }else{
                logInLabel.setText("Password Incorrect");
            }
        }catch(NullPointerException e) {
        
            logInLabel.setText("Username not recognized");
        }
    }

    @FXML
    public void onCreateUserButtonPress(ActionEvent event) throws IOException{
        try{
            if (!WorkoutJournal.userData.containsKey(createUsernameInput.getText())) {
                WorkoutJournal.newUser(createUsernameInput.getText(), createPasswordinput1.getText(), createPasswordinput2.getText());
                
                String username = createUsernameInput.getText();
                WorkoutJournal.setActiveSession(username, LocalDate.now());
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/home.fxml"));
                root = loader.load();

                WorkoutJournalController wjController = loader.getController();
                wjController.setTable(WorkoutJournal.getActiveDate());
                wjController.setDatePicker();
                
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }else{
                logInLabel.setText("Username is already taken");
            }
        }catch (Exception e) {
            logInLabel.setText(e.getLocalizedMessage());
        }
    }
}
