package presenters;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.presenter.employee.builders.EmployeeView;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.*;
import java.util.Iterator;
import java.util.Set;

import payrollcasestudy.boundaries.*;

public class EmployeePresenter {	
	
	public EmployeePresenter() {		
	}

	public void newEmployee(String name, String address, int paymentClassification, double hourlyRate, double salary, double commissionRate)
	{
		Transaction addEmployeeTransaction = null;
		Set<Integer> employeesIds = PayrollDatabase.globalPayrollDatabase.getAllEmployeeIds();
		int employeeId = 0;
		if(!employeesIds.isEmpty())
			employeeId = employeesIds.toArray().length;
		
		if(paymentClassification == 1)
			addEmployeeTransaction = new AddHourlyEmployeeTransaction(employeeId, name, address, hourlyRate);
		if(paymentClassification == 2)
			addEmployeeTransaction = new AddSalariedEmployeeTransaction(employeeId, name, address, salary);
		if(paymentClassification == 3)
			addEmployeeTransaction = new AddCommissionedEmployeeTransaction(employeeId, name, address, salary , commissionRate);
		
		addEmployeeTransaction.execute();
	}
	
	public Employee[] showEmployees(){		
		Set<Integer> employeesIds = PayrollDatabase.globalPayrollDatabase.getAllEmployeeIds();
		Employee[] employeesTable = new Employee[employeesIds.toArray().length];
		int index = 0;
		for (Iterator<Integer> it = employeesIds.iterator(); it.hasNext();) {
			int employeeId = it.next();
			employeesTable[index] = PayrollDatabase.globalPayrollDatabase.getEmployee(employeeId);
			index++;
		}
		return employeesTable;
	}
	
	public Employee showASingleEmployee(int employeeId)
	{
		return PayrollDatabase.globalPayrollDatabase.getEmployee(employeeId);
	}
}
