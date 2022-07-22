package cn.com.dhw.sms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectManager {
	private static Connection con;

	private ConnectManager() {}

	public static Connection getConnection() throws SQLException {
		if (con != null && !con.isClosed())//
			return con;
		//
		String className = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost/sms";
		String username = "root";
		String password = "123456";
		//
		try {
			Class.forName(className);
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	//
	public static void closeConnection() {
		if (con == null)
			return;
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		con = null;
	}
}
