package cn.com.dhw.sms.archive;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.actions.ActionGroup;

import cn.com.dhw.sms.Constants;
import cn.com.dhw.sms.archive.wizard.ArchiveWizard;
import cn.com.dhw.sms.db.DbOperate;
import cn.com.dhw.sms.db.QueryInfo;
import cn.com.dhw.sms.model.IUser;
import cn.com.dhw.sms.system.ImagesContext;
import cn.com.dhw.sms.system.SmsFactory;

public class ArchiveEditorActionGroup extends ActionGroup{
	private TableViewer tv;
	private DbOperate db = SmsFactory.getDbOperate();
	private QueryInfo queryInfo = new QueryInfo();

	private Action addAction = new AddAction();
	private Action modifyAction = new ModifyAction();
	private Action removeAction = new RemoveAction();
	private Action firstAction = new FirstAction();
	private Action prevAction = new PrevAction();
	private Action nextAction = new NextAction();
	private Action lastAction = new LastAction();

	public ArchiveEditorActionGroup(TableViewer tv) {
		this.tv = tv;
		queryInfo.currentPage = 1;
		queryInfo.pageSize = Constants.ARCHIVE_EDITOR_RS_NUM;
	}

	public void fillActionToolBars(ToolBarManager toolBarManager) {
		toolBarManager.add(createActionContrItem(addAction));
		toolBarManager.add(createActionContrItem(modifyAction));
		toolBarManager.add(createActionContrItem(removeAction));
		toolBarManager.add(createActionContrItem(firstAction));
		toolBarManager.add(createActionContrItem(prevAction));
		toolBarManager.add(createActionContrItem(nextAction));
		toolBarManager.add(createActionContrItem(lastAction));
		//
		toolBarManager.update(true);
	}

	//
	private ActionContributionItem createActionContrItem(IAction action) {
		ActionContributionItem aci = new ActionContributionItem(action);
		aci.setMode(ActionContributionItem.MODE_FORCE_TEXT);//
		return aci;
	}

	private class AddAction extends Action {
		public AddAction() {
			setText("新增");
			setHoverImageDescriptor(ImagesContext.getImageDescriptor("REPORT"));
		}

		public void run() {
			ArchiveWizard wizard = new ArchiveWizard();
			WizardDialog dialog = new WizardDialog(null, wizard);
			dialog.setPageSize(-1, 120); // 
			if (dialog.open() == IDialogConstants.OK_ID) {
				IUser user = wizard.getUser();
				if (db.insertUser(user)) {
					MessageDialog.openInformation(null, "", "成功插入");
					IUser o = db.getUser(user.getUserId());
					tv.add(o);
					List list = (List) tv.getInput();
					list.add(o);
				} else {
					MessageDialog.openError(null, "", "记录插入失败");
				}
			}
		}
	}

	private class ModifyAction extends Action {
		public ModifyAction() {
			setText("修改");
			setHoverImageDescriptor(ImagesContext.getImageDescriptor("NOTE"));
		}

		public void run() {}
	}

	private class RemoveAction extends Action {
		public RemoveAction() {
			setText("删除");
			setHoverImageDescriptor(ImagesContext.getImageDescriptor("REMOVE"));
		}

		public void run() {
			IStructuredSelection sel = (IStructuredSelection) tv.getSelection();
			IUser user = (IUser) sel.getFirstElement();
			if (user == null)
				return;
			if (MessageDialog.openConfirm(null, null, "确认删除吗？")) {
				if (db.removeUser(user)) {
					//从表格界面删除，同时从list源头删除
					//否则被删除得数据会在tv.refresh得时候再次被加载
					tv.remove(user);
					List list = (List) tv.getInput();
					list.remove(user);
				} else {
					MessageDialog.openConfirm(null, null, "删除失败！");
				}
			}
		}
	}

	private class FirstAction extends Action {
		public FirstAction() {
			setText("首页");
			setHoverImageDescriptor(ImagesContext.getImageDescriptor("FIRST"));
		}

		public void run() {
			queryInfo.currentPage = 1;
			tv.setInput(db.getUsers(queryInfo));
			refreshActionsState();
		}
	}

	private class PrevAction extends Action {
		public PrevAction() {
			setText("上一页");
			setHoverImageDescriptor(ImagesContext.getImageDescriptor("PREV"));
		}

		public void run() {
			queryInfo.currentPage = queryInfo.currentPage - 1;
			tv.setInput(db.getUsers(queryInfo));
			refreshActionsState();
		}
	}

	private class NextAction extends Action {
		public NextAction() {
			setText("下一页");
			setHoverImageDescriptor(ImagesContext.getImageDescriptor("NEXT"));
		}

		public void run() {
			queryInfo.currentPage = queryInfo.currentPage + 1;
			tv.setInput(db.getUsers(queryInfo));
			refreshActionsState();
		}
	}

	private class LastAction extends Action {
		public LastAction() {
			setText("末页");
			setHoverImageDescriptor(ImagesContext.getImageDescriptor("LAST"));
		}

		public void run() {
			queryInfo.currentPage = queryInfo.pageCount;
			tv.setInput(db.getUsers(queryInfo));
			refreshActionsState();
		}
	}

	// 翻页按钮有效判定
	private void refreshActionsState() {
		if (queryInfo.pageCount == 0) {//无记录时
			firstAction.setEnabled(false);
			prevAction.setEnabled(false);
			nextAction.setEnabled(false);
			lastAction.setEnabled(false);
		} else {
			//第一页时
			boolean b = (queryInfo.currentPage == 1);
			firstAction.setEnabled(!b);
			prevAction.setEnabled(!b);
			//最后一页时
			b = (queryInfo.currentPage == queryInfo.pageCount);
			lastAction.setEnabled(!b);
			nextAction.setEnabled(!b);
		}
	}

	//提供给外界的首页按钮方法
	public void fireFirstAction() {
		firstAction.run();
	}
}
