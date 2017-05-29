package presenters;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.*;
import java.util.Collection;import java.util.Set;
import payrollcasestudy.boundaries.*;
public class EmployeePresenter {
	public EmployeePresenter() 	{}
	public void newEmployee(String name, String address, int paymentClassification, double hourlyRate, double salary, double commissionRate)
	{
		Transaction addEmployeeTransaction = null;
		Set<Integer> employeesIds = PayrollDatabaseOnMemory.globalPayrollDatabase.getAllEmployeeIds();
		int employeeId = 0;
		if(!employeesIds.isEmpty())
			employeeId = employeesIds.toArray().length;
		if(paymentClassification == 1)
			addEmployeeTransaction = new AddHourlyEmployeeTransaction(employeeId, name, address, hourlyRate);
		if(paymentClassification == 2)
			addEmployeeTransaction = new AddSalariedEmployeeTransaction(employeeId, name, address, salary);
		if(paymentClassification == 3)
			addEmployeeTransaction = new AddCommissionedEmployeeTransaction(employeeId, name, address, salary , commissionRate);
		addEmployeeTransaction.execute(PayrollDatabaseOnMemory.globalPayrollDatabase);		
	}	
	public Collection<Employee> showEmployees(){	
		return PayrollDatabaseOnMemory.globalPayrollDatabase.getAllEmployees();
	}	
	public Object[] getEmployeesIds(){	
		return PayrollDatabaseOnMemory.globalPayrollDatabase.getAllEmployeeIds().toArray();
	}
	public Employee showASingleEmployee(int employeeId)	{		return PayrollDatabaseOnMemory.globalPayrollDatabase.getEmployee(employeeId);
	}}
