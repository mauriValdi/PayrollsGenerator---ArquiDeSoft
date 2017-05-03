import static spark.Spark.*;

import payrollcasestudy.presenter.employee.builders.Updatable;
import presenters.EmployeePresenter;

public class Main {

	public static void main(String[] args) {
		get("/", (request, response) -> hola());
		post("/hola", (request, response) -> getFisrtEmployee());
		get("/Arquitectura", (request, response) -> "Hola Arquitectura");
	}

	private static String responder_saludo(String nombre) {
		System.out.println("----------RESPONDIENDO---------");
		return "Hola "+nombre;
	}

	private static String hola() {
		return "<html>"
				+ "<body>"
				+ "<form method='post' action='/hola'>" 
				+ "<label>Nombre:</label>"
				+ "<input type='text' name='nombre_saludo'>"
				+ "<input type='submit' value='Saluda'"
				+ "</body>"
				+ "</html>";
	}
	
	private static String getFisrtEmployee() {
		EmployeePresenter presenter = new EmployeePresenter(1);
		return presenter.showEmployee(1);
	}

}
