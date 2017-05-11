package updatables;

public interface Updatable {
	String updateName(String name);
	String updateAddress(String address);
	String updateHourlyRate(String hourlyRate);
	String updateSalary(String salary);
	String updateComissionRate(String commissionRate);
	String updatePaymentClass(String paymentClass);
	String updatePaymentSchedule(String schedule);
	String updateAddCart(String employeeId);
	String startEmployee();
	String endEmployee();
}
