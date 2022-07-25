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

import cn.com.dhw.sms.model.SchoolClass;
import cn.com.dhw.sms.model.Student;
import cn.com.dhw.sms.system.SmsUtil;

public class SchoolClassPage extends WizardPage{
	private Combo combo;

	protected SchoolClassPage(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		setTitle("添加用户");
		setMessage("选择班级", INFORMATION);
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayout(new GridLayout());

		Composite c = new Composite(topComp, SWT.NONE);
		c.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, true));
		c.setLayout(new RowLayout());
		new Label(c, SWT.NONE).setText("班级：");
		// 创建一个包含所有班级的combo
		combo = SmsUtil.createSchoolClassCombo(c, SWT.BORDER | SWT.READ_ONLY);
		combo.setLayoutData(new RowData(100, -1));// 设定Combo宽度
		combo.select(0);
		setControl(topComp);
	}

	public void getValue(Student student) {
		String key = combo.getText();
		SchoolClass sc = (SchoolClass) combo.getData(key);
		student.setSchoolclass(sc);
	}
}
