package cn.com.dhw.sms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertAndSelect {
	public static void main(String[] args) {
		Connection con = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/sms?useUnicode=true&characterEncoding=utf8", "root", "123456");
			sm = con.createStatement();
			//清空表，插入如下记录，打印结果
			sm.executeUpdate("delete from test_table");
			//插入记录
			String s = "段惠文";
			sm.executeUpdate("insert into test_table (name0) values ('" + s + "')");
			sm.executeUpdate("insert into test_table (name1) values ('" + s + "')");
			sm.executeUpdate("insert into test_table (name2) values ('" + s + "')");
			sm.executeUpdate("insert into test_table (name3) values ('" + s + "')");
			//读取记录
			rs = sm.executeQuery("select * from test_table");
			while (rs.next())
				System.out.println(rs.getInt("id") + "_" + rs.getString("name0") + "_" + rs.getString("name1") + "_" + rs.getString("name2") + "_" + rs.getString("name3"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {//断链，释放资源
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs = null;
			}
			if (sm != null) {
				try {
					sm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sm = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				con = null;
			}
		}
	}
}
