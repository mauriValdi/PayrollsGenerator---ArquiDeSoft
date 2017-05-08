import static spark.Spark.*;
import java.util.HashMap;
import spark.ModelAndView;
import presenters.EmployeePresenter;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {	
	
	public static void main(String[] args) {
		//staticFileLocation("/public");
		HashMap<String,Object> view = new HashMap<String,Object>();
				
		get("/", (request, response) -> {					
			return new ModelAndView(view, "/newEmployee.vtl");
		}, new VelocityTemplateEngine());		
		
		post("/CreatingEmployee", (request, response) -> updateEmployees(request.queryParams("name"), request.queryParams("address"), Integer.parseInt(request.queryParams("paymentClassification")), Double.parseDouble(request.queryParams("hourlyRate")), Double.parseDouble(request.queryParams("salary")), Double.parseDouble(request.queryParams("commissionRate"))));
		get("/CreatingJohn", (request, response) -> updateEmployees("john", "address", 1, 12, 0, 0));
		get("/CreatingHamster", (request, response) -> updateEmployees("Hamster", "address2", 3, 0, 200, 20));
		get("/NewEmployee", (request, response) -> newEmployeeForm());
		post("/EmployeesTable", (request, response) ->showEmployees());
	}
	
	private static String updateEmployees(String name, String address, int paymentClassification, double hourlyRate, double salary, double commissionRate) {
		EmployeePresenter presenter = new EmployeePresenter(1);
		presenter.newEmployee(name, address, paymentClassification, hourlyRate, salary, commissionRate);
		return presenter.showEmployees();
	}

	private static String showEmployees() {
		EmployeePresenter presenter = new EmployeePresenter(1);
		return presenter.showEmployees();
	}
	
	  private static String newEmployeeForm() {		  
		    return 	"<form method='post' action='/CreatingEmployee'>" 		 
		        + "<label>Name:</label>"		 
		        + "<input type='text' name='name'>"
		        + "<br>"
		        + "<label>Address:</label>"		 
		        + "<input type='text' name='address'>"
		        + "<br>"
		        + "<br>"
		        + "<label>Payment asdClassification:</label>"		 
		        + "<input type='radio' name='paymentClassification' value='1'> Hourly"
		        + "<input type='radio' name='paymentClassification' value='2'> Salaried"
		        + "<input type='radio' name='paymentClassification' value='3'> Commissioned"
		        + "<br>"
		        + "<label>Hourly Rate:</label>"
		        + "<input type='number' name='hourlyRate' value=0>"
		        + "<br>"
		        + "<label>Salary:</label>"		 
		        + "<input type='number' name='salary' value=0>"
		        + "<br>"
		        + "<label>Commission Rate:</label>"		 
		        + "<input type='number' name='commissionRate' value=0>"		       
		        + "<br>"
		        + "<br>"		       
		        + "<input type='submit' value='Create'>"
		        + "</form>";		 
	  }
		 

}
