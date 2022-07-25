package cn.com.dhw.sms.system;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import cn.com.dhw.sms.Activator;

public abstract class EditorPartAdapter extends EditorPart implements ILogonListener{
	public void doSave(IProgressMonitor monitor) {}

	public void doSaveAs() {}

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
		Context.getInstance().addLogonListener(this);
	}
	
	@Override
	public void dispose() {
		Context.getInstance().removeLogonListener(this);
		super.dispose();
	}
	
	public void logoff() {
		IWorkbenchPage activePage = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (activePage != null) {
			// 关闭前尝试保存
			boolean success = activePage.closeEditor(this, true);
			if (!success) {
				if (MessageDialog.openConfirm(null, "", "保存编辑器内容失败，是否直接关闭"))
					activePage.closeEditor(this, false);
			}
		}
	}
	
	public void logon() {}

	public boolean isDirty() {
		return false;
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	public void setFocus() {}
}
