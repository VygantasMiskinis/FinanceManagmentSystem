package webControllers;

import Data.databaseUtils;
import com.google.gson.GsonBuilder;
import ds.Company;
import ds.Expenditure;
import ds.Income;
import ds.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class WebFinancesController {

    @GetMapping(path ="/finance/incomes/{categoryName}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getIncomes(@PathVariable String categoryName) throws SQLException, ClassNotFoundException {
        ArrayList<Income> allIncomes = new ArrayList<>();
        categoryName=categoryName.replaceAll("_"," ");
        allIncomes=databaseUtils.readIncomesfromDb();

        for(int i=allIncomes.size()-1;i>=0;i--){
            if(!allIncomes.get(i).getParent().equals(categoryName))
                allIncomes.remove(i);
        }

        String jsonObjectPretty = new GsonBuilder().create().toJson(allIncomes);
        return jsonObjectPretty;
    }

    @GetMapping(path ="/finance/expenses/{categoryName}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getExpenses(@PathVariable String categoryName) throws SQLException, ClassNotFoundException {
        ArrayList<Expenditure> allExpenditures = new ArrayList<>();
        categoryName=categoryName.replaceAll("_"," ");
        allExpenditures=databaseUtils.readExpendituresfromDb();
        for(int i=allExpenditures.size()-1;i>=0;i--){
            if(!allExpenditures.get(i).getParent().equals(categoryName))
                allExpenditures.remove(i);
        }

        String jsonObjectPretty = new GsonBuilder().create().toJson(allExpenditures);
        return jsonObjectPretty;
    }

    @PostMapping(value ="/finance/income",consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE
    }
    )
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String insertIncome(@RequestBody Income income) {
        databaseUtils.insertIncomeToDb(income);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readIncomesfromDb());
        return jsonObjectPretty;
    }

    @PostMapping(value ="/finance/expense",consumes = {
            MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.APPLICATION_JSON_VALUE
    }
    )
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String insertExpense(@RequestBody Expenditure expenditure) {
        databaseUtils.insertExpenditureToDb(expenditure);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readExpendituresfromDb());
        return jsonObjectPretty;
    }


    @DeleteMapping (path ="/finance/income/{incomeID}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteIncome(@PathVariable int incomeID) throws SQLException, ClassNotFoundException {

        databaseUtils.removeIncomefromDB(incomeID);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readIncomesfromDb());
        return jsonObjectPretty;
    }

    @DeleteMapping (path ="/finance/expense/{expenseID}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteExpense(@PathVariable int expenseID) throws SQLException, ClassNotFoundException {

        databaseUtils.removeExpensefromDB(expenseID);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readExpendituresfromDb());
        return jsonObjectPretty;
    }


    @PutMapping(path="/finance/expense/{expenseID}", consumes = {
            MediaType.APPLICATION_JSON_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateExpense(@PathVariable int expenseID, @RequestBody Expenditure expenditure) {
        databaseUtils.updateExpenseInDb(expenditure,expenseID);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readExpendituresfromDb());
        return jsonObjectPretty;
    }

    @PutMapping(path="/finance/income/{incomeID}", consumes = {
            MediaType.APPLICATION_JSON_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            })
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateIncome(@PathVariable int incomeID, @RequestBody Income income)  {
        databaseUtils.updateIncomeInDb(income,incomeID);
        String jsonObjectPretty = new GsonBuilder().create().toJson(databaseUtils.readIncomesfromDb());
        return jsonObjectPretty;
    }

}
