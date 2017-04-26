package payrollcasestudy.transactions.change;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.affiliations.UnionAffiliation;

public class ChangeNoMemberTransaction extends ChangeUnionMemberTransaction {

    public ChangeNoMemberTransaction(int employeeId) {
        super(employeeId);
    }

    
    public UnionAffiliation getAffiliation() {
        return UnionAffiliation.NO_AFFILIATION;
    }

    @Override
    public void changeMember(Employee employee) {
        int memberId = employee.getUnionAffiliation().getMemberId();
        database.deleteUnionMember(memberId);
        employee.setUnionAffiliation(getAffiliation());
    }

}