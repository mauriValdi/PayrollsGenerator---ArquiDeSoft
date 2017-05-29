package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.PayrollDatabaseOnMemory;
import payrollcasestudy.boundaries.Repository;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.affiliations.UnionAffiliation;
import payrollcasestudy.transactions.Transaction;

public abstract class ChangeUnionMemberTransaction implements Transaction {

	   PayrollDatabaseOnMemory database = PayrollDatabaseOnMemory.globalPayrollDatabase;
	    private int employeeId;	

	    public ChangeUnionMemberTransaction(int employeeId) {
	        this.employeeId = employeeId;
	    }

	    public void execute(Repository repository) {
	        Employee employee = database.getEmployee(employeeId);	       
	        changeMember(employee);	        
	    }

	public abstract void changeMember(Employee employee);
	
	protected abstract UnionAffiliation getAffiliation();

}
