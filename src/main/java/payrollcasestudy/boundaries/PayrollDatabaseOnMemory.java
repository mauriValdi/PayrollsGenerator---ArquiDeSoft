package payrollcasestudy.boundaries;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.SalesReceipt;
import payrollcasestudy.entities.TimeCard;

import java.util.*;

/**
 * Listing 19-3
 * Listing 19-4
 */
public class PayrollDatabaseOnMemory implements Repository{
    public static PayrollDatabaseOnMemory globalPayrollDatabase = new PayrollDatabaseOnMemory();

    private Map<Integer, Employee> employees = new HashMap<Integer, Employee>();
    public Map<Integer, Employee> unionMembers = new HashMap<Integer, Employee>();


    public Employee getEmployee(int employeeId) {
        return employees.get(employeeId);
    }

    public void addEmployee(int employeeId, Employee employee) {
        employees.put(employeeId, employee);
    }

    public void clear(){
        employees.clear();
        unionMembers.clear();
    }

    public void deleteEmployee(int employeeId) {
        employees.put(employeeId, null);
    }

    public Employee getUnionMember(int memberId) {
        return unionMembers.get(memberId);
    }

    public void addUnionMember (int memberId, Employee employee) {
        unionMembers.put(memberId, employee);
    }

    public void deleteUnionMember(int memberId) {
        unionMembers.remove(memberId);
    }

    public Set<Integer> getAllEmployeeIds() {
        return employees.keySet();
    }
    
    public Collection<Employee> getAllEmployees(){
    	return  employees.values();
    }


	@Override
	public void testDataBase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveTimeCard(int employeeId, TimeCard timeCard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveSalesReceipt(int employeeId, SalesReceipt salesReceipt) {
		// TODO Auto-generated method stub
		
	}
	
	
    
    
}
