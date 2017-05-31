package payrollcasestudy.entities.affiliations;

import static payrollcasestudy.entities.paymentclassifications.PaymentClassification.isInPayPeriod;

import java.util.Calendar;

import payrollcasestudy.entities.PayCheck;
import payrollcasestudy.entities.ServiceCharge;

public interface Affilliation {
	public int getMemberId();
	public void setMemberId(int memberId);
	public double getDues();
	public void setDues(double d);
	public void addServiceCharge(Calendar date, double amount);
	public ServiceCharge getServiceCharge(Calendar date);
	public double calculateDeduction(PayCheck payCheck);
	public int calculateNumberOfFridaysInPeriod(Calendar payPeriodStart, Calendar payPeriodEnd); 
}
