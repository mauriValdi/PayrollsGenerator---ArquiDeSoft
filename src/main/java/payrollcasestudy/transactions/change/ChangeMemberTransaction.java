package payrollcasestudy.transactions.change;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.affiliations.UnionAffiliation;

public class ChangeMemberTransaction extends ChangeUnionMemberTransaction{
	UnionAffiliation affiliation = new UnionAffiliation(1,2);

	public ChangeMemberTransaction(int employeeId, int memberId, double dues) {
		super(employeeId);
		this.affiliation.setMemberId(memberId);
		this.affiliation.setDues(dues);
	}

	@Override
	public void changeMember(Employee employee) {
		employee.setUnionAffiliation(affiliation);
	}

}
