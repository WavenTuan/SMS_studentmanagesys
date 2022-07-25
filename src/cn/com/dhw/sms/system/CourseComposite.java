package cn.com.dhw.sms.system;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;

import cn.com.dhw.sms.model.Course;

public class CourseComposite {
	private Group group;
	private List courseList; //此处的List是SWT组件

	public CourseComposite(Composite parent, int style) {
		createCourseComp(parent, style);
	}

	// �����γ����
	private Composite createCourseComp(Composite comp, int style) {
		group = new Group(comp, style);
		group.setText("课程");
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		group.setLayout(new GridLayout(2, false));
		// 创建课程面板组件
		courseList = new List(group, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		courseList.setLayoutData(new GridData(GridData.FILL_BOTH));
		// 命令按钮面板
		Composite cmdComp = new Composite(group, SWT.NONE);
		cmdComp.setLayout(new RowLayout(SWT.VERTICAL));
		Button addButton = new Button(cmdComp, SWT.NONE);
		addButton.setText("增加");
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				CourseDialog dialog = new CourseDialog(null);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Course course = dialog.getCourse();
					add(course);
				}
			}
		});
		Button removeButton = new Button(cmdComp, SWT.NONE);
		removeButton.setText("删除");
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (courseList.getSelection().length != 0) {
					String sel = courseList.getSelection()[0];
					courseList.remove(sel);
				}
			}
		});
		return group;
	}

	// 隐藏方法
	public void setVisible(boolean enabled) {
		group.setVisible(false);
	}

	// 往list中加入一个课程
	public void add(Course course) {
		if (courseList.indexOf(course.getName()) < 0) {
			String name = course.getName();
			courseList.add(name);
			courseList.setData(name, course);
		} else {
			MessageDialog.openError(null, "", "课程已存在！");
		}
	}

	// 取得课程名
	public String[] getItems() {
		return courseList.getItems();
	}

	// 缺的课程名（key）对应的课程
	public Course getData(String key) {
		return (Course) courseList.getData(key);
	}
}
