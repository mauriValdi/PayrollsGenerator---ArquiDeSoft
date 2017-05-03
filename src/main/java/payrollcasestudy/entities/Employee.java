package payrollcasestudy.entities;

import payrollcasestudy.entities.affiliations.UnionAffiliation;
import payrollcasestudy.entities.paymentclassifications.*;
import payrollcasestudy.entities.paymentmethods.PaymentMethod;
import payrollcasestudy.entities.paymentschedule.PaymentSchedule;
import payrollcasestudy.presenter.employee.builders.EmployeeView;
import payrollcasestudy.presenter.employee.builders.Updatable;

import java.util.Calendar;

public class Employee {
    private PaymentClassification paymentClassification;
    private PaymentSchedule paymentSchedule;
    private PaymentMethod paymentMethod;
    private int employeeId;
    private String name;
    private String address;
    private Updatable updatable;
    public Updatable getUpdatable() {
		return updatable;
	}

	public void setUpdatable(Updatable updatable) {
		this.updatable = updatable;
	}

	private UnionAffiliation employeeUnionAffiliation = UnionAffiliation.NO_AFFILIATION;;

    public Employee(int employeeId, String name, String address) {
    	this.updatable = new EmployeeView();
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
	
	public String update(Updatable update) {
		String result = "";
		result += update.startEmployee();
		result += update.updateName(name);
		result += update.updateAddress(address);
		if(paymentClassification instanceof HourlyPaymentClassification)
		{
			result += update.updatePaymentClass("Hourly");
			HourlyPaymentClassification hourlyPaymentClassification = (HourlyPaymentClassification) paymentClassification;
			result += update.updateHourlyRate(Double.toString(hourlyPaymentClassification.getHourlyRate()));
			result += update.updatePaymentSchedule("Weekly");
		}
		if(paymentClassification instanceof SalariedClassification)
		{
			result += update.updatePaymentClass("Salaried");
			SalariedClassification salariedClassification = (SalariedClassification) paymentClassification;
			result += update.updateSalary(Double.toString(salariedClassification.getSalary()));
			result += update.updatePaymentSchedule("Monthly");
		}
		if(paymentClassification instanceof CommissionedPaymentClassification)
		{
			result += update.updatePaymentClass("Commissioned");
			CommissionedPaymentClassification commissionedClassification = (CommissionedPaymentClassification) paymentClassification;
			result += update.updateSalary(Double.toString(commissionedClassification.getMonthlySalary()));
			result += update.updateComissionRate(Double.toString(commissionedClassification.getCommissionRate()));
			result += update.updatePaymentSchedule("Biweekly");
		}		
		result += update.endEmployee();
		return result;
	}

}
