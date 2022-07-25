package cn.com.dhw.sms.archive.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import cn.com.dhw.sms.Constants;

public class UserTypePage extends WizardPage{
	private Combo combo;
	
	protected UserTypePage(String pageName) {
		super(pageName);
	}
	
	public void createControl(Composite parent) {
		setTitle("添加用户");
		setMessage("选择用户类型", INFORMATION);
		Composite topComp = new Composite(parent, SWT.NULL);
		topComp.setLayout(new GridLayout());
		
		Composite c = new Composite(topComp, SWT.NULL);
		c.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, true));
		c.setLayout(new RowLayout());
		new Label(c, SWT.NONE).setText("用户类型：");
		combo = new Combo(c, SWT.BORDER);
		combo.setLayoutData(new RowData(50,-1));//设定combo得宽度
		combo.add("老师");
		combo.add("学生");
		//
		combo.setData("老师", Constants.IUSER_TEACHER_TYPE);
		combo.setData("学生", Constants.IUSER_STUDENT_TYPE);
		combo.select(0);
		setControl(topComp);
	}
	
	//得到用户的类型
	public String getUserType() {
		String key = combo.getText();
		return combo.getData(key) + "";
	}
}
