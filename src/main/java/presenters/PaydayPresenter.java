package presenters;
import java.util.Calendar;
import java.util.GregorianCalendar;
import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.transactions.PaydayTransaction;import payrollcasestudy.boundaries.*;
public class PaydayPresenter {
	public PaydayPresenter()
	{}
	public PayCheck getPayroll(int year, int month, int day, int employeeId)
	{
		Calendar payDate = new GregorianCalendar(year, month - 1, day);
        PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
        paydayTransaction.execute(PayrollDatabaseOnMemory.globalPayrollDatabase);
        PayCheck payCheck = paydayTransaction.getPaycheck(employeeId);   
		return payCheck;
	}
}
