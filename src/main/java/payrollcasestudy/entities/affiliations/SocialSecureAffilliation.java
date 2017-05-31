package payrollcasestudy.entities.affiliations;

import static payrollcasestudy.entities.paymentclassifications.PaymentClassification.isInPayPeriod;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.ServiceCharge;

public class SocialSecureAffilliation implements Affilliation{
	
	private int memberId;
	private double dues;
	private Map<Calendar, ServiceCharge> serviceCharges = new HashMap<Calendar, ServiceCharge>();
	
	SocialSecureAffilliation(int memberId, double dues){
		this.memberId = memberId;
		this.dues = dues;
	}
	
	@Override
	public int getMemberId() {
		return memberId;
	}

	@Override
	public void setMemberId(int memberId) {
		this.memberId=memberId;
		
	}
	@Override
	public double getDues() {
		return dues;
	}
	@Override
	public void setDues(double dues) {
		this.dues=dues;
	}

	@Override
	public void addServiceCharge(Calendar date, double amount) {
		this.serviceCharges.put(date, new ServiceCharge(date, amount));
	}

	@Override
	public ServiceCharge getServiceCharge(Calendar date) {
		return serviceCharges.get(date);
	}

	@Override
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

	@Override
	public int calculateNumberOfFridaysInPeriod(Calendar payPeriodStart, Calendar payPeriodEnd) {
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
