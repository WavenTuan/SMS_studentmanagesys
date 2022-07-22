package cn.com.dhw.sms.navigator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;

import cn.com.dhw.sms.system.Context;
import cn.com.dhw.sms.system.ILogonListener;
import cn.com.dhw.sms.system.ImagesContext;

public class NavigatorActionGroup extends ActionGroup{
	private Action logonAction = new LogonAction();
	private Action logoffAction = new LogoffAction();
	private Context ctx = Context.getInstance();
	private ILogonListener logonListener = new MyLogonListener();

	public NavigatorActionGroup() {
		ctx.addLogonListener(logonListener);
		setActionsEnabled(ctx.isLogon());//有效状态判定״̬
	}

	private void setActionsEnabled(boolean logon) {
		logonAction.setEnabled(!logon);
		logoffAction.setEnabled(logon);
	}

	@Override
	public void dispose() {
		ctx.removeLogonListener(logonListener);
		super.dispose();
	}

	//加入视图工具栏按钮
	@Override
	public void fillActionBars(IActionBars actionBars) {
		IToolBarManager toolBar = actionBars.getToolBarManager();
		toolBar.add(logonAction);
		toolBar.add(logoffAction);
	}

	// 登录按钮
	private class LogonAction extends Action {
		public LogonAction() {
			setText("登录");
			setHoverImageDescriptor(ImagesContext.getImageDescriptor(ImagesContext.LOGON));
		}

		public void run() {
			//打开登陆窗口验证用户密码
			LogonDialog dialog = new LogonDialog(null);
			if (dialog.open() == IDialogConstants.OK_ID) {
				ctx.fireLogonEvent();//进行登录通知
				//String s = "��¼��" + Context.getInstance().getCurrentUser().getName();
				//Activator.getDefault().getStatusLine().setMessage(s);
			}
		}
	}

	// 退出按钮
	private class LogoffAction extends Action {
		public LogoffAction() {
			setText("退出");
			setHoverImageDescriptor(ImagesContext.getImageDescriptor(ImagesContext.LOGOFF));
		}

		public void run() {
			ctx.fireLogoffEvent();//进行退出通知
			//Activator.getDefault().getStatusLine().setMessage("");
		}
	}

	// 设置监听器，决定按钮的有效状态״̬
	private class MyLogonListener implements ILogonListener {
		public void logon() {
			setActionsEnabled(true);
		}

		public void logoff() {
			setActionsEnabled(false);
		}
	}
}
