package presenters;
import java.util.Calendar;
import java.util.GregorianCalendar;import java.util.HashMap;import java.util.Iterator;import java.util.Map;import java.util.Set;import payrollcasestudy.entities.PayCheck;import payrollcasestudy.entities.TimeCard;import payrollcasestudy.transactions.PaydayTransaction;import payrollcasestudy.boundaries.*;
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
	}		  public Map<Integer, PayCheck> getAllPayChecks(int year, int month, int day)	  	  {	 	    Calendar payDate = new GregorianCalendar(year, month - 1, day);	         PaydayTransaction paydayTransaction = new PaydayTransaction(payDate);	         paydayTransaction.execute(PayrollDatabaseOnMemory.globalPayrollDatabase);	         Set<Integer> employeesIds = PayrollDatabaseOnMemory.globalPayrollDatabase.getAllEmployeeIds();        Map<Integer, PayCheck> payroll = new HashMap<Integer, PayCheck>();        	 	    int index = 0;	 	    for (Iterator<Integer> it = employeesIds.iterator(); it.hasNext();) {	 	      int employeeId = it.next();	 	      payroll.put(employeeId, paydayTransaction.getPaycheck(employeeId));	 	      index++;	 	    }  	    return payroll;	 	  }	 
}
