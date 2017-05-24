package presenters;
import java.util.Calendar;
import java.util.GregorianCalendar;

import payrollcasestudy.boundaries.PayrollDatabaseOnMemory;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.*;

public class SaleReceiptPresenter {
	public SaleReceiptPresenter()
	{}
	public void addSaleReceipt(int employeeId, int year, int month, int day, int amount)
	{
		Calendar date = new GregorianCalendar(year, month, day);
        Transaction salesReceiptTransaction = new AddSalesReceiptTransaction(date, amount, employeeId);
        salesReceiptTransaction.execute(PayrollDatabaseOnMemory.globalPayrollDatabase);
	}
}
