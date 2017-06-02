package payrollcasestudy.boundaries;


import java.util.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.SalesReceipt;
import payrollcasestudy.entities.TimeCard;
import payrollcasestudy.entities.paymentclassifications.CommissionedPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.HourlyPaymentClassification;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentclassifications.SalariedClassification;
import payrollcasestudy.entities.paymentmethods.HoldMethod;
import payrollcasestudy.entities.paymentschedule.BiweeklyPaymentSchedule;
import payrollcasestudy.entities.paymentschedule.MonthlyPaymentSchedule;
import payrollcasestudy.entities.paymentschedule.WeeklyPaymentSchedule;

public class JDBCPersistance implements Repository {
	private final String userName = "root";
	private final String password = "";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private final String dbName = "test";
	private final String tableName = "JDBC_TEST";
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);

		return conn;
	}

	/**
	 * Run a SQL command which does not return a recordset:
	 * CREATE/INSERT/UPDATE/DELETE/DROP/etc.
	 * 
	 * @throws SQLException If something goes wrong
	 */
	public boolean executeUpdate(Connection conn, String command) throws SQLException {
	    Statement stmt = null;
	    try {
	        stmt = conn.createStatement();
	        stmt.executeUpdate(command); // This will throw a SQLException if it fails
	        return true;
	    } finally {

	    	// This will run whether we throw an exception or not
	        if (stmt != null) { stmt.close(); }
	    }
	}
	

	@Override
	public void addEmployee(int employeeId, Employee employee) {
		Connection jdbcConnection = null;
		jdbcConnection = connectDB(jdbcConnection);	
		try {
			String addEmployeeQuery =
					"INSERT INTO employee (name, address, id_payClass) VALUES (?, ?, ?)";
	        PreparedStatement statement = jdbcConnection.prepareStatement(addEmployeeQuery);
	        statement.setString(1, employee.getName());
	        statement.setString(2, employee.getAddress());
	        statement.setInt(3, employee.getId());
	        statement.executeUpdate();
	        statement.close();
		} catch(SQLException exception){
			System.out.println("ERROR: Could not add the employee");
			exception.printStackTrace();
			return;
		}
		int lastEmployeeId = getLastEmployeeId();
		addPaymentClassification(lastEmployeeId,employee);
	}
	
	public int getLastEmployeeId(){
		int lastId = 0;
		Connection jdbcConnection = null;  
        Statement statement;
		ResultSet resultSet;
		jdbcConnection = connectDB(jdbcConnection);
        String jdbcListQuery = "SELECT Id FROM employee ORDER BY Id DESC LIMIT 1";
		try {
			statement = jdbcConnection.createStatement();
			resultSet = statement.executeQuery(jdbcListQuery);
			if (resultSet.next()) {
				lastId = resultSet.getInt("id");
			}
		    resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not read from the database");
			e.printStackTrace();
		}  
		return lastId;
	}
	
		
	

	private Connection connectDB(Connection jdbcConnection) {
		try {
			jdbcConnection = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
		}
		return jdbcConnection;
	}

	@Override
	public Collection<Employee> getAllEmployees() {
		Connection jdbcConnection = null;  
        Statement statement;
		ResultSet resultSet;
		jdbcConnection = connectDB(jdbcConnection);
		List<Employee> employeeList = new ArrayList<Employee>();
        String jdbcListQuery = "SELECT * FROM employee";
		List<Integer> employeeIds = (List<Integer>) getAllEmployeeIds();
		for(int id:employeeIds)
		{
			employeeList.add(getEmployee(id));
		}
		Collection<Employee> employeeCollection = employeeList;
        return employeeCollection;
	}

	@Override
	public Employee getEmployee(int employeeId) {
		Connection jdbcConnection = null;  
        Statement statement;
		ResultSet resultSet;
		jdbcConnection = connectDB(jdbcConnection);
        String jdbcListQuery = "SELECT * FROM employee WHERE id="+employeeId;
        Employee employee = new Employee(0, " ", " ");
		try {
			statement = jdbcConnection.createStatement();
			resultSet = statement.executeQuery(jdbcListQuery);
			if (resultSet.next()) {
			    int id = resultSet.getInt("id");
			    String name = resultSet.getString("name");
			    String address = resultSet.getString("address");
			    employee = new Employee(id, name, address);
			    System.out.println();
			    System.out.println(" EMPLEADO ENCONTRADO EN BASE DE DATOS: " +id+name+address);
			    System.out.println();
			}
		    resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not read from the database");
			e.printStackTrace();
		}        
		jdbcConnection = connectDB(jdbcConnection);
        jdbcListQuery = "SELECT * FROM paymentclassification WHERE Id="+employeeId;
		try {
			statement = jdbcConnection.createStatement();
			resultSet = statement.executeQuery(jdbcListQuery);
			if (resultSet.next()) {
			    int id = resultSet.getInt("Id");
			    String paymentClass = resultSet.getString("paymentClass");
			    Double salary = resultSet.getDouble("salary");
			    Double hourlyRate = resultSet.getDouble("hourlyRate");
			    Double commissionRate = resultSet.getDouble("commissionRate");
			    
			    System.out.println();
			    System.out.println("CLASIFICACION DE PAGOS: " +paymentClass);
			    System.out.println();
			    
			    if(Objects.equals(paymentClass, "Hourly")){
			    	HourlyPaymentClassification hourly = new HourlyPaymentClassification(hourlyRate);
			    	List<TimeCard> cardList= getTimeCards(employeeId);
			    	for(TimeCard timeCard : cardList){
			    		hourly.addTimeCard(timeCard);
			    	}
			    	employee.setPaymentClassification(hourly);
			    	employee.setPaymentSchedule(new WeeklyPaymentSchedule());
			    }
			    if(Objects.equals(paymentClass, "Salaried")){
			    	employee.setPaymentClassification(new SalariedClassification(salary));
			    	employee.setPaymentSchedule(new MonthlyPaymentSchedule());
			    }
			    if(Objects.equals(paymentClass, "Commissioned")){
			    	CommissionedPaymentClassification commissioned = new CommissionedPaymentClassification(salary, commissionRate);
			    	List<SalesReceipt> salesReceiptList= getSaleReceipts(employeeId);
			    	for(SalesReceipt saleReceipt : salesReceiptList){
			    		commissioned.addSalesReceipt(saleReceipt);
			    	}
			    	employee.setPaymentClassification(commissioned);
			    	employee.setPaymentSchedule(new BiweeklyPaymentSchedule());
			    }
			    employee.setPaymentMethod(new HoldMethod());
			}
		    resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not read from the database");
			e.printStackTrace();
		}        
        return employee;
	}

	@Override
	public Set<Integer> getAllEmployeeIds() {
		Connection jdbcConnection = null;  
        Statement statement;
		ResultSet resultSet;
		List<Integer> employeeIds = new ArrayList<Integer>();
		jdbcConnection = connectDB(jdbcConnection);
        String jdbcListQuery = "SELECT * FROM employee WHERE id >= 0";
		try {
			statement = jdbcConnection.createStatement();
			resultSet = statement.executeQuery(jdbcListQuery);
			while (resultSet.next()) {
			    int id = resultSet.getInt("id"); 
			    employeeIds.add(id);
			    System.out.println(" ID DE EMPLEADO DE BASE DE DATOS: " +id);
			}
		    resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not read from the database");
			e.printStackTrace();
		}      
		Set<Integer> setIds = new HashSet<Integer>(employeeIds);
		return setIds;
	}

	
	public void addPaymentClassification(int employeeId, Employee employee){
		Connection jdbcConnection = null;
		jdbcConnection = connectDB(jdbcConnection);	
		try {
			String addPaymentClassQuery =
					"INSERT INTO paymentclassification (Id, paymentClass, salary, hourlyRate, commissionRate) VALUES (?, ?, ?, ?, ?)"
					;

	        PreparedStatement statement = jdbcConnection.prepareStatement(addPaymentClassQuery);
	        statement.setInt(1, employeeId);
	        statement.setString(2, employee.getPaymentClassificationString());
	        statement.setDouble(3, employee.getSalary());
	        statement.setDouble(4, employee.getHourlyRate());
	        statement.setDouble(5, employee.getCommisionRate());
	        statement.executeUpdate();
	        statement.close();
		} catch(SQLException exception){
			System.out.println("ERROR: Could not add the payment classification");
			exception.printStackTrace();
			return;
		}		
	}
	
	public void saveTimeCard(int employeeId, TimeCard timeCard){
		Connection jdbcConnection = null;
		jdbcConnection = connectDB(jdbcConnection);
		try {
			String addTimeCardsQuery = "INSERT INTO timecard (id, dateTime, hours, id_payClassification) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = jdbcConnection.prepareStatement(addTimeCardsQuery);
			statement.setInt(1, employeeId);
		    statement.setDate(2, (Date) timeCard.getDateFormat());
		    statement.setDouble(3, timeCard.getHours());
		    statement.setDouble(4, employeeId);
		    statement.executeUpdate();
		    statement.close();
		} catch (SQLException exception) {
			System.out.println("ERROR: Could not add the timeCard ");
			exception.printStackTrace();
		}
	}
	
	public void saveSalesReceipt(int employeeId, SalesReceipt salesReceipt){
		Connection jdbcConnection = null;
		jdbcConnection = connectDB(jdbcConnection);
		try {
			String addTimeCardsQuery = "INSERT INTO salesreceipt (Id, calendar, amount, id_payClassification) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = jdbcConnection.prepareStatement(addTimeCardsQuery);
			statement.setInt(1, employeeId);
		    statement.setDate(2, (Date) salesReceipt.getDateFormat());
		    statement.setDouble(3, salesReceipt.getAmount());
		    statement.setDouble(4, employeeId);
		    statement.executeUpdate();
		    statement.close();
		} catch (SQLException exception) {
			System.out.println("ERROR: Could not add the receipment ");
			exception.printStackTrace();
		}
	}
	
	public List<TimeCard> getTimeCards(int employeeId){
			Connection jdbcConnection = null;  
	        Statement statement;
			ResultSet resultSet;
			jdbcConnection = connectDB(jdbcConnection);
	        String jdbcListQuery = "SELECT `dateTime`, `hours` FROM timecard WHERE id_payClassification="+employeeId;
	        List<TimeCard> resultList = new ArrayList();
			try {
				statement = jdbcConnection.createStatement();
				resultSet = statement.executeQuery(jdbcListQuery);
				while (resultSet.next()) {
				    Date dateCalendar = resultSet.getDate("dateTime");
				    Double hours = resultSet.getDouble("hours");
				    Calendar calendar = Calendar.getInstance();
				    calendar.setTime(dateCalendar);
				    TimeCard timeCard= new TimeCard(calendar, hours);
				    resultList.add(timeCard);
				}
			    resultSet.close();
				statement.close();
			} catch (SQLException e) {
				System.out.println("ERROR: Could not read from the database");
				e.printStackTrace();
			}
			return resultList;
	}
	
	public List<SalesReceipt> getSaleReceipts(int employeeId){
		Connection jdbcConnection = null;  
        Statement statement;
		ResultSet resultSet;
		jdbcConnection = connectDB(jdbcConnection);
        String jdbcListQuery = "SELECT `calendar`, `amount` FROM salesreceipt WHERE id_payClassification="+employeeId;
        List<SalesReceipt> resultList = new ArrayList();
		try {
			statement = jdbcConnection.createStatement();
			resultSet = statement.executeQuery(jdbcListQuery);
			while (resultSet.next()) {
			    Date dateCalendar = resultSet.getDate("calendar");
			    Double hours = resultSet.getDouble("amount");
			    Calendar calendar = Calendar.getInstance();
			    calendar.setTime(dateCalendar);
			    SalesReceipt saleReceipt= new SalesReceipt(calendar, hours);
			    resultList.add(saleReceipt);
			}
		    resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not read from the database");
			e.printStackTrace();
		}
		return resultList;
}
	
	
	
	
	
	@Override
	public void deleteUnionMember(int memberId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUnionMember(int memberId, Employee employee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getUnionMember(int memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEmployee(int employeeId) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public int getDbLenght(){
		return getAllEmployeeIds().size();
	}
	/**
	 * Connect to the DB and do some stuff
	 */

	@Override
	public void testDataBase() {
		// TODO Auto-generated method stub
		
	}

}
