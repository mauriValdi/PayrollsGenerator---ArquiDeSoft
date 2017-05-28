package payrollcasestudy.entities.paymentclassifications;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.SalesReceipt;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CommissionedPaymentClassification extends PaymentClassification {
    private Map<Calendar, SalesReceipt> salesReceiptMap = new HashMap<Calendar, SalesReceipt>();
    private double commissionRate;
    private double monthlySalary;

    public CommissionedPaymentClassification(double monthlySalary, double commissionRate) {
        this.commissionRate = commissionRate;
        this.monthlySalary = monthlySalary;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public SalesReceipt getSalesReceipt(Calendar date) {
        return salesReceiptMap.get(date);
    }

    public void addSalesReceipt(SalesReceipt salesReceipt) {
        salesReceiptMap.put(salesReceipt.getDate(), salesReceipt);
    }
    
    public SalesReceipt[] getAllSalesReceipts()
    {
    	SalesReceipt[] salesReceiptArray = new SalesReceipt[salesReceiptMap.size()];
    	int index = 0;
    	for (Entry<Calendar, SalesReceipt> mapEntry : salesReceiptMap.entrySet()) {    	    
    		salesReceiptArray[index] = mapEntry.getValue();
    	    index++;
    	}
    	return salesReceiptArray;
    }

    @Override
    public double calculatePay(PayCheck payCheck) {
        double  totalPay = monthlySalary;
        for (SalesReceipt receipt: salesReceiptMap.values()){
            if (isInPayPeriod(receipt.getDate(), payCheck)){
                totalPay += receipt.getAmount() * commissionRate;
            }
        }
        return totalPay;
    }
}
