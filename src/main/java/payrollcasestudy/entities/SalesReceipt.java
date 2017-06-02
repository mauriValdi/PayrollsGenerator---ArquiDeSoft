package payrollcasestudy.entities;

import java.util.Calendar;
import java.util.Date;

public class SalesReceipt {
    private Calendar date;
    private double amount;

    public SalesReceipt(Calendar date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public Calendar getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
    
    public Date getDateFormat(){
    	return date.getTime();
    }
}
