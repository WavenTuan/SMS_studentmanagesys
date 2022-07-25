package cn.com.dhw.sms.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.com.dhw.sms.Constants;
import cn.com.dhw.sms.model.Course;
import cn.com.dhw.sms.model.Grade;
import cn.com.dhw.sms.model.IUser;
import cn.com.dhw.sms.model.SchoolClass;
import cn.com.dhw.sms.model.Student;
import cn.com.dhw.sms.model.Teacher;

public class DbOperate {
	public IUser getUser(String userId) {
		Connection con = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			con = ConnectManager.getConnection();
			sm = con.createStatement();
			rs = sm.executeQuery("select * from iuser where userId='" + userId + "'");
			if (rs.next()) {
				IUser user = createUserFromRs(rs);
				//
				user.setId((long)rs.getLong("id"));
				user.setUserId(userId);
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setLatestOnline(rs.getDate("latestOnline"));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(sm);
			close(con);
		}
		return null;
	}
	
	public List<IUser> getUsers(QueryInfo qi) {
		Connection con = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			con = ConnectManager.getConnection();
			sm = con.createStatement();
			//
			rs = sm.executeQuery("select count(id) from iuser");
			rs.next();
			qi.rsCount = rs.getInt(1);
			if (qi.rsCount == 0)//
				return Collections.emptyList();
			//
			if (qi.rsCount % qi.pageSize == 0)
				qi.pageCount = qi.rsCount / qi.pageSize;
			else
				qi.pageCount = (qi.rsCount / qi.pageSize) + 1;
			//
			int start = (qi.currentPage - 1) * qi.pageSize;
			rs = sm.executeQuery("select * from iuser limit " + start + "," + qi.pageSize);
			List<IUser> list = new ArrayList<IUser>(qi.pageSize);
			while (rs.next()) {
				IUser user = createUserFromRs(rs);
				user.setId((long)rs.getLong("id"));
				user.setUserId(rs.getString("userid"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setLatestOnline(rs.getDate("latestOnline"));
				list.add(user);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(sm);
			close(con);
		}
		return Collections.emptyList();
	}
	
	private IUser createUserFromRs(ResultSet rs) throws SQLException {
		String type = rs.getString("type");//
		if (type.equalsIgnoreCase(Constants.IUSER_TEACHER_TYPE)) {
			Teacher o = new Teacher();
			Long iuser_id = (long)rs.getLong("id");
			o.setCourses(getCourses(iuser_id));
			return o;
		} else if (type.equalsIgnoreCase(Constants.IUSER_STUDENT_TYPE)) {
			Student o = new Student();
			Long schoolclass_id = (long)rs.getLong("schoolclass_id");
			o.setSchoolclass(getSchoolclass(schoolclass_id));
			return o;
		}
		return null;
	}
	
	public Set<Course> getCourses(Long iuser_id) {
		Connection con = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			con = ConnectManager.getConnection();
			sm = con.createStatement();
			//由用户课程连接表得到该用户所有课程idֵ
			String subSql = "select course_id from iuser_course WHERE iuser_id=" + iuser_id;
			//得到id对应的课程记录
			String sql = "SELECT * from course where id in (" + subSql + ")";
			rs = sm.executeQuery(sql);
			Set<Course> set = new HashSet<Course>();
			while (rs.next()) {
				Course course = new Course();
				course.setId((long)rs.getInt("id"));
				course.setName(rs.getString("name"));
				set.add(course);
			}
			return set;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(sm);
			close(con);
		}
		return Collections.emptySet();
	}
	
	public SchoolClass getSchoolclass(Long id) {
		Connection con = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			con = ConnectManager.getConnection();
			sm = con.prepareStatement("SELECT * from schoolclass where id=" + id);
			rs = sm.executeQuery();
			if (rs.next()) {
				SchoolClass schoolClass = new SchoolClass();
				schoolClass.setId((long)rs.getInt("id"));
				schoolClass.setName(rs.getString("name"));
				{//
					Grade grade = new Grade();
					grade.setId((long)rs.getInt("grade_id"));
					schoolClass.setGrade(grade);
				}
				return schoolClass;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(sm);
			close(con);
		}
		return null;
	}
	
	void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	}

	//
	void close(Statement sm) {
		if (sm != null) {
			try {
				sm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			sm = null;
		}
	}

	//
	void close(Connection con) {}
	
	public boolean removeUser(IUser user) {
		Connection con = null;
		Statement sm = null;
		ResultSet rs = null;
		boolean result = true;
		try {
			// setAuto用以设置是否自动提交事务
			//commit用以提交事务
			//roll_back用以撤销事务
			// 以上为控制事务得逻辑方法
			con = ConnectManager.getConnection();
			con.setAutoCommit(false); // 禁止自动提交事务
			sm = con.createStatement();
			sm.addBatch("delete from iuser where id=" + user.getId());
			if (user instanceof Teacher)//教师用户得删除还需要后台进行其课程连接表得删除
				sm.addBatch("delete from iuser_course where iuser_id=" + user.getId());
			sm.executeBatch();
			con.commit(); // 提交事务
		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
			try {
				con.rollback(); //删除异常时则马上进行回滚
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			close(rs);
			close(sm);
			close(con);
		}
		return result;
	}
	
	public boolean insertUser(IUser user) {
		Connection con = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			con = ConnectManager.getConnection();
			if (getUser(user.getUserId()) != null)//检查userID是否重名
				return false;
			String sql = "insert into iuser (type,userid,password,name,schoolclass_id) VALUES (?,?,?,?,?);";
			sm = con.prepareStatement(sql);
			sm.setString(2, user.getUserId());
			sm.setString(3, user.getPassword());
			sm.setString(4, user.getName());
			if (user instanceof Student) {
				sm.setString(1, Constants.IUSER_STUDENT_TYPE);
				SchoolClass schoolClass = ((Student) user).getSchoolclass();
				if (schoolClass == null)
					sm.setNull(5, java.sql.Types.BIGINT);
				else
					sm.setInt(5, schoolClass.getId().intValue());
				sm.execute();
			} else if (user instanceof Teacher) {
				sm.setString(1, Constants.IUSER_TEACHER_TYPE);
				sm.setNull(5, java.sql.Types.BIGINT);
				sm.execute();//将用户表记录插入
				rs = sm.executeQuery("select id from iuser where userid='" + user.getUserId() + "'");
				rs.next();
				int iuser_id = rs.getInt(1);
				//对于老师用户需要处理其课程情况
				for (Course course : ((Teacher) user).getCourses()) {
					sql = "insert into iuser_course values (" + iuser_id + "," + course.getId() + ")";
					sm.addBatch(sql);
				}
				sm.executeBatch();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback(); //经典异常回滚
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			close(rs);
			close(sm);
			close(con);
		}
		return false;
	}

	public List<SchoolClass> getAllSchoolClass() {
		Connection con = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			con = ConnectManager.getConnection();
			sm = con.prepareStatement("SELECT * from schoolclass");
			rs = sm.executeQuery();
			List<SchoolClass> list = new ArrayList<SchoolClass>();
			while (rs.next()) {
				SchoolClass schoolClass = new SchoolClass();
				schoolClass.setId((long)rs.getInt("id"));
				schoolClass.setName(rs.getString("name"));
				// 设置年纪属性
				Grade grade = new Grade();
				grade.setId(new Long(rs.getInt("grade_id")));
				schoolClass.setGrade(grade);
				list.add(schoolClass);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(sm);
			close(con);
		}
		return Collections.emptyList();
	}
	
	public List<Course> getCourses() {
		Connection con = null;
		PreparedStatement sm = null;
		ResultSet rs = null;
		try {
			con = ConnectManager.getConnection();
			String sql = "SELECT * from course";
			sm = con.prepareStatement(sql);
			rs = sm.executeQuery();
			List<Course> list = new ArrayList<Course>();
			while (rs.next()) {
				Course course = new Course();
				course.setId(new Long(rs.getInt("id")));
				course.setName(rs.getString("name"));
				list.add(course);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(sm);
			close(con);
		}
		return Collections.emptyList();
	}
}
