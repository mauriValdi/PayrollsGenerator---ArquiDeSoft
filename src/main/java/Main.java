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
			view.clear();
			return new ModelAndView(view, "/Index.vtl");
		}, new VelocityTemplateEngine());		
		
		get("/NewEmployee", (request, response) -> {
			view.clear();
			return new ModelAndView(view, "/newEmployee.vtl");
		}, new VelocityTemplateEngine());
		
		get("/ShowEmployees", (request, response) -> {
			view.clear();
			EmployeePresenter presenter = new EmployeePresenter();
			view.put("Employees", presenter.showEmployees());
			return new ModelAndView(view, "/employeesList.vtl");
		}, new VelocityTemplateEngine());	
		
		post("/CreatingEmployee", (request, response) -> updateEmployees(request.queryParams("name"), request.queryParams("address"), Integer.parseInt(request.queryParams("paymentClassification")), Double.parseDouble(request.queryParams("hourlyRate")), Double.parseDouble(request.queryParams("salary")), Double.parseDouble(request.queryParams("commissionRate"))));
		get("/CreatingJohn", (request, response) -> updateEmployees("john", "address", 1, 12, 0, 0));
		get("/CreatingHamster", (request, response) -> updateEmployees("Hamster", "address2", 3, 0, 200, 20));
		
	}
	
	private static String updateEmployees(String name, String address, int paymentClassification, double hourlyRate, double salary, double commissionRate) {
		EmployeePresenter presenter = new EmployeePresenter();
		presenter.newEmployee(name, address, paymentClassification, hourlyRate, salary, commissionRate);
		return "";
	}
	
}
