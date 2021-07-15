package controllers;

import Data.DataStorage;
import Data.databaseUtils;
import ds.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class ResponsiblePeopleController {
    public ListView usersList;
    public ListView responsibleList;
    ObservableList<User> usersSelected;
    ObservableList<User> responsibleSelected;
    ArrayList<User> responsiblePeople;
    DataStorage storage;
    Category category;

    public void setData(DataStorage storage, Category category) throws SQLException, ClassNotFoundException {

        this.storage = storage;
        this.category = category;
        usersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
       responsibleList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        update();
    }


    private void update() throws SQLException, ClassNotFoundException {

        storage.setAllusers(databaseUtils.readUsersfromDB());
        responsiblePeople=databaseUtils.readResponsiblefromDb(category.getName());
        usersList.getItems().clear();
        for (int i = 0; i < storage.getAllusers().size(); i++) {
           if(storage.getAllusers().get(i).getType().toLowerCase().equals("user") && !isResponsible(storage.getAllusers().get(i).getId()) )
            usersList.getItems().add(storage.getAllusers().get(i));
        }

        responsibleList.getItems().clear();
        for (int i = 0; i < responsiblePeople.size(); i++) {
            responsibleList.getItems().add(responsiblePeople.get(i));

        }
    }

      boolean isResponsible(String id){
        for(User person : responsiblePeople){
            if (person.getId().equals(id))
                return true;
        }
        return false;
    }

    public void moveRight(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        for(User user : usersSelected)
        {
            databaseUtils.insertResponsibleUser(user.getId(),category.getName());
        }
        update();
    }

    public void moveLeft(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        for(User user : responsibleSelected)
        {
            databaseUtils.removeResponsibleFromDb(user.getId(),category.getName());
        }
        update();
    }

    public void userSelected(MouseEvent mouseEvent) {
        usersSelected =  usersList.getSelectionModel().getSelectedItems();
    }

    public void responsibleSelected(MouseEvent mouseEvent) {
        responsibleSelected =  responsibleList.getSelectionModel().getSelectedItems();
    }
}
