package presenters;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.presenter.employee.builders.EmployeeView;
import payrollcasestudy.transactions.Transaction;
import payrollcasestudy.transactions.add.*;

import java.util.Iterator;
import java.util.Set;

import payrollcasestudy.boundaries.*;

public class EmployeePresenter {	
	
	public EmployeePresenter(int id) {		
	}

	public void newEmployee(String name, String address, String paymentClassification, double hourlyRate, double salary, double commissionRate)
	{
		Transaction addEmployeeTransaction = null;
		Set<Integer> employeesIds = PayrollDatabase.globalPayrollDatabase.getAllEmployeeIds();
		int employeeId = 0;
		if(!employeesIds.isEmpty())
			employeeId = employeesIds.toArray().length;
		
		if(paymentClassification == "Hourly")
			addEmployeeTransaction = new AddHourlyEmployeeTransaction(employeeId, name, address, hourlyRate);
		if(paymentClassification == "Salaried")
			addEmployeeTransaction = new AddSalariedEmployeeTransaction(employeeId, name, address, salary);
		if(paymentClassification == "Commissioned")
			addEmployeeTransaction = new AddCommissionedEmployeeTransaction(employeeId, name, address, salary , commissionRate);
		
		addEmployeeTransaction.execute();
	}
	
	public String showEmployees(){	
		EmployeeView view = new EmployeeView();
		String employeesTable = "";
		Set<Integer> employeesIds = PayrollDatabase.globalPayrollDatabase.getAllEmployeeIds();
		for (Iterator<Integer> it = employeesIds.iterator(); it.hasNext();) {
			int employeeId = it.next();
			Employee employee = PayrollDatabase.globalPayrollDatabase.getEmployee(employeeId);
			employeesTable = employeesTable + employee.update(view);
		}
		return employeesTable;
	}
}
