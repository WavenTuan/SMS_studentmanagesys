package cn.com.dhw.sms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTestTables {
	public static void main(String[] args) {
		Connection con = null;
		Statement sm = null;
		try {
			// 装载JDBC驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 连接数据库
			con = DriverManager.getConnection("jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf8", "root", "123456");
			// 创建语句对象
			sm = con.createStatement();
			// 如果sms已经存在则先删除后创建
			sm.addBatch("DROP DATABASE IF EXISTS sms;");
			sm.addBatch("CREATE DATABASE sms;");
			// 转移当前库到sms
			sm.addBatch("USE sms;");
			// 如果存在test_table则先删除后创建
			sm.addBatch("DROP TABLE IF EXISTS test_table;");
			// get一下创建数据表的SQL语句
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE test_table (");// ����test_table��
			sb.append("  Id int(6) unsigned NOT NULL auto_increment,");
			sb.append("  name0 varchar(4) default NULL,");
			sb.append("  name1 varchar(4) default NULL,");
			sb.append("  name2 varchar(4) default NULL,");
			sb.append("  name3 varchar(4) default NULL,");
			sb.append("  PRIMARY KEY  (Id)");
			sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");
			sm.addBatch(sb.toString());
			// 提交并执行
			sm.executeBatch();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 断链，释放资源
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
