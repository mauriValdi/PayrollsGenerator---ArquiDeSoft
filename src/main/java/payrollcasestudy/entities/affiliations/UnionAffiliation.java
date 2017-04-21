package payrollcasestudy.entities.affiliations;

import org.hamcrest.Matcher;

public class UnionAffiliation {

	public static final Matcher NO_AFFILIATION = null;
	private int memberId;
	private double dues;
	
	
	public UnionAffiliation(int memberId, double dues) {
		this.memberId = memberId;
		this.dues = dues;
	}
	
	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public double getDues() {
		return dues;
	}

	public void setDues(double d) {
		this.dues = d;
	}

}
