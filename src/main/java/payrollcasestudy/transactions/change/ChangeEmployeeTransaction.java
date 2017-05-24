package payrollcasestudy.transactions.change;

import payrollcasestudy.boundaries.Repository;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.transactions.Transaction;

public abstract class ChangeEmployeeTransaction implements Transaction {

    private int employeeId;

    public ChangeEmployeeTransaction(int employeeId) {
        this.employeeId = employeeId;
    }

    public void execute(Repository repository) {
        Employee employee = repository.getEmployee(employeeId);
        changeEmployee(employee);
    }

    public abstract void changeEmployee(Employee employee);
}
