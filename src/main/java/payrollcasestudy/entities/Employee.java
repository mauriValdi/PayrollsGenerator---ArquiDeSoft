package payrollcasestudy.entities;

import payrollcasestudy.entities.affiliations.UnionAffiliation;
import payrollcasestudy.entities.paymentclassifications.*;
import payrollcasestudy.entities.paymentmethods.PaymentMethod;
import payrollcasestudy.entities.paymentschedule.PaymentSchedule;
import java.util.Calendar;

public class Employee {
    private PaymentClassification paymentClassification;
    private PaymentSchedule paymentSchedule;
    private PaymentMethod paymentMethod;
    private int employeeId;
    private String name;
    private String address;
    
	private UnionAffiliation employeeUnionAffiliation = UnionAffiliation.NO_AFFILIATION;;

    public Employee(int employeeId, String name, String address) {    	
        this.employeeId = employeeId;
        this.name = name;
        this.address = address;
    }

    public PaymentClassification getPaymentClassification() {
        return paymentClassification;
    }

    public void setPaymentClassification(PaymentClassification paymentClassification) {
        this.paymentClassification = paymentClassification;
    }

    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getName() {
        return name;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPayDate(Calendar payDate) {
        return paymentSchedule.isPayDate(payDate);
    }

    public Calendar getPayPeriodStartDay(Calendar payDate) {
        return paymentSchedule.getPayPeriodStartDate(payDate);
    }

    public void payDay(PayCheck payCheck) {
        double grossPay = paymentClassification.calculatePay(payCheck);
        double deductions = employeeUnionAffiliation.calculateDeduction(payCheck);
        double netPay = grossPay - deductions;
        payCheck.setDeductions(deductions);
        payCheck.setGrossPay(grossPay);
        payCheck.setNetPay(netPay);
        paymentMethod.pay(payCheck);
    }

	public void setUnionAffiliation(UnionAffiliation unionAffiliation) {
		employeeUnionAffiliation = unionAffiliation;
	}

	public UnionAffiliation getUnionAffiliation() {
		return employeeUnionAffiliation;
	}
	
	public String getPaymentClassificationString()
	{
		if(paymentClassification instanceof HourlyPaymentClassification)
			return "Hourly";	
		if(paymentClassification instanceof SalariedClassification)
			return "Salaried";
		if(paymentClassification instanceof CommissionedPaymentClassification)
			return "Commissioned";	
		return "";
	}
	
	public String getPaymentScheduleString()
	{
		if(paymentClassification instanceof HourlyPaymentClassification)
			return "Weekly";	
		if(paymentClassification instanceof SalariedClassification)
			return "Monthly";
		if(paymentClassification instanceof CommissionedPaymentClassification)
			return "Biweekly";	
		return "";
	}
	
	public double getHourlyRate()
	{
		if(paymentClassification instanceof HourlyPaymentClassification)
		{			
			HourlyPaymentClassification hourlyPaymentClassification = (HourlyPaymentClassification) paymentClassification;
			return hourlyPaymentClassification.getHourlyRate();
		}
		return 0;
	}
	
	public double getSalary()
	{
		if(paymentClassification instanceof SalariedClassification)
		{		
			SalariedClassification salariedClassification = (SalariedClassification) paymentClassification;
			return salariedClassification.getSalary();
		}
		if(paymentClassification instanceof CommissionedPaymentClassification)
		{		
			CommissionedPaymentClassification commissionedClassification = (CommissionedPaymentClassification) paymentClassification;
			return commissionedClassification.getMonthlySalary();			
		}	
		return 0;
	}
	
	public double getCommisionRate()
	{
		if(paymentClassification instanceof CommissionedPaymentClassification)
		{			
			CommissionedPaymentClassification commissionedClassification = (CommissionedPaymentClassification) paymentClassification;
			return commissionedClassification.getCommissionRate();
		}
		return 0;
	}
	
	public int getId()
	{
		return employeeId;
	}
}