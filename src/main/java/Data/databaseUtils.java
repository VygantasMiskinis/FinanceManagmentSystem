package Data;

import ds.*;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;

public class databaseUtils {



    public static Connection connectionToDb() throws ClassNotFoundException {
       Connection conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String DB_URL = "jdbc:mysql://localhost/financesystem";
        String USER = "root";
        String PASS = "";
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }
    public static void disconnectFromDb(Connection connection, Statement statement) {
        if(connection !=null && statement!=null){
            try {
                statement.close();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    public static void removeFromDb(String userID){

        Connection connection = null;
        Statement statement = null;

        try{
            connection=  connectionToDb();
            statement=connection.createStatement ();
            String deleteUser = "delete from userinfo where id = ?";
            PreparedStatement delete = connection.prepareStatement(deleteUser);
            delete.setString(1,userID);
            delete.execute();
        }catch (Exception e){
           e.printStackTrace();
        }
        disconnectFromDb(connection,statement);
    }
    public static void removeFromDb(Expenditure expenditure){
        Connection connection = null;
        Statement statement = null;

        try{

            connection=  connectionToDb();
            statement=connection.createStatement ();
            String deleteExpense = "delete from expenses where Category = ? and amount = ?";
            PreparedStatement deleteExp = connection.prepareStatement(deleteExpense);
            deleteExp.setString(1,expenditure.getParent());
            deleteExp.setDouble(2,expenditure.getAmount());
            deleteExp.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        disconnectFromDb(connection,statement);
    }
    public static void removeFromDb(Income income) {
        Connection connection = null;
        Statement statement = null;

        try{

            connection=  connectionToDb();
            statement=connection.createStatement ();
            String deleteIncome = "delete from incomes where Category = ? and Amount = ?";
            PreparedStatement deleteInc = connection.prepareStatement(deleteIncome);
            deleteInc.setString(1,income.getParent());
            deleteInc.setDouble(2,income.getAmount());
            deleteInc.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        disconnectFromDb(connection,statement);
    }
    public static void removeFromDb(Category category){

        Connection connection = null;
        Statement statement = null;

        try{
            connection=  connectionToDb();
            statement=connection.createStatement ();

            for(Expenditure expenditure : category.getExpenditures()){

                removeFromDb(expenditure);
            }

            for(Income income : category.getIncome()){

                removeFromDb(income);
            }


            String deleteCategory = "delete from categoryinfo where name = ?";
            PreparedStatement deleteCat = connection.prepareStatement(deleteCategory);
            deleteCat.setString(1,category.getName());
            deleteCat.execute();

            String deleteSubCategory = "delete from categoryinfo where parent = ?";
            PreparedStatement deleteSubCat = connection.prepareStatement(deleteSubCategory);
            deleteSubCat.setString(1,category.getName());
            deleteSubCat.execute();

        }catch (Exception e){
            e.printStackTrace();
        }

        disconnectFromDb(connection,statement);
    }
    public static void removeResponsibleFromDb(String userID,String category){

        Connection connection = null;
        Statement statement = null;

        try{
            connection=  connectionToDb();
            statement=connection.createStatement ();
            String deleteUser = "delete from responsiblepeople where userID = ? and Category = ?";
            PreparedStatement delete = connection.prepareStatement(deleteUser);
            delete.setString(1,userID);
            delete.setString(2,category);
            delete.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        disconnectFromDb(connection,statement);
    }

    public static void updateUserInDb(User user, String oldID){
        Connection connection = null;
        Statement statement = null;

        try{
            connection=  connectionToDb();
            statement=connection.createStatement ();
            if(user.getType().toLowerCase().equals("user")) {
                String insertStringUser = "update userinfo Set id=?,userName=?,password=?,type=?,email=?,name=?,lastname=? where id = ?";
                PreparedStatement insertUser =  connection.prepareStatement(insertStringUser);
                insertUser.setString(1,user.getId());
                insertUser.setString(2,user.getUserName());
                insertUser.setString(3,user.getPassword());
                insertUser.setString(4,user.getType());
                insertUser.setString(5,user.getEmail());
                insertUser.setString(6,user.getFirstName());
                insertUser.setString(7,user.getLastName());

                insertUser.setString(8,oldID);
                try{
                    insertUser.execute();}
                catch (SQLIntegrityConstraintViolationException e){
                    showAlert(e.toString());

                }


            }
            else if(user.getType().toLowerCase().equals("company")) {

                String insertStringCompany = "update userinfo Set id=?,username=?,password=?,type=?,email=?,companyname=?,contactperson=? where id=?";
                PreparedStatement insertCompany = connection.prepareStatement(insertStringCompany);
                insertCompany.setString(1,user.getId());
                insertCompany.setString(2,user.getUserName());
                insertCompany.setString(3,user.getPassword());
                insertCompany.setString(4,user.getType());
                insertCompany.setString(5,user.getEmail());
                insertCompany.setString(6,user.getCompanyName());
                insertCompany.setString(7,user.getContactPerson());
                insertCompany.setString(8,oldID);
                try{
                    insertCompany.execute();}catch (SQLIntegrityConstraintViolationException e){
                    showAlert(e.toString());

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        disconnectFromDb(connection,statement);
    }
    public static void insertExpenditureToDb(Expenditure expenditure){
        Connection connection = null;
        Statement statement = null;

        try{

            connection=  connectionToDb();
            statement=connection.createStatement ();
            String insertStringExpenditure = "insert into expenses (`amount`,`description`,`Category`) VALUES(?,?,?)";
            PreparedStatement insertExp = connection.prepareStatement(insertStringExpenditure);
            insertExp.setDouble(1,expenditure.getAmount());
            insertExp.setString(2,expenditure.getDescription());
            insertExp.setString(3,expenditure.getParent());
            insertExp.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        disconnectFromDb(connection,statement);
    }
    public static void insertIncomeToDb(Income income){
        Connection connection = null;
        Statement statement = null;

        try{

            connection=  connectionToDb();
            statement=connection.createStatement ();
            String insertStringIncome = "insert into incomes (`amount`,`description`,`Category`) VALUES(?,?,?)";
            PreparedStatement insertInc = connection.prepareStatement(insertStringIncome);
            insertInc.setDouble(1,income.getAmount());
            insertInc.setString(2,income.getDescription());
            insertInc.setString(3,income.getParent());
            insertInc.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        disconnectFromDb(connection,statement);
    }

    public static void updateCategoryInDb(String categoryOld, Category categoryNew){
        Connection connection = null;
        Statement statement = null;

        try{
            connection=  connectionToDb();
            statement=connection.createStatement ();

                String updateCategory = "update categoryinfo Set name=?,description=?,authorID=?,parent=?  where name = ?";
                PreparedStatement updateCat =  connection.prepareStatement(updateCategory);
                updateCat.setString(1,categoryNew.getName());
                updateCat.setString(2,categoryNew.getDescription());
                updateCat.setString(3,categoryNew.getAuthor().getId());

                if(categoryNew.getParentCategory().equals(""))
                updateCat.setString(4,categoryNew.getParentCategory());
                else
                    updateCat.setString(4,categoryNew.getParentCategory());
                updateCat.setString(5,categoryOld);
                updateCat.execute();

            for(Expenditure expenditure : categoryNew.getExpenditures()){
                removeFromDb(expenditure);
                expenditure.setParent(categoryNew.getName());
                insertExpenditureToDb(expenditure);
            }

            for(Income income : categoryNew.getIncome()){

               removeFromDb(income);
                income.setParent(categoryNew.getName());
                insertIncomeToDb(income);

            }


            updateResponsiblePeople(categoryOld,categoryNew.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }


        disconnectFromDb(connection,statement);
    }

    private static void updateResponsiblePeople(String categoryOld, String name) {
        Connection connection = null;
        Statement statement = null;

        try{
            connection=  connectionToDb();
            statement=connection.createStatement ();
            String updateResponsible = "update responsiblepeople Set Category=?  where Category=?";
            PreparedStatement updateResp =  connection.prepareStatement(updateResponsible);
            updateResp.setString(1,name);
            updateResp.setString(2,categoryOld);
            updateResp.execute();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnectFromDb(connection,statement);
    }

    public static void updateIncomeInDb(Income income,int ID){
        Connection connection = null;
        Statement statement = null;

        try{
            connection=  connectionToDb();
            statement=connection.createStatement ();
            String updateIncome = "update incomes Set amount=?,description=?,Category=?  where ID=?";
            PreparedStatement updateInc =  connection.prepareStatement(updateIncome);
            updateInc.setDouble(1,income.getAmount());
            updateInc.setString(2,income.getDescription());
            updateInc.setString(3,income.getParent());
            updateInc.setInt(4,ID);
            updateInc.execute();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnectFromDb(connection,statement);
    }
    public static void updateExpenseInDb(Expenditure expenditure,int ID){
        Connection connection = null;
        Statement statement = null;

        try{
            connection=  connectionToDb();
            statement=connection.createStatement ();
            String updateIncome = "update expenses Set amount=?,description=?,Category=?  where ID=?";
            PreparedStatement updateInc =  connection.prepareStatement(updateIncome);
            updateInc.setDouble(1,expenditure.getAmount());
            updateInc.setString(2,expenditure.getDescription());
            updateInc.setString(3,expenditure.getParent());
            updateInc.setInt(4,ID);
            updateInc.execute();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnectFromDb(connection,statement);
    }
    public static ArrayList<Category> readCategoriesfromDB() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Statement statement = null;
        connection = connectionToDb();
        statement = connection.createStatement();

        DataStorage storage = new DataStorage();
        ArrayList<Category> categories = new ArrayList<Category>();
        ArrayList<Expenditure> allExpenses = new ArrayList<Expenditure>();
        ArrayList <Income> allIncomes = new ArrayList<Income>();
        ArrayList<User> allUsers = new ArrayList<User>();
        storage.setAllCategories(categories);
        storage.setAllusers(allUsers);


        storage.setAllusers(readUsersfromDB());

        String catSql= "SELECT * FROM categoryinfo ";
        ResultSet catRs = statement.executeQuery(catSql);

        while(catRs.next()){
            String name = catRs.getString(1);
            String description = catRs.getString(2);
            String authorID = catRs.getString(3);
            String parent = catRs.getString(4);

            User author;
            Category category = new Category(name);
            category.setDescription(description);
            category.setParentCategory(parent);
            author = storage.findUser(authorID);
            if(author!=null)
               category.setAuthor(author);

            storage.getCategories().add(category);

        }

        allExpenses=readExpendituresfromDb();
        allIncomes=readIncomesfromDb();


        for(int i=0;i<allExpenses.size();i++){
            try{
                storage.findcategory(storage.getCategories(),allExpenses.get(i).getParent()).getExpenditures().add(allExpenses.get(i));
            }catch(NullPointerException e) {

            }


        }
        for(int i=0;i<allIncomes.size();i++){
            try{
                storage.findcategory(storage.getCategories(),allIncomes.get(i).getParent()).getIncome().add(allIncomes.get(i));
            }catch(NullPointerException e) {

            }
        }

        for(int i=storage.getCategories().size()-1;i>=0;i--){
            Category category = storage.getCategories().get(i);

            if(!category.getParentCategory().equals("")) {
              try {
                  storage.findcategory(storage.getCategories(),category.getParentCategory()).getSubCategories().add(category);
                  storage.getCategories().remove(i);

              } catch (NullPointerException e) {
                  storage.getCategories().remove(i);
              }

          }
        }


        disconnectFromDb(connection,statement);
        return storage.getCategories();
    }
    public static ArrayList<User> readUsersfromDB() throws ClassNotFoundException, SQLException {

        ArrayList<User> users = new ArrayList<User>();
        Connection connection = null;
        Statement statement = null;
        String id,username,password,type,email,name,lastname,companyname,contactperson;

        User user;
        connection = connectionToDb();
        statement = connection.createStatement();

        String sql = "SELECT * FROM userinfo ";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

          id=rs.getString(1);
            username=rs.getString(2);
            password=rs.getString(3);
            type=rs.getString(4);
            email=rs.getString(5);
            name=rs.getString(6);
            lastname=rs.getString(7);
            companyname=rs.getString(8);
            contactperson=rs.getString(9);

            if(type.toLowerCase().equals("user")){
                user = new Person(id,username,password,type,email,name,lastname);
                users.add(user);
            }
            else if(type.toLowerCase().equals("company")){
                user = new Company(id,username,password,type,email,companyname,contactperson);
                users.add(user);
            }
        }




        disconnectFromDb(connection,statement);
        return users;
    }
    public static User getUserbyUserNamefromDB(String username) throws ClassNotFoundException, SQLException {

       User user = null;
        Connection connection = null;
        Statement statement = null;
        String id;
        String userName;
        String password;
        String type;
        try {
            connection = connectionToDb();
            statement = connection.createStatement();

            String sql = "SELECT id,type,userName,password FROM userinfo where userName=? ";
            PreparedStatement readUser =  connection.prepareStatement(sql);
            readUser.setString(1,username);
           ResultSet rs = readUser.executeQuery();

            while (rs.next()) {
                id = rs.getString(1);
                type = rs.getString(2);
                userName = rs.getString(3);
                password = rs.getString(4);

                if(type.toLowerCase().equals("user")){
                    Person person = new Person();
                    person.setId(id);
                    person.setUserName(userName);
                    person.setPassword(password);
                    person.setType(type);
                    user=person;
                }
               else if(type.toLowerCase().equals("company")){
                    Company company = new Company();
                    company.setId(id);
                    company.setUserName(userName);
                    company.setPassword(password);
                    company.setType(type);
                    user=company;
                }
            }}catch(Error e) {
            e.printStackTrace();
            }

            disconnectFromDb(connection, statement);
            return user;

    }
    public static User getUserbyIDfromDB(String userId) throws ClassNotFoundException, SQLException {

        User user = null;
        Connection connection = null;
        Statement statement = null;
        String id;
        String userName;
        String password;
        String type;
        try {
            connection = connectionToDb();
            statement = connection.createStatement();

            String sql = "SELECT id,type,userName,password FROM userinfo where id=? ";
            PreparedStatement readUser =  connection.prepareStatement(sql);
            readUser.setString(1,userId);
            ResultSet rs = readUser.executeQuery();

            while (rs.next()) {
                id = rs.getString(1);
                type = rs.getString(2);
                userName = rs.getString(3);
                password = rs.getString(4);

                if(type.toLowerCase().equals("user")){
                    Person person = new Person();
                    person.setId(id);
                    person.setUserName(userName);
                    person.setPassword(password);
                    person.setType(type);
                    user=person;
                }
                else if(type.toLowerCase().equals("company")){
                    Company company = new Company();
                    company.setId(id);
                    company.setUserName(userName);
                    company.setPassword(password);
                    company.setType(type);
                    user=company;
                }
            }}catch(Error e) {
            e.printStackTrace();
        }

        disconnectFromDb(connection, statement);
        return user;

    }
    public static ArrayList<User> readResponsiblefromDb(String categoryName){

        ArrayList<User> users = new ArrayList<>();
        DataStorage storage = new DataStorage();
        ArrayList<User> allusers = new ArrayList<User>();

        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionToDb();
            statement = connection.createStatement();

            String sql = "SELECT DISTINCT * FROM responsiblepeople ";
            ResultSet rs = statement.executeQuery(sql);

            allusers=readUsersfromDB();
            storage.setAllusers(allusers);
            while (rs.next()) {
                String id=rs.getString(1);
                String category=rs.getString(2);
                if(category.equals(categoryName)){
                    if(storage.findUser(id)!=null)
                        users.add(storage.findUser(id));
                }
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        disconnectFromDb(connection,statement);
        return users;
    }
    public static ArrayList<Income> readIncomesfromDb(){
        Connection connection = null;
        Statement statement = null;
        ArrayList<Income> allIncomes = new ArrayList<>();
       try {
           connection = connectionToDb();
           statement = connection.createStatement();
           String incSql = "SELECT * FROM incomes";
           ResultSet incRs = statement.executeQuery(incSql);

           while (incRs.next()) {
               double amount = incRs.getDouble(1);
               String description = incRs.getString(2);
               String parent = incRs.getString(3);
               allIncomes.add(new Income(description, amount, parent));
           }
       } catch (SQLException | ClassNotFoundException throwables) {
           throwables.printStackTrace();
       }
       return allIncomes;
    }
    public static ArrayList<Expenditure> readExpendituresfromDb(){
        Connection connection = null;
        Statement statement = null;
        ArrayList<Expenditure> allExpenditures = new ArrayList<>();
        try {
            connection = connectionToDb();
            statement = connection.createStatement();
            String expSql= "SELECT * FROM expenses";
            ResultSet expRs = statement.executeQuery(expSql);

            while(expRs.next()) {
                double amount = expRs.getDouble(1);
                String description = expRs.getString(2);
                String parent = expRs.getString(3);
                allExpenditures.add(new Expenditure(description,amount,parent));
            }
            }
         catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return allExpenditures;
    }
    static void showAlert (String e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("DB error");
        alert.setContentText(e.toString());
        alert.show();
    }

    public static void insertUserToDb(User user) {

        Connection connection = null;
        Statement statement = null;

        try{
            connection=  connectionToDb();
            statement=connection.createStatement ();
            if(user.getType().toLowerCase().equals("user")) {
                String insertStringUser = "insert into userinfo(`id`,`userName`,`password`,`type`,`name`,`lastname`,`email`) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement insertUser =  connection.prepareStatement(insertStringUser);
                insertUser.setString(1,user.getId());
                insertUser.setString(2,user.getUserName());
                insertUser.setString(3,user.getPassword());
                insertUser.setString(4,user.getType());
                insertUser.setString(5,user.getFirstName());
                insertUser.setString(6,user.getLastName());
                insertUser.setString(7,user.getEmail());

                try{
                    insertUser.execute();
                }catch (SQLIntegrityConstraintViolationException e){


                    showAlert(e.toString());


                }


            }
            else if(user.getType().toLowerCase().equals("company")) {
                String insertStringCompany = "insert into userinfo(`id`,`userName`,`password`,`type`,`email`,`companyName`,`contactPerson`) VALUES(?,?,?,?,?,?,?)";
                PreparedStatement insertCompany = connection.prepareStatement(insertStringCompany);
                insertCompany.setString(1,user.getId());
                insertCompany.setString(2,user.getUserName());
                insertCompany.setString(3,user.getPassword());
                insertCompany.setString(4,user.getType());
                insertCompany.setString(5,user.getEmail());
                insertCompany.setString(6,user.getCompanyName());
                insertCompany.setString(7,user.getContactPerson());
                    try{
                        insertCompany.execute();}
                    catch (SQLIntegrityConstraintViolationException e){
                            showAlert(e.toString());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        disconnectFromDb(connection,statement);
    }


    public static void insertCategoryToDb(Category category) {

        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectionToDb();
            statement = connection.createStatement();
            String insertString = "insert into categoryinfo (`name`,`description`,`authorID`,`parent`) VALUES(?,?,?,?)";
            PreparedStatement insert = connection.prepareStatement(insertString);
            insert.setString(1,category.getName());
            insert.setString(2,category.getDescription());
            if(category.getAuthor()!=null)
            insert.setString(3,category.getAuthor().getId());
            else insert.setString(3,"");

            if(category.getParentCategory()!=null)
            insert.setString(4,category.getParentCategory());
            else
                insert.setString(4,"");


            try {
                insert.execute();
            } catch (SQLIntegrityConstraintViolationException e) {
                showAlert(e.toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        insertResponsibleUser(category.getAuthor().getId(),category.getName());
        disconnectFromDb(connection, statement);
    }

    public static void insertResponsibleUser(String id, String category) {
        Connection connection = null;
        Statement statement = null;

        try{

            connection=  connectionToDb();
            statement=connection.createStatement ();
            String insertStringResponsbile = "insert into responsiblepeople (`userid`,`Category`) VALUES(?,?)";
            PreparedStatement insertResp = connection.prepareStatement(insertStringResponsbile);
            insertResp.setString(1,id);
            insertResp.setString(2,category);
            insertResp.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        disconnectFromDb(connection,statement);
    }

    public static void removeIncomefromDB(int ID) {
        Connection connection = null;
        Statement statement = null;

        try{

            connection=  connectionToDb();
            statement=connection.createStatement ();
            String deleteIncome = "delete from incomes where ID =?";
            PreparedStatement deleteInc = connection.prepareStatement(deleteIncome);
            deleteInc.setInt(1,ID);
            deleteInc.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        disconnectFromDb(connection,statement);
    }

    public static void removeExpensefromDB(int ID) {
        Connection connection = null;
        Statement statement = null;

        try{

            connection=  connectionToDb();
            statement=connection.createStatement ();
            String deleteIncome = "delete from expenses where ID =?";
            PreparedStatement deleteInc = connection.prepareStatement(deleteIncome);
            deleteInc.setInt(1,ID);
            deleteInc.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        disconnectFromDb(connection,statement);
    }

}


