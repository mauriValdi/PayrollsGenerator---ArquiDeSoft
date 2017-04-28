package payrollcasestudy.transactions.change;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.affiliations.UnionAffiliation;

public class ChangeMemberTransaction extends ChangeUnionMemberTransaction{	

	private double dues;
	private int memberId;

	public ChangeMemberTransaction(int employeeId, int memberId, double dues) {
		super(employeeId);
		this.memberId = memberId;
		this.dues = dues;
	}

	@Override
	public void changeMember(Employee employee) {
		database.addUnionMember(memberId, employee);
		employee.setUnionAffiliation(getAffiliation());
	}

	@Override
	protected UnionAffiliation getAffiliation() {
		return new UnionAffiliation(memberId, dues);
	}

}
