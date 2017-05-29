package presenters;
import java.util.Calendar;
import java.util.GregorianCalendar;
import payrollcasestudy.boundaries.*;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.TimeCard;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.*;
public class TimeCardPresenter {
	public TimeCardPresenter()
	{}
	public void addTimeCard(int employeeId, int year, int month, int day, int hours)
	{
		Calendar date = new GregorianCalendar(year, month - 1, day);
        Transaction timeCardTransaction = new AddTimeCardTransaction(date, hours, employeeId);
        timeCardTransaction.execute(PayrollDatabaseOnMemory.globalPayrollDatabase);
	}	
	public TimeCard[] getTimeCardArray(int employeeId)
	{		Employee employee = PayrollDatabaseOnMemory.globalPayrollDatabase.getEmployee(employeeId);
		PaymentClassification paymentClass = employee.getPaymentClassification();
		if(paymentClass instanceof HourlyPaymentClassification)	
			return ((HourlyPaymentClassification) paymentClass).getAllTimeCards();	
		return null;
	}
}
