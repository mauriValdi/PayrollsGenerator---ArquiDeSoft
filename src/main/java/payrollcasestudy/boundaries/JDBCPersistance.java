package payrollcasestudy.boundaries;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import payrollcasestudy.entities.Employee;

public class JDBCPersistance implements Repository {

	@Override
	public void addEmployee(int employeeId, Employee employee) {
		
		 try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			 String URL = "jdbc:odbc:dbname";
			 Connection dbConn = DriverManager.getConnection(URL, "user", "passw");
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 ObjectOutputStream oos = new ObjectOutputStream(baos);
			 oos.writeObject(employee);
			 byte[] employeeAsBytes = baos.toByteArray();
			 PreparedStatement pstmt = dbConn.prepareStatement("INSERT INTO EMPLOYEE (emp) VALUES(?)");
			 ByteArrayInputStream bais = new ByteArrayInputStream(employeeAsBytes);
			 pstmt.setBinaryStream(1, bais, employeeAsBytes.length);
			 pstmt.executeUpdate();
			 pstmt.close();
			 Statement stmt = dbConn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT emp FROM Employee");
			 while (rs.next()) {
				 byte[] st = (byte[]) rs.getObject(1);
			     ByteArrayInputStream baip = new ByteArrayInputStream(st);
			     ObjectInputStream ois = new ObjectInputStream(baip);
			     Employee emp = (Employee) ois.readObject();
			 }
			 stmt.close();
			 rs.close();
			 dbConn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
