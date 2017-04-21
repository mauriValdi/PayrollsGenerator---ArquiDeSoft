package payrollcasestudy.transactions.change;


import payrollcasestudy.entities.Employee;


public class ChangeNoMemberTransaction extends ChangeEmployeeTransaction {

	public ChangeNoMemberTransaction(int employeeId, int memberId) {
		super(employeeId);
		database.deleteUnionMember(memberId);
	}

	@Override
	public void changeEmployee(Employee employee) {
		employee.setUnionAffiliation(null);
	}

}




