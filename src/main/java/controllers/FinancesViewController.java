package controllers;

import Data.DataStorage;
import Data.databaseUtils;
import ds.Category;
import ds.Expenditure;
import ds.Income;
import ds.User;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class FinancesViewController {
    public TextField amount;
    public TextArea description;
    public CheckBox expenditureCheck;
    public CheckBox incomeCheck;
    DataStorage storage;
    User user;
    Category category;
    public ListView<Expenditure> expenditureListview;
    public ListView<Income> incomeListview;
    Expenditure selectedExpenditure;
    Income selectedIncome;
    Alert a = new Alert(Alert.AlertType.ERROR);

    public void setData(DataStorage storage, User user, Category category) {

        this.storage = storage;
        this.user = user;
        this.category = category;
        update();
    }


    private void update() {

        expenditureListview.getItems().clear();
        description.clear();
        amount.clear();
        incomeCheck.setSelected(false);
        expenditureCheck.setSelected(false);
        for (int i = 0; i < category.getExpenditures().size(); i++) {
            expenditureListview.getItems().add(category.getExpenditures().get(i));

        }
        incomeListview.getItems().clear();
        for (int i = 0; i < category.getIncome().size(); i++) {
            incomeListview.getItems().add(category.getIncome().get(i));

        }
    }

    
    private void fillExistingUserFieldsWithData() {
        if (selectedExpenditure == null && selectedIncome!=null) {

            description.setText(selectedIncome.getDescription());
            amount.setText(String.valueOf(selectedIncome.getAmount()));
            incomeCheck.setSelected(true);
            expenditureCheck.setSelected(false);

        }

        else if (selectedExpenditure != null && selectedIncome==null){
                description.setText(selectedExpenditure.getDescription());
                amount.setText(String.valueOf(selectedExpenditure.getAmount()));
                incomeCheck.setSelected(false);
                expenditureCheck.setSelected(true);
        }
    }





    private void add(){
        a.setHeaderText("Bad number value");
        a.setContentText("The number you entered: " + amount.getText() +" is not a number");

        try {
            if (description.getText().equals("") || amount.getText().equals("")) {
                a.setHeaderText("one or more fields are empty");
                a.setContentText("Empty fields");
                a.show();
                return;
            }
        }catch (NullPointerException e) {
            a.setHeaderText("empty field(-s)!");
            a.setContentText(e.toString());
            a.show();
            return;
        }
        if (incomeCheck.isSelected() && !expenditureCheck.isSelected()) {


            try{
                category.getIncome().add(new Income(description.getText(),Double.parseDouble(amount.getText()),category.getName()));
                databaseUtils.insertIncomeToDb(new Income(description.getText(),Double.parseDouble(amount.getText()),category.getName()));
            }
            catch (NumberFormatException e) {
                a.show();
                return;
            }

        }


        else if (!incomeCheck.isSelected() && expenditureCheck.isSelected()){
            try{
                category.getExpenditures().add(new Expenditure(description.getText(),Double.parseDouble(amount.getText()),category.getName()));
                databaseUtils.insertExpenditureToDb(new Expenditure(description.getText(),Double.parseDouble(amount.getText()),category.getName()));
            }
            catch (NumberFormatException e) {

                a.show();
                return;
            }

        }
        else  {
            a.setHeaderText("Checkbox error");
            a.setContentText("Either both checkboxes are marked or neither");
            a.show();
        }
        update();
    }

    private void remove(){
        if (selectedIncome!=null && selectedExpenditure==null) {

            category.getIncome().remove(selectedIncome);
            databaseUtils.removeFromDb(selectedIncome);

        }


        else if (selectedIncome==null && selectedExpenditure!=null){

            category.getExpenditures().remove(selectedExpenditure);
            databaseUtils.removeFromDb(selectedExpenditure);
        }


    }

    public void onAdd(ActionEvent actionEvent) {
        add();
        update();
    }

    public void onUpdate(ActionEvent actionEvent) {
        if((incomeCheck.isSelected()&&expenditureCheck.isSelected())||(!incomeCheck.isSelected()&&!expenditureCheck.isSelected())) {
            a.setHeaderText("Checkbox error");
            a.setContentText("both / neither  checkboxes are marked");
            a.show();
            return;
        }
        remove();
        add();
    }

    public void onDelete(ActionEvent actionEvent) {

        remove();
        update();
    }

    public void onEXPselected(MouseEvent mouseEvent) {

        selectedExpenditure = expenditureListview.getSelectionModel().getSelectedItem();
        if (selectedExpenditure == null) return;
        selectedIncome=null;
        fillExistingUserFieldsWithData();

    }

    public void onINCselected(MouseEvent mouseEvent) {
        selectedIncome = incomeListview.getSelectionModel().getSelectedItem();
        if (selectedIncome == null) return;
        selectedExpenditure=null;
        fillExistingUserFieldsWithData();

    }
}
