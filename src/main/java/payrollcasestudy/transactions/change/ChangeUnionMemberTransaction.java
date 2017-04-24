package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.transactions.Transaction;

public abstract class ChangeUnionMemberTransaction implements Transaction {

	   PayrollDatabase database = PayrollDatabase.globalPayrollDatabase;
	    private int employeeId;	

	    public ChangeUnionMemberTransaction(int employeeId) {
	        this.employeeId = employeeId;
	    }

	    public void execute() {
	        Employee employee = database.getEmployee(employeeId);
	        int memberId = employee.getUnionAffiliation().getMemberId();
	        changeMember(employee);
	        database.addUnionMember(memberId, employee);
	    }

	public abstract void changeMember(Employee employee);
	

}
