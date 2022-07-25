package cn.com.dhw.sms.archive.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import cn.com.dhw.sms.model.Course;
import cn.com.dhw.sms.model.Teacher;
import cn.com.dhw.sms.system.CourseComposite;

public class CoursesPage extends WizardPage{
	private CourseComposite courseComp; //�γ����

    protected CoursesPage(String pageName) {
        super(pageName);
    }

    public void createControl(Composite parent) {
        setTitle("添加用户");
        setMessage("输入教师所教课程", INFORMATION);
        Composite topComp = new Composite(parent, SWT.NONE);
        topComp.setLayout(new GridLayout());
        courseComp = new CourseComposite(topComp, SWT.NONE);
        setControl(topComp);
    }

    //将面板中的值更新传入Teacher对象
    public void getValue(Teacher teacher) {
    	teacher.clearCourses();
        for (String key : courseComp.getItems()) {
            Course course = courseComp.getData(key);
            teacher.addCourse(course);
        }
    }
}
