import static spark.Spark.*;
import java.util.HashMap;
import spark.ModelAndView;
import presenters.*;
import spark.template.velocity.VelocityTemplateEngine;

public class Main {	
	
	public static void main(String[] args) {
		//staticFileLocation("/templates");
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
			response.redirect("/ShowEmployees");
			return null;
		});
		
		post("/SaleReceipts", (request, response) -> {
			view.clear();
			SaleReceiptPresenter presenter = new SaleReceiptPresenter();
			view.put("Receipts", presenter.getSalesReceiptArray(Integer.parseInt(request.queryParams("employeeId"))));
			return new ModelAndView(view, "/salesReceipts.vtl");
		}, new VelocityTemplateEngine());
		
		post("/AddTimeCard", (request, response) -> {
			TimeCardPresenter presenter = new TimeCardPresenter();
			presenter.addTimeCard(Integer.parseInt(request.queryParams("employeeId")), Integer.parseInt(request.queryParams("year")), Integer.parseInt(request.queryParams("month")), Integer.parseInt(request.queryParams("day")), Integer.parseInt(request.queryParams("hours")));
			response.redirect("/ShowEmployees");
			return null;
		});
		
		post("/TimeCards", (request, response) -> {
			view.clear();
			TimeCardPresenter presenter = new TimeCardPresenter();
			view.put("Cards", presenter.getTimeCardArray(Integer.parseInt(request.queryParams("employeeId"))));
			return new ModelAndView(view, "/timeCards.vtl");
		}, new VelocityTemplateEngine());
		
		post("/Pay", (request, response) -> {
			view.clear();
			view.put("Id", Integer.parseInt(request.queryParams("employeeId")));
			return new ModelAndView(view, "/pay.vtl");
		}, new VelocityTemplateEngine());
		
		post("/Payroll", (request, response) -> {
			view.clear();
			PaydayPresenter payrollPresenter = new PaydayPresenter();
			view.put("Check", payrollPresenter.getPayroll(Integer.parseInt(request.queryParams("year")), Integer.parseInt(request.queryParams("month")), Integer.parseInt(request.queryParams("day")), Integer.parseInt(request.queryParams("employeeId"))));
			return new ModelAndView(view, "/payroll.vtl");
		}, new VelocityTemplateEngine());
		
		get("/AFP", (request, response) -> {
			PaydayPresenter presenter = new PaydayPresenter();
			return presenter.getAllPayChecks(2017, 5, 19);
		}, JsonUtil.json());	
	}
	
	private static String updateEmployees(String name, String address, int paymentClassification, double hourlyRate, double salary, double commissionRate) {
		EmployeePresenter presenter = new EmployeePresenter();
		presenter.newEmployee(name, address, paymentClassification, hourlyRate, salary, commissionRate);
		return "";
	}
	
}
