package ds;

import javax.management.Descriptor;
import java.io.Serializable;

public class Expenditure implements Serializable {
    String description;
    double amount;
    String parent;

    public Expenditure(String description, double amount,String parent) {
        this.description = description;
        this.amount = amount;
        this.parent = parent;
    }
    @Override
    public String toString() {
        return amount + " " + description;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double count) {
        this.amount = amount;
    }

    public String getParent() {
        return parent;
    }
    public void setParent(String parent){
        this.parent=parent;
    }
}
