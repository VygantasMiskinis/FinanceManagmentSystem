package controllers;

import Data.DataStorage;
import ds.Person;
import ds.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {

    public Button choiseUser;
    public Button choiseCategories;

    Scene userScene, categoryScene;
    CategoryController categoryController;
    UserController userController;
    Stage mainStage;
    User user;

    public void setData(Stage mainStage, Scene mainScene, DataStorage storage,User user) throws SQLException, ClassNotFoundException {
    this.user=user;
    this.mainStage=mainStage;
    userSceneSetup(mainStage,mainScene,storage,user);
    categorySceneSetup(mainStage,mainScene,storage,user);
    }

    public void userSceneSetup(Stage mainstage, Scene mainscene,DataStorage storage,User user) throws SQLException, ClassNotFoundException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserControlView.fxml"));
        try {
            userScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userController = loader.getController();
        userController.setData(mainstage,mainscene,storage,user);

    }

    public void categorySceneSetup(Stage mainstage, Scene mainscene,DataStorage storage,User user){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("CategoryControlView.fxml"));
        try {
            categoryScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoryController = loader.getController();
        categoryController.setData(mainstage,mainscene,storage,user);

    }



    public void toUserControler(ActionEvent actionEvent) {

        mainStage.setTitle("User control");
        mainStage.setScene(userScene);

    }

    public void toCategoryController(ActionEvent actionEvent) {
        mainStage.setTitle("Category control");
        mainStage.setScene(categoryScene);
    }





}
