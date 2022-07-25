package cn.com.dhw.sms.archive.wizard;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import cn.com.dhw.sms.model.IUser;
import cn.com.dhw.sms.system.SmsFactory;

public class UserInfoPage extends WizardPage implements ModifyListener{
	private Text useridText; // 用户名
	private Text passwordText; // 密码
	private Text nameText; // 姓名

	protected UserInfoPage(String pageName) {
		super(pageName);
	}

	public void createControl(Composite parent) {
		setTitle("添加用户");
		setMessage("输入用户基本信息", IMessageProvider.INFORMATION);
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, true));
		//创建界面组件
		topComp.setLayout(new GridLayout(3, false));// 三格的网格布局
		new Label(topComp, SWT.NONE).setText("用户名：");
		useridText = createText(topComp, SWT.BORDER);
		useridText.addModifyListener(this);
		Button checkButton = new Button(topComp, SWT.NONE);
		checkButton.setText("检查唯一性");
		checkButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String userid = useridText.getText();
				if (!userid.trim().equals("")) {//文本框传出值不需要做null判定
					if (SmsFactory.getDbOperate().getUser(userid) == null) {
						MessageDialog.openInformation(null, "", "ͨ通过");
					} else {
						MessageDialog.openError(null, "", "此用户名已存在");
						useridText.forceFocus();// 获取焦点
						useridText.selectAll();// 全选
					}
				}
			}
		});

		new Label(topComp, SWT.NONE).setText("密码：");
		passwordText = createText(topComp, SWT.BORDER);
		passwordText.addModifyListener(this);
		new Label(topComp, SWT.NONE);

		new Label(topComp, SWT.NONE).setText("姓名：");
		nameText = createText(topComp, SWT.BORDER);
		new Label(topComp, SWT.NONE);
		nameText.addModifyListener(this);

		setPageComplete(false);
		setControl(topComp);
	}

	// 获取一个固定长度的Text框
	private Text createText(Composite comp, int style) {
		Text text = new Text(comp, style);
		text.setLayoutData(new GridData(100, -1));
		return text;
	}

	// 文本修改事件
	public void modifyText(ModifyEvent e) {
		setPageComplete(false);
		if (useridText.getText().trim().equals("")) {
			setErrorMessage("必须填写用户名！");// 进行错误信息提示
			return;
		}
		if (passwordText.getText().trim().equals("")) {
			setErrorMessage("必须填写密码！");// 同上
			return;
		}
		if (nameText.getText().trim().equals("")) {
			setErrorMessage("必须填写姓名！");// 同上
			return;
		}
		setErrorMessage(null); // 消除错误提醒
		setPageComplete(true);
	}

	//界面值注入Teacher对象中
	public void getValue(IUser user) {
		user.setUserId(useridText.getText());
		user.setPassword(passwordText.getText());
		user.setName(nameText.getText());
	}
}
