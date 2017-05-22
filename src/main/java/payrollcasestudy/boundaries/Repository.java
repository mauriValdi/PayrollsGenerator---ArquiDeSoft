package payrollcasestudy.boundaries;

import java.awt.List;
import java.util.Set;

import payrollcasestudy.entities.Employee;

public interface Repository {
	void addEmployee(int employeeId, Employee employee);
	/*List getEmployees();
	Employee getEmployee(int employeeId);
	Set<Integer> getAllEmployeeIds();
	void deleteUnionMember(int memberId);
	void addUnionMember(int memberId, Employee employee);
	Employee getUnionMember(int memberId);
	void deleteEmployee(int employeeId);*/
}
