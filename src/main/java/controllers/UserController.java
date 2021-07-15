package controllers;

import Data.DataStorage;
import Data.databaseUtils;
import ds.Company;
import ds.Person;
import ds.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;

public class UserController {
    @FXML
    public ListView<User> usersList;
    public Stage mainStage;
    public Scene mainScene;
    public TextField userID;
    public TextField username;
    public TextField email;
    public TextField name;
    public TextField lastname;
    public TextField password;
    public TextField companyName;
    public TextField contactPerson;
    public TextField addUsername;
    public TextField addUserId;
    public TextField addPassword;
    public TextField addName;
    public TextField addLastName;
    public TextField addCompanyName;
    public TextField addContactPerson;
    public TextField addUserEmail;
    public TextField addUserType;


    DataStorage storage;
    User user;
    User selectedUser;


    public void setData(Stage mainStage, Scene mainScene, DataStorage storage, User user) throws SQLException, ClassNotFoundException {
        this.mainStage = mainStage;
        this.mainScene = mainScene;
        this.storage = storage;
        this.user = user;
        load();
    }

    private void load() throws ClassNotFoundException, SQLException {

        storage.setAllusers(databaseUtils.readUsersfromDB());
        usersList.getItems().clear();
        for (int i = 0; i < storage.getAllusers().size(); i++) {
            usersList.getItems().add(storage.getAllusers().get(i));
        }

    }

    public void goBack(ActionEvent actionEvent) {
        mainStage.setTitle("Finance Manager");
        mainStage.setScene(mainScene);
    }
public void resetFields(){
    userID.setText("");
    username.setText("");
    password.setText("");
    userID.setText("");
    name.setText("");
    lastname.setText("");
    email.setText("");
    companyName.setText("");
    contactPerson.setText("");
}
    public void onSelect(MouseEvent mouseEvent) {
        selectedUser = usersList.getSelectionModel().getSelectedItem();
        if (selectedUser == null) return;
        resetFields();
        fillDataFields();
        setElementsActiveStatus(true);
    }

    private void setElementsActiveStatus(boolean active) {
        userID.setDisable(!active);
        username.setDisable(!active);
        password.setDisable(!active);
        userID.setDisable(!active);
        name.setDisable(!active);
        lastname.setDisable(!active);
        email.setDisable(!active);
        companyName.setDisable(!active);

    }

    private void fillDataFields() {
        if (selectedUser == null) return;

        userID.setText(selectedUser.getId());
        username.setText(selectedUser.getUserName());
        password.setText(selectedUser.getPassword());
        name.setText(selectedUser.getFirstName());
        lastname.setText(selectedUser.getLastName());
        email.setText(selectedUser.getEmail());
        if (selectedUser.getType().toLowerCase().equals("company")) {
            companyName.setText(selectedUser.getCompanyName());
            contactPerson.setText(selectedUser.getContactPerson());
        }

    }


    public void deleteUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (selectedUser != null) {
            if (storage.findUser(selectedUser.getId()) != null) {
                databaseUtils.removeFromDb(selectedUser.getId());
            }
        } else {

        }
        load();
    }

    public void updateUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {




        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (selectedUser != null) {


            if (selectedUser.getType().toLowerCase().equals("user")) {
                //individual
                if (userID.getText().equals("") || username.getText().equals("") || password.getText().equals("") || name.getText().equals("") || lastname.getText().equals("") || email.getText().equals("")) {
                    alert.setTitle("Alert!");
                    alert.setContentText("One or more fields were not filled");
                    return;
                }
                if (storage.findUser(selectedUser.getId()) != null) {

                   databaseUtils.updateUserInDb(new Person(userID.getText(), username.getText(), password.getText(), "User", email.getText(), name.getText(), lastname.getText()),selectedUser.getId());
                }
            } else if(selectedUser.getType().toLowerCase().equals("company")) {
                //company
                if (companyName.getText().equals("") || userID.getText().equals("") || username.getText().equals("") || password.getText().equals("") || contactPerson.getText().equals("") || email.getText().equals("")) {
                    alert.setTitle("Alert!");
                    alert.setContentText("One or more fields were not filled");
                    alert.show();
                    return;
                }
                if (storage.findUser(selectedUser.getId()) != null) {
                  ;
                    databaseUtils.updateUserInDb(new Company(userID.getText(), username.getText(), password.getText(), "Company", email.getText(), companyName.getText(), contactPerson.getText()),selectedUser.getId());
                }
            }

            load();
        }


    }

    public void addNewUser(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (addUserType.getText().toLowerCase().equals("user")) {
            if (addUserId.getText().equals("") || addUsername.getText().equals("") || addPassword.getText().equals("") || addName.getText().equals("") || addLastName.getText().equals("") || addUserEmail.getText().equals("")) {

                alert.setTitle("Alert!");
                alert.setContentText("One or more fields were not filled");
                alert.showAndWait();

                return;
            }
            databaseUtils.insertUserToDb(new Person(addUserId.getText(), addUsername.getText(), addPassword.getText(), "User", addUserEmail.getText(), addName.getText(), addLastName.getText()));

        }

        if (addUserType.getText().toLowerCase().equals("company")) {
            if (addCompanyName.getText().equals("") || addUserId.getText().equals("") || addUsername.getText().equals("") || addPassword.getText().equals("") || addCompanyName.getText().equals("") || addContactPerson.getText().equals("") || addUserEmail.getText().equals("")) {

                alert.setTitle("Alert!");
                alert.setContentText("One or more fields were not filled");
                alert.show();
                return;
            }
            databaseUtils.insertUserToDb(new Company(addUserId.getText(), addUsername.getText(), addPassword.getText(), "Company", addUserEmail.getText(), addCompanyName.getText(), addContactPerson.getText()));

        }

        load();
    }








}
