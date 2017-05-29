package presenters;

import java.util.Calendar;
import java.util.GregorianCalendar;
import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.SalesReceipt;
import payrollcasestudy.entities.paymentclassifications.CommissionedPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.*;

public class SaleReceiptPresenter {
	public SaleReceiptPresenter()
	{}
	public void addSaleReceipt(int employeeId, int year, int month, int day, int amount)
	{
		Calendar date = new GregorianCalendar(year, month - 1, day);
        Transaction salesReceiptTransaction = new AddSalesReceiptTransaction(date, amount, employeeId);
        salesReceiptTransaction.execute(PayrollDatabaseOnMemory.globalPayrollDatabase);
	}
	
	public SalesReceipt[] getSalesReceiptArray(int employeeId)
	{
		Employee employee = PayrollDatabase.globalPayrollDatabase.getEmployee(employeeId);
		PaymentClassification paymentClass = employee.getPaymentClassification();
		if(paymentClass instanceof CommissionedPaymentClassification)		
			return ((CommissionedPaymentClassification) paymentClass).getAllSalesReceipts();		
		return null;
	}	
}
