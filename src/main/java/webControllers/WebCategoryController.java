package webControllers;

import Data.DataStorage;
import Data.databaseUtils;
import com.google.gson.GsonBuilder;
import ds.Category;
import ds.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class WebCategoryController {

    @GetMapping(value ="/category/categoryList")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllCategories() throws SQLException, ClassNotFoundException {

        ArrayList<Category> allCategories = databaseUtils.readCategoriesfromDB();
        String jsonObjectPretty = new GsonBuilder().create().toJson(allCategories);
        return jsonObjectPretty;
    }
    @GetMapping(value ="/category/categoryList/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUsersCategory(@PathVariable String userId) throws SQLException, ClassNotFoundException {

        ArrayList<Category> allCategories = databaseUtils.readCategoriesfromDB();
        ArrayList<Category> resultCategories = new ArrayList<>();
        User respUser = databaseUtils.getUserbyIDfromDB(userId);

        for(Category category: allCategories){
           category.setResponsibleUsers(databaseUtils.readResponsiblefromDb(category.getName()));
            for(User user : category.getResponsibleUsers()){
                if (user.getId().equals(respUser.getId())&&user.getId()!=null){
                    category.setResponsibleUsers(null);
                    resultCategories.add(category);
                    continue;
                }
            }
        }
        String jsonObjectPretty = new GsonBuilder().create().toJson(resultCategories);
        return jsonObjectPretty;
    }

    @GetMapping(value ="/category/categoryList/sub/{parentCategory}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getSubcategories(@PathVariable String parentCategory) throws SQLException, ClassNotFoundException {
        parentCategory=parentCategory.replaceAll("_"," ");
        ArrayList<Category> allCategories = databaseUtils.readCategoriesfromDB();
        ArrayList<Category> resultCategories = new ArrayList<>();
        Category parentCat = DataStorage.findcategory(allCategories,parentCategory);
        if(parentCat!=null)
        resultCategories = parentCat.getSubCategories();
        String jsonObjectPretty = new GsonBuilder().create().toJson(resultCategories);
        return jsonObjectPretty;
    }



    @PostMapping(value ="/category/",consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE
    }
            )
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String insertCategory(@RequestBody Category insertCategory) throws SQLException, ClassNotFoundException {
        databaseUtils.insertCategoryToDb(insertCategory);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readCategoriesfromDB());
        return jsonObjectPretty;
    }

    @DeleteMapping (path ="/category/{categoryName}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deletCategory(@PathVariable String categoryName) throws SQLException, ClassNotFoundException {
        categoryName=categoryName.replaceAll("_"," ");
        DataStorage storage = new DataStorage();
        storage.setAllCategories(databaseUtils.readCategoriesfromDB());
        databaseUtils.removeFromDb(storage.findcategory(storage.getCategories(),categoryName));
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readCategoriesfromDB());
        return jsonObjectPretty;
    }

    @PutMapping(path="/category/{categoryName}", consumes = {
            MediaType.APPLICATION_JSON_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateCategory(@PathVariable String categoryName, @RequestBody Category updatedCategory) throws SQLException, ClassNotFoundException {
        DataStorage storage=new DataStorage();
        categoryName=categoryName.replaceAll("_"," ");
        storage.setAllCategories(databaseUtils.readCategoriesfromDB());
        databaseUtils.updateCategoryInDb(categoryName,updatedCategory);
        String jsonObjectPretty = new GsonBuilder().create().toJson(updatedCategory);
        return jsonObjectPretty;
    }

    @GetMapping(path ="/category/{categoryName}/responsiblePeople")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getResponsible(@PathVariable String categoryName) throws SQLException, ClassNotFoundException {
        categoryName=categoryName.replaceAll("_"," ");
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readResponsiblefromDb(categoryName));
        return jsonObjectPretty;
    }

    @DeleteMapping (path ="/category/{categoryName}/{userID}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteResponsible(@PathVariable String categoryName,@PathVariable String userID) throws SQLException, ClassNotFoundException {
        categoryName=categoryName.replaceAll("_"," ");
        databaseUtils.removeResponsibleFromDb(userID,categoryName);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readResponsiblefromDb(categoryName));
        return jsonObjectPretty;
    }

    @PostMapping(path ="/category/{categoryName}/{userID}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String insertResponsiblePerson(@PathVariable String categoryName,@PathVariable String userID) throws SQLException, ClassNotFoundException {
        categoryName=categoryName.replaceAll("_"," ");
        databaseUtils.insertResponsibleUser(userID,categoryName);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readResponsiblefromDb(categoryName));
        return jsonObjectPretty;
    }
}
