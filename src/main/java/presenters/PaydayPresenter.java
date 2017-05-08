package presenters;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.transactions.PaydayTransaction;

public class PaydayPresenter {
	public PaydayPresenter()
	{}
	public PayCheck[] getPayroll(int year, int month, int day)
	{
		Calendar payDate = new GregorianCalendar(year, month, day);
        PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);
        paydayTransaction.execute();

        Set<Integer> employeesIds = PayrollDatabase.globalPayrollDatabase.getAllEmployeeIds();
        PayCheck[] payroll = new PayCheck[employeesIds.toArray().length];
		int index = 0;
		for (Iterator<Integer> it = employeesIds.iterator(); it.hasNext();) {
			int employeeId = it.next();
			payroll[index] = paydayTransaction.getPaycheck(employeeId);
			index++;
		}      
		return payroll;
	}
}
