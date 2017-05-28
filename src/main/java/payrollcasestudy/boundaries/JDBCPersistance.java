package payrollcasestudy.boundaries;


import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import payrollcasestudy.entities.Employee;

public class JDBCPersistance implements Repository {
	private final String userName = "root";
	private final String password = "";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private final String dbName = "test";
	private final String tableName = "JDBC_TEST";
	/**
	 * Get a new database connection
	 * 
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * Connect to MySQL and do some stuff.
	 */
	public void testDataBase() {

		// Connect to MySQL
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}

		// Create a table
		try {
		    String createString =
			        "CREATE TABLE " + this.tableName + " ( " +
			        "ID INTEGER NOT NULL, " +
			        "NAME varchar(40) NOT NULL, " +
			        "STREET varchar(40) NOT NULL, " +
			        "CITY varchar(20) NOT NULL, " +
			        "STATE char(2) NOT NULL, " +
			        "ZIP char(5), " +
			        "PRIMARY KEY (ID))";
			this.executeUpdate(conn, createString);
			System.out.println("Created a table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not create the table");
			e.printStackTrace();
			return;
		}
		
		// Drop the table
		try {
		    String dropString = "DROP TABLE " + this.tableName;
			this.executeUpdate(conn, dropString);
			System.out.println("Dropped the table");
	    } catch (SQLException e) {
			System.out.println("ERROR: Could not drop the table");
			e.printStackTrace();
			return;
		}
	}
	
	

	@Override
	public void addEmployee(int employeeId, Employee employee) {
		Connection jdbcConnection = null;
		jdbcConnection = connectDB(jdbcConnection);	
		try {
			String addEmployeeQuery =
					"INSERT INTO employee (id, name, address, id_payClass) VALUES (?, ?, ?, ?)"
					;

	        PreparedStatement statement = jdbcConnection.prepareStatement(addEmployeeQuery);
	        statement.setInt(1, employee.getId());
	        statement.setString(2, employee.getName());
	        statement.setString(3, employee.getAddress());
	        statement.setInt(4, employee.getId());
	        statement.executeUpdate();
	        statement.close();
		} catch(SQLException exception){
			System.out.println("ERROR: Could not add the employee");
			exception.printStackTrace();
			return;
		}
		// TODO Auto-generated method stub
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
		try {
			statement = jdbcConnection.createStatement();
			resultSet = statement.executeQuery(jdbcListQuery);
			while (resultSet.next()) {
			    int id = resultSet.getInt("id");
			    String name = resultSet.getString("name");
			    String address = resultSet.getString("address");
			    Employee employee = new Employee(id, name, address);
			    System.out.println(" EMPLEADO DE BASE DE DATOS: " +id+name+address);
			    employeeList.add(employee);
			}
		    resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println("ERROR: Could not read from the database");
			e.printStackTrace();
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

}
