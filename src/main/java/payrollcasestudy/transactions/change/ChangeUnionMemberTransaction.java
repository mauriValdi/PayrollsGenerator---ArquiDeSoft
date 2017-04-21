package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.transactions.Transaction;

public abstract class ChangeUnionMemberTransaction implements Transaction {

	   PayrollDatabase database = PayrollDatabase.globalPayrollDatabase;
	    private int employeeId;
	    private int memberId;

	    public ChangeUnionMemberTransaction(int employeeId, int memberId) {
	        this.employeeId = employeeId;
	        this.memberId = memberId;
	    }

	    public void execute() {
	        Employee employee = database.getEmployee(employeeId);
	        changeMember(employee,memberId);
	        database.addUnionMember(memberId, employee);
	    }

	public abstract void changeMember(Employee employee, int memberId);
	

}
