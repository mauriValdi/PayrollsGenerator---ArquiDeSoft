package presenters;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.presenter.employee.builders.EmpleadoView;

public class EmployeePresenter {
	
	private int id; /*solo para prueba*/
	
	public EmployeePresenter(int id) {
		this.id = id;
	}

	public String showEmployee(int id){
		EmpleadoView view = new EmpleadoView();/*solo prueba (deberia sacar el empleado y ejecurar el update para retornarlo)*/
		Employee nuevoEmpleado = new Employee(1,"juan","La calle 1");
		return nuevoEmpleado.update(view);
	}
}
