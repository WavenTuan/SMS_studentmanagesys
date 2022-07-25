package cn.com.dhw.sms.system;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import cn.com.dhw.sms.db.DbOperate;
import cn.com.dhw.sms.model.Course;
import cn.com.dhw.sms.model.SchoolClass;

public class SmsUtil {
	private SmsUtil() {}//private型构造，防止创建工具类

	public static Combo createSchoolClassCombo(Composite parent, int style) {
		Combo combo = new Combo(parent, style);
		DbOperate db = SmsFactory.getDbOperate();
		for (SchoolClass sc : db.getAllSchoolClass()) {
			String name = sc.getName();
			combo.add(name);
			combo.setData(name, sc);
		}
		return combo;
	}
	
	public static Combo createCourseCombo(Composite parent, int style) {
		Combo combo = new Combo(parent, style);
		DbOperate db = SmsFactory.getDbOperate();
		for (Course course : db.getCourses()) {
			String name = course.getName();
			combo.add(name);
			combo.setData(name, course);
		}
		return combo;
	}
}
