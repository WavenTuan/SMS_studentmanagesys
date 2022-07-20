package cn.com.dhw.sms;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class ScorePerspective implements IPerspectiveFactory {

	@Override	
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		//加入主功能导航视图
		IFolderLayout left = layout.createFolder("left", IPageLayout.LEFT, 0.3f, editorArea);
		left.addView("cn.com.dhw.sms.navigator.NavigatorView");
		//加入搜索视图
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.5f, "left");
		bottom.addView("cn.com.dhw.sms.score.SearchView");
	}
}
