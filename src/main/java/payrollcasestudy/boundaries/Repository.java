package payrollcasestudy.boundaries;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.SalesReceipt;
import payrollcasestudy.entities.TimeCard;

public interface Repository {
	void addEmployee(int employeeId, Employee employee);
	Employee getEmployee(int employeeId);
	Set<Integer> getAllEmployeeIds();
	void deleteUnionMember(int memberId);
	void addUnionMember(int memberId, Employee employee);
	Employee getUnionMember(int memberId);
	void deleteEmployee(int employeeId);
	void testDataBase();
	Collection<Employee> getAllEmployees();
	void saveTimeCard(int employeeId, TimeCard timeCard);
	void saveSalesReceipt(int employeeId, SalesReceipt salesReceipt);
}
