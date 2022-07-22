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
				user.setId(new Long(rs.getLong("id")));
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
}
