package payrollcasestudy;

import org.junit.rules.ExternalResource;
import payrollcasestudy.boundaries.PayrollDatabaseOnMemory;

public class DatabaseResource extends ExternalResource {
    protected PayrollDatabaseOnMemory instance;

    public void before(){
        instance = PayrollDatabaseOnMemory.globalPayrollDatabase;
    }

    public void after(){
        instance.clear();
    }

    public PayrollDatabaseOnMemory getInstance() {
        return instance;
    }
}
