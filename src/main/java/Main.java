import Data.DataStorage;
import Data.databaseUtils;
import controllers.LoginController;
import ds.Category;
import ds.Person;
import ds.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;
import controllers.MainController;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        DataStorage storage = new DataStorage();
       User user;
        //admin default user
        user = new Person("0", "admin", "password", "User", "admin@finance.com", "adminName", "adminLastName");




        storage.setAllCategories(databaseUtils.readCategoriesfromDB());
        storage.setAllusers(databaseUtils.readUsersfromDB());
        if (storage==null)
       // if the storage is empty
        {
            //test user
            storage.addUser(user);
            //test category
            Category category;
            Category category2;
            Category category3;
            category = new Category("testcategory");
            category2 = new Category("testcategory2");
            category3 = new Category("testcategory3");
            category.setAuthor(user);
            category2.setAuthor(user);
            category3.setAuthor(user);

            ArrayList<Category> testSubCats = new ArrayList<Category>();
            ArrayList<Category> testSub2Cats = new ArrayList<Category>();

            testSubCats.add(new Category("test sub category"));
            testSub2Cats.add(new Category("test sub 2nd lvl category"));

            testSubCats.get(0).setSubCategories(testSub2Cats);

            category.setSubCategories(testSubCats);
            storage.getCategories().add(category);
        }

        else if(storage.findUser("0")==null){
            storage.addUser(user);
            databaseUtils.insertUserToDb(user);
        }
        User logedInUser = null;




            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("controllers/Login.fxml"));
            Parent login;
            login = loginLoader.load();

            Scene loginScene = new Scene(login);
            Stage loginStage = new Stage();
            loginStage.setTitle("Login");
            loginStage.setScene(loginScene);

            LoginController loginController = loginLoader.getController();
            logedInUser=loginController.loginPage(loginStage, loginController);



        if(logedInUser!=null)
        user=logedInUser;

        if(!user.getUserName().equals("admin")){
            storage.setAllCategories(DataStorage.filterForUser(storage.getCategories(),user.getId()));
        }

        System.out.println(user.getUserName());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("controllers/Main.fxml"));
        Parent root;
        root=loader.load();

        Scene mainScene = new Scene(root);

        primaryStage.setTitle("Finance manager");
        primaryStage.setScene(mainScene);

        MainController mainController = loader.getController();
        mainController.setData(primaryStage,mainScene, storage, user);

        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);


    }


}




