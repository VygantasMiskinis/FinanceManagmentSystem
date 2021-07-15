package controllers;

import Data.DataStorage;
import Data.databaseUtils;
import ds.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField username = new TextField();
    @FXML
    private PasswordField password = new PasswordField();
    private Alert a = new Alert(Alert.AlertType.ERROR);
    private Stage stage = new Stage();
    private DataStorage datastorage;
    private LoginController controllerForLogin;
    private User loggedInUser = null;
    private Stage mainStage;
    public User loginPage(Stage mainStage, LoginController controllerForLogin){

        this.controllerForLogin = controllerForLogin;
        this.mainStage=mainStage;

        mainStage.showAndWait();

        return loggedInUser;
    }

    public void login() {
        try{
            loggedInUser = databaseUtils.getUserbyUserNamefromDB(username.getText());
        }
        catch (Exception e){}
        if(loggedInUser != null){
            if(loggedInUser.getPassword().equals(password.getText())){

                a.setHeaderText("Succes!");
                a.setContentText("Logging in");
                a.show();

            mainStage.close();
            }
            else {
                a.setHeaderText("Fail");
                a.setContentText("Wrong password");
                a.show();
            }
        }

        else{
            a.setHeaderText("Wrong input");
            a.setContentText("Fields are empty or incorrect, try again!");
            a.show();
        }
    }
}
