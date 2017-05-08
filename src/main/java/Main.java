import static spark.Spark.*;
import java.util.HashMap;
import spark.ModelAndView;
import presenters.*;
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
		
		post("/ViewEmployee", (request, response) -> {
			view.clear();
			EmployeePresenter presenter = new EmployeePresenter();
			view.put("Employee", presenter.showASingleEmployee(Integer.parseInt(request.queryParams("employeeId"))));
			return new ModelAndView(view, "/employeeView.vtl");
		}, new VelocityTemplateEngine());	
		
		post("/CreatingEmployee", (request, response) -> {
			updateEmployees(request.queryParams("name"), request.queryParams("address"), Integer.parseInt(request.queryParams("paymentClassification")), Double.parseDouble(request.queryParams("hourlyRate")), Double.parseDouble(request.queryParams("salary")), Double.parseDouble(request.queryParams("commissionRate")));
			response.redirect("/");
			return null;
		});
		
		post("/AddSaleReceipt", (request, response) -> {
			SaleReceiptPresenter presenter = new SaleReceiptPresenter();
			presenter.addSaleReceipt(Integer.parseInt(request.queryParams("employeeId")), Integer.parseInt(request.queryParams("year")), Integer.parseInt(request.queryParams("month")), Integer.parseInt(request.queryParams("day")), Integer.parseInt(request.queryParams("amount")));
			response.redirect("/");
			return null;
		});
		
		post("/AddTimeCard", (request, response) -> {
			TimeCardPresenter presenter = new TimeCardPresenter();
			presenter.addTimeCard(Integer.parseInt(request.queryParams("employeeId")), Integer.parseInt(request.queryParams("year")), Integer.parseInt(request.queryParams("month")), Integer.parseInt(request.queryParams("day")), Integer.parseInt(request.queryParams("hours")));
			response.redirect("/");
			return null;
		});
	}
	
	private static String updateEmployees(String name, String address, int paymentClassification, double hourlyRate, double salary, double commissionRate) {
		EmployeePresenter presenter = new EmployeePresenter();
		presenter.newEmployee(name, address, paymentClassification, hourlyRate, salary, commissionRate);
		return "";
	}
	
}
