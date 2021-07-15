package webControllers;

import Data.DataStorage;
import Data.databaseUtils;
import com.google.gson.GsonBuilder;
import ds.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
@Controller
public class WebUserController {

    @GetMapping(value ="/user/usersList")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllUsers() throws SQLException, ClassNotFoundException {
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readUsersfromDB());
        return jsonObjectPretty;
    }

    @PostMapping(value ="/user/person",consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE
    }
    )
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String insertPerson(@RequestBody Person person) throws SQLException, ClassNotFoundException {
        databaseUtils.insertUserToDb(person);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readUsersfromDB());
        return jsonObjectPretty;
    }

    @PostMapping(path="user/login", consumes = {
            MediaType.APPLICATION_JSON_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String login(@RequestBody loginUser loginUser) throws SQLException, ClassNotFoundException {
        User user = databaseUtils.getUserbyUserNamefromDB(loginUser.getUsername());

        String error = "Validation error";

        if(user == null){
            return error;
        }
        if(!user.getPassword().equals(user.getPassword())){
            return error;
        }

        return user.getId();
    }

    @PostMapping(value ="/user/company",consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE
    }
    )
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String insertCompany(@RequestBody Company company) throws SQLException, ClassNotFoundException {
        databaseUtils.insertUserToDb(company);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readUsersfromDB());
        return jsonObjectPretty;
    }

    @DeleteMapping (path ="/user/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deletUser(@PathVariable String userId) throws SQLException, ClassNotFoundException {

        databaseUtils.removeFromDb(userId);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readUsersfromDB());
        return jsonObjectPretty;
    }

    @PutMapping(path="/user/person/{userID}", consumes = {
            MediaType.APPLICATION_JSON_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updatePerson(@PathVariable String userID, @RequestBody Person person) throws SQLException, ClassNotFoundException {
        databaseUtils.updateUserInDb(person,userID);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readUsersfromDB());
        return jsonObjectPretty;
    }

    @PutMapping(path="/user/company/{userID}", consumes = {
            MediaType.APPLICATION_JSON_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateCompany(@PathVariable String userID, @RequestBody Company company) throws SQLException, ClassNotFoundException {
        databaseUtils.updateUserInDb(company,userID);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readUsersfromDB());
        return jsonObjectPretty;
    }

}
