package Data;

import ds.Category;
import ds.User;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    ArrayList<User> allusers = new ArrayList<>();
    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<User> getAllusers() {
        return allusers;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void addUser(User user) {
        allusers.add(user);
    }

    public User findUser(String userId) {
        for (int i = 0; i < getAllusers().size(); i++) {
            if (getAllusers().get(i).getId().equals(userId))
                return getAllusers().get(i);
        }
        return null;
    }
    public User findUserbyName(String name) {
        for (int i = 0; i < getAllusers().size(); i++) {
            if (getAllusers().get(i).getUserName().equals(name))
                return getAllusers().get(i);
        }
        return null;
    }

    public static Category findcategory(ArrayList<Category> searchList, String categoryName) {
        Category result = null;
        for (Category category : searchList) {
            if (category.getName().equals(categoryName)) {
                result = category;
            } else if (category.getSubCategories().size() > 0) {
                if (findcategory(category.getSubCategories(), categoryName) != null)
                    result = findcategory(category.getSubCategories(), categoryName);
            }
        }

        return result;
    }

    public static ArrayList<Category> filterForUser(ArrayList<Category> catList,String userId){
        ArrayList<Category> resultCategories= new ArrayList<>();
        if(userId.equals("0"))
            return catList;
        for(Category category: catList){
            category.setResponsibleUsers(databaseUtils.readResponsiblefromDb(category.getName()));

            if(category.getSubCategories().size()>0){
                category.setSubCategories(filterForUser(category.getSubCategories(),userId));
                if(category.getSubCategories().size()>0){
                    resultCategories.add(category);
                    continue;
                }
            }
            for(User user : category.getResponsibleUsers()){
                if (user.getId().equals(userId)&&user.getId()!=null){
                    resultCategories.add(category);
                    break;
                }
            }

        }

        return resultCategories;
    }


    public void deleteCategory(ArrayList<Category> searchList, String categoryName) {

        for (Category category : searchList) {
            if (category.getName() == categoryName) {
                categories.remove(category);
                return;
            } else if (category.getSubCategories().size() > 0) {
                if (findcategory(category.getSubCategories(), categoryName) != null)
                    deleteCategory(category.getSubCategories(),categoryName);
            }
        }


    }

    public void addCategory(Category category) {
        if(category==null) return;
        else if(category.getParentCategory().equals(""))
            categories.add(category);

        else
            findcategory(categories,category.getName()).getSubCategories().add(category);
    }

    public void setAllusers(ArrayList<User> users) {
        this.allusers=users;
    }
    public void setAllCategories(ArrayList<Category> categories) {this.categories=categories;}
}

