package ds;

import java.io.*;
import java.util.ArrayList;

public class Category implements Serializable {

    private String name = "default";
    private String description;
    private User author = null;
    private ArrayList<User> responsibleUsers = new ArrayList<>();
    private ArrayList<Category> subCategories = new ArrayList<>();
    private String parentCategory;
    private ArrayList<Income> income = new ArrayList<>();
    private ArrayList<Expenditure> expenditures = new ArrayList<>();

    public Category(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<User> getResponsibleUsers() {
        return responsibleUsers;
    }
    public void setResponsibleUsers(ArrayList<User> responsibleUsers) {
        this.responsibleUsers = responsibleUsers;
    }

    public ArrayList<Category> getSubCategories() {
        return subCategories;
    }
    public void setSubCategories(ArrayList<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public String getParentCategory() {
        return parentCategory;
    }
    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }

    public User getAuthor() { return author ; }
    public void setAuthor(User author) { this.author = author;}

    public ArrayList<Income> getIncome() { return income; }
    public void setIncome(ArrayList<Income> income) { this.income = income; }

    public ArrayList<Expenditure> getExpenditures() { return expenditures; }
    public void setExpenditures(ArrayList<Expenditure> expenditures) { this.expenditures = expenditures; }

    @Override
    public String toString() {
        return this.getName();
    }

    public double calculateTotalIncome() {

        double sum = 0.0;
        for (Income income : income) {
            sum += income.amount;
        }
        return sum;
    }
    public double calculateTotalExpenditure() {

        double sum = 0.0;
        for (Expenditure expenditure : expenditures) {
            sum += expenditure.amount;
        }
        return sum;
    }

    public double getProfit() {
        return calculateTotalIncome() - calculateTotalExpenditure();
    }

}



