package payrollcasestudy.presenter.employee.builders;

public class EmployeeView implements Updatable{
	public String startEmployee() {
		return "<div>";
	}
	
	public String endEmployee() {
		return "</div>"; 
	}

	public String updateName(String name) {
		return "<div>"
			+ "<label>name: </label>"
			+ ""+name+""
			+"</div>";
	}		
		
	public String updateAddress(String address) {
		return "<div>"
			+ "<label>Address: </label>"
			+ ""+address+""
			+"</div>";
	}
	
	public String updateHourlyRate(String hourlyRate)
	{
		return "<div>"
				+ "<label>Hourly Rate: </label>"
				+ ""+hourlyRate+""
				+"</div>";		
	}
	
	public String updateSalary(String salary)
	{
		return "<div>"
				+ "<label>Salary: </label>"
				+ ""+salary+""
				+"</div>";		
	}
	
	public String updateComissionRate(String commissionRate)
	{
		return "<div>"
				+ "<label>Commission Rate: </label>"
				+ ""+commissionRate+""
				+"</div>";		
	}
	
	public String updatePaymentClass(String paymentClass)
	{
		return "<div>"
				+ "<label>Payment Classification: </label>"
				+ ""+paymentClass+""
				+"</div>";		
	}
	
	public String updatePaymentSchedule(String schedule)
	{
		return "<div>"
				+ "<label>Schedule: </label>"
				+ ""+schedule+""
				+"</div>";		
	}
}
