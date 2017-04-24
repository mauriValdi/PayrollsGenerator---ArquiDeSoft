package payrollcasestudy.transactions.add;

import java.util.Calendar;

import payrollcasestudy.boundaries.PayrollDatabase;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.affiliations.UnionAffiliation;
import payrollcasestudy.transactions.Transaction;

public class AddServiceChargeTransaction implements Transaction {

	private Calendar date;
	private double amount;
	private int memberId;
	
	public AddServiceChargeTransaction(int memberId, Calendar date, double amount) {
		this.memberId = memberId;
		this.date = date;
		this.amount = amount;
	}

	public void execute() {
		Employee unionMember = PayrollDatabase.globalPayrollDatabase.getUnionMember(memberId);
		if (unionMember != null)
		{
			UnionAffiliation unionAffiliation = unionMember.getUnionAffiliation();
        	unionAffiliation.addServiceCharge(date, amount);
        }
	}

}
