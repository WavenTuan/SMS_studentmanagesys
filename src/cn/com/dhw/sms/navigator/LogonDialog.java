package cn.com.dhw.sms.navigator;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.com.dhw.sms.db.DbOperate;
import cn.com.dhw.sms.model.IUser;
import cn.com.dhw.sms.system.Context;
import cn.com.dhw.sms.system.SmsFactory;

public class LogonDialog extends Dialog{
	private Text userIdText;
	private Text passwordText;

	public LogonDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected Control createDialogArea(Composite parent) {
		getShell().setText("用户登录");
		Composite topComp = new Composite(parent, SWT.NONE);
		topComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		topComp.setLayout(new GridLayout(2, false));

		new Label(topComp, SWT.NONE).setText("用户名：");
		userIdText = new Text(topComp, SWT.BORDER);
		userIdText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(topComp, SWT.NONE).setText("密码：");
		passwordText = new Text(topComp, SWT.BORDER);
		passwordText.setEchoChar('*');
		passwordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		return topComp;
	}
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			String userId = userIdText.getText().trim();
			String password = passwordText.getText().trim();
			if (userId == null || userId.equals("")) {
				MessageDialog.openWarning(getShell(), "", "用户名不能为空");
				userIdText.setFocus();
				return;
			}
			if (password == null || password.equals("")) {
				MessageDialog.openWarning(getShell(), "", "密码不能为空");
				passwordText.setFocus();
				return;
			}
			//	֤
			DbOperate db = SmsFactory.getDbOperate();
			IUser user = db.getUser(userId);
			if (user == null) {
				MessageDialog.openInformation(getShell(), "", "用户名不存在");
				userIdText.setFocus();
				userIdText.selectAll();//
				return;
			}
			String dbPassword = user.getPassword();
			if (dbPassword == null || !dbPassword.equals(password)) {
				MessageDialog.openInformation(getShell(), "", "密码错误");
				passwordText.setFocus();
				passwordText.selectAll();
				return;
			}
			Context.getInstance().setCurrentUser(user);//保存当前登录用户
		}
		super.buttonPressed(buttonId);
	}
}
