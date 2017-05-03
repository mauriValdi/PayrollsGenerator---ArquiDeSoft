package payrollcasestudy.presenter.employee.builders;

public class EmpleadoView implements Updatable{
	public String inicioEmpleado() {
		return "<div>";
		}

		public String updateNombre(String nombre) {
			return "<div>"
					+ "<label>Nombre: </label>"
					+ ""+nombre+""
					+"</div>";
		}

		public String finEmpleado() {
			return "</div>"; }

		
		public String updateAddress(String address) {
			return "<div>"
					+ "<label>Apellido: </label>"
					+ ""+address+""
					+"</div>";
		} 
}
