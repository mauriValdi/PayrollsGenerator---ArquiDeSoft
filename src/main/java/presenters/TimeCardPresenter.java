package presenters;
import java.util.Calendar;
import java.util.GregorianCalendar;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.*;

public class TimeCardPresenter {
	public TimeCardPresenter()
	{}
	public void addTimeCard(int employeeId, int year, int month, int day, int hours)
	{
		Calendar date = new GregorianCalendar(year, month - 1, day);
        Transaction timeCardTransaction = new AddTimeCardTransaction(date, hours, employeeId);
        timeCardTransaction.execute();
	}
}
