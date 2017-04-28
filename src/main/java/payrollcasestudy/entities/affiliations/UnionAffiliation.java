package payrollcasestudy.entities.affiliations;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static payrollcasestudy.entities.paymentclassifications.PaymentClassification.isInPayPeriod;
import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.ServiceCharge;

import org.hamcrest.Matcher;

public class UnionAffiliation {

	public static final UnionAffiliation NO_AFFILIATION = new UnionAffiliation(-1, 0);
	private int memberId;
	private double dues;
	private Map<Calendar, ServiceCharge> serviceCharges = new HashMap<Calendar, ServiceCharge>();
	
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

	public void addServiceCharge(Calendar date, double amount) {
		this.serviceCharges.put(date, new ServiceCharge(date, amount));
	}

	public ServiceCharge getServiceCharge(Calendar date) {
		return serviceCharges.get(date);
	}

	public double calculateDeduction(PayCheck payCheck) {
		double deductions = 0;
        deductions += calculateNumberOfFridaysInPeriod(payCheck.getPayPeriodStart(), payCheck.getPayPeriodEnd()) * dues;
        for (ServiceCharge serviceCharge : serviceCharges.values()){
            if (isInPayPeriod(serviceCharge.getDate(), payCheck)){
                deductions += serviceCharge.getAmount();
            }
        }
        return deductions;
	}
	
	private int calculateNumberOfFridaysInPeriod(Calendar payPeriodStart, Calendar payPeriodEnd) {
        int numberOfFridays = 0;
        while (!payPeriodStart.after(payPeriodEnd)){
            if (payPeriodStart.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
                numberOfFridays++;
            }
            payPeriodStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        return numberOfFridays;
    }

}
