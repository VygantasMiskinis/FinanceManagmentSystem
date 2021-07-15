package controllers;

import Data.DataStorage;
import Data.databaseUtils;
import ds.Category;
import ds.Person;
import ds.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class CategoryController {

    public TreeView <Category> categoryTree;
    public TextField categoryName;
    public TextField categoryAuthor;
    public TextField expenditures;
    public TextField incomes;
    public TextArea description;
    Stage mainStage;
    Scene mainScene;
    Alert alert = new Alert(Alert.AlertType.ERROR);
    DataStorage storage;
    User user;
    TreeItem<Category> selectedItem;

    public void setData(Stage mainStage, Scene mainScene, DataStorage storage, User user) {
        this.mainStage = mainStage;
        this.mainScene = mainScene;
        this.storage = storage;
        this.user = user;
       update();
    }

    public void goBack(ActionEvent actionEvent) {
        mainStage.setTitle("Finance Manager");
        mainStage.setScene(mainScene);
    }


    public void load(){
        try {
            storage.setAllCategories(DataStorage.filterForUser(databaseUtils.readCategoriesfromDB(),user.getId()));
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void update() {


        load();

        TreeItem<Category> root;
        root = new TreeItem(new Category("Categories"));
        root.setExpanded(true);
       categoryTree.setRoot(root);

        for(int i = 0; i < storage.getCategories().size(); i++){
            categoryTree.getRoot().getChildren().add(addTreeItem(storage.getCategories().get(i)));
        }
        categoryTree.getSelectionModel().selectFirst();

        categoryTree.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue) -> {
            if(newValue!=null)
              selectedItem=newValue;
        });



    }

    private TreeItem<Category> addTreeItem(Category category){
        TreeItem<Category> treeCategory = new TreeItem<>(category);
        for(int i = 0; i< category.getSubCategories().size(); i++){
            treeCategory.getChildren().add(addTreeItem(category.getSubCategories().get(i)));
        }
        return treeCategory;
    }

    public void onSelect(MouseEvent mouseEvent) {

        if (selectedItem == null || selectedItem.getValue().getAuthor() == null) {

            resetTreeView();
            return;
        }
        fillDataFields();
    }



    private void fillDataFields() {
        if (selectedItem == null) return;
        Category selectedCategory = selectedItem.getValue();

        if (selectedCategory.getAuthor() == null) return;

        expenditures.setText(Double.toString(selectedCategory.calculateTotalExpenditure()));
        incomes.setText(Double.toString(selectedCategory.calculateTotalIncome()));
        categoryName.setText(selectedCategory.getName());
        description.setText(selectedCategory.getDescription());
        categoryAuthor.setText(selectedCategory.getAuthor().getUserName());
    }

    private void resetTreeView() {

        categoryName.clear();
        categoryAuthor.clear();
        expenditures.clear();
        incomes.clear();
        description.clear();
    }



    public void updateClick(ActionEvent actionEvent) {

        if(selectedItem == null || emptyFields()) return;
        String oldName = selectedItem.getValue().getName();

        Category category = storage.findcategory(storage.getCategories(),selectedItem.getValue().getName());
        if(category==null)
            return;

        if(storage.findcategory(storage.getCategories(),categoryName.getText())!=null){
            return;
        }

        storage.deleteCategory(storage.getCategories(),selectedItem.getValue().getName());

        category.setName(categoryName.getText());

        User author = storage.findUserbyName(categoryAuthor.getText());
        if(author!=null)
            category.setAuthor(author);
        else
            category.setAuthor((Person) user);
        category.setDescription(description.getText());



        if(storage.findcategory(storage.getCategories(),selectedItem.getValue().getName())!=null){
            return;

        }else if (category!=null){

            storage.addCategory(category);
            databaseUtils.updateCategoryInDb(oldName,category);
        }
        update();
    }

    public void deleteClick(ActionEvent actionEvent) {
        Category category=selectedItem.getValue();
        if(category!=null)
            storage.deleteCategory(storage.getCategories(),category.getName());
            databaseUtils.removeFromDb(category);
        load();
        update();
    }

    public void addCategory(ActionEvent actionEvent) {
      if(emptyFields()) return;
        Category category = new Category(categoryName.getText());
        User author = user;

        if(author!=null)
            category.setAuthor(author);

        category.setDescription(description.getText());
        storage.getCategories().add(category);
        databaseUtils.insertCategoryToDb(category);
        update();
    }

    public boolean emptyFields(){
        try{ if(categoryName.getText().equals("") || description.getText().equals("")) {
            alert.setHeaderText("empty field(-s)!");
            alert.setContentText("one or more empty fields");
            alert.show();
            return true;}
        }catch (NullPointerException e) {
            alert.setHeaderText("empty field(-s)!");
            alert.setContentText(e.toString());
            alert.show();
            return true;
        }
        return false;
    }

    public void addSubcategory(ActionEvent actionEvent) {
        if(selectedItem == null || storage.findcategory(storage.getCategories(),selectedItem.getValue().getName())==null) return;

      if(emptyFields()) return;

        Category category = new Category(categoryName.getText());
        category.setParentCategory(selectedItem.getValue().getName());
        category.setAuthor(user);
        category.setParentCategory(selectedItem.getValue().getName());
        category.setDescription(description.getText());
        storage.findcategory(storage.getCategories(),selectedItem.getValue().getName()).getSubCategories().add(category);
        databaseUtils.insertCategoryToDb(category);

        update();

    }

    public void manageFinances(ActionEvent actionEvent) {
        if(selectedItem==null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FinancesView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Manage Finances");
        stage.setScene(scene);

        FinancesViewController financesController = loader.getController();
        financesController.setData(storage,user,selectedItem.getValue());
        stage.show();
    }

    public void manageRespPeople(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(selectedItem==null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ResponsiblePeopleView.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle("Manage Responsible people");
        stage.setScene(scene);
        ResponsiblePeopleController responsiblePeopleController = loader.getController();
        responsiblePeopleController.setData(storage,selectedItem.getValue());
        stage.show();
    }
}

