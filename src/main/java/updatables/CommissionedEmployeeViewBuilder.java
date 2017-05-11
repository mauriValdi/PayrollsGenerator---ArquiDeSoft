package updatables;

public class CommissionedEmployeeViewBuilder {
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
	
	public String updateAddCart(String employeeId) {
		return "<div>"
				+"<h3>Add a time card:</h3>"
				+"<form method='post' action='/AddSaleReceipt'>"
				+	"<label>Year:</label>"
				+	"<input type='number' name='year'>"
				+	"<label>Month:</label>"
				+	"<input type='number' name= 'month' min='1' max='12' value='1'>"
				+	"<label>Day:</label>"
				+	"<input type='number' name='day' min='1' max='31' value = '1'>"
				+	"<br>"
				+	"<label>Hours:</label>"
				+	"<input type='number' name='amount' value=0>" 
				+	"<input type='hidden' name='employeeId' value= '"+employeeId+"'>"
				+	"<br>"
				+	"<input type='submit' value='Add'>"
				+"</form>"
				+"</div>;";
	}
	
	public String updatePaymentSchedule(String schedule)
	{
		return "<div>"
				+ "<label>Schedule: </label>"
				+ ""+schedule+""
				+"</div>";		
	}
}
