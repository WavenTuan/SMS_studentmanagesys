package cn.com.dhw.sms.archive.wizard;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import cn.com.dhw.sms.Constants;
import cn.com.dhw.sms.db.DbOperate;
import cn.com.dhw.sms.model.IUser;
import cn.com.dhw.sms.model.Student;
import cn.com.dhw.sms.model.Teacher;
import cn.com.dhw.sms.system.SmsFactory;

public class ArchiveWizard extends Wizard{
	private UserTypePage userTypePage;// 选择用户类型页
	private UserInfoPage userInfoPage;// 用户基本信息页
	private CoursesPage coursesPage;//老师所教课程设置页
	private SchoolClassPage schoolClassPage;// 选择学生所在班级页
	private IUser user;

	//改写父类方法，加入页面
	public void addPages() {
		//创建页面对象，并设置名称
		userTypePage = new UserTypePage("userTypePage");
		userInfoPage = new UserInfoPage("userInfoPage");
		coursesPage = new CoursesPage("coursesPage");
		schoolClassPage = new SchoolClassPage("schoolClassPage");
		addPage(userTypePage);
		addPage(userInfoPage);
		addPage(coursesPage);
		addPage(schoolClassPage);
	}

	// 设置完成方法
	public boolean canFinish() {
		IWizardPage page = getContainer().getCurrentPage();
		if (page != coursesPage && page != schoolClassPage)
			return false;
		return super.canFinish();
	}

	// 判断当前页面得下一页
	public IWizardPage getNextPage(IWizardPage page) {
		//使用usertype判定下一页是什么
		if (page == userInfoPage) {
			String type = userTypePage.getUserType();
			if (type.equals(Constants.IUSER_TEACHER_TYPE))
				return coursesPage;
			else if (type.equals(Constants.IUSER_STUDENT_TYPE))
				return schoolClassPage;
		}
		// coursesPage，schoolClassPage页都没有下一页，所以返回null
		if (page == coursesPage || page == schoolClassPage)
			return null;
		return super.getNextPage(page);
	}

	// 更新数据到user对象中
	public boolean performFinish() {
		String type = userTypePage.getUserType();
		if (type.equals(Constants.IUSER_TEACHER_TYPE)) {
			Teacher o = new Teacher();
			coursesPage.getValue(o);
			user = o;
		} else if (type.equals(Constants.IUSER_STUDENT_TYPE)) {
			Student o = new Student();
			schoolClassPage.getValue(o);
			user = o;
		}
		userInfoPage.getValue(user);
		// 插入数据库前检查是否同名
		DbOperate db = SmsFactory.getDbOperate();
		IUser o = db.getUser(user.getUserId());
		if (o != null) {
			MessageDialog.openError(null, "", "用户名已存在！");
			return false;
		}
		return true;
	}

	//得到用户对象
	public IUser getUser() {
		return user;
	}
}
