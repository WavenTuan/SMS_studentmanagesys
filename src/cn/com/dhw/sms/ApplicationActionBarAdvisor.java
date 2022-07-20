package cn.com.dhw.sms;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	private IWorkbenchAction quitAction;//退出
	private IWorkbenchAction aboutAction;//关于
	private IWorkbenchAction newWindowAction;//打开新窗口
	private IAction scorePersAction;//成绩管理透视图

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}
	
	@Override
	protected void makeActions(IWorkbenchWindow window) {
		quitAction = ActionFactory.QUIT.create(window);
		aboutAction = ActionFactory.ABOUT.create(window);
		newWindowAction = ActionFactory.OPEN_NEW_WINDOW.create(window);
		scorePersAction = new ScorePerspectiveAction();
		
		register(quitAction);
		register(aboutAction);
		register(newWindowAction);
		register(scorePersAction);
		//super.makeActions(window);
	}
	
	@Override
	protected void fillMenuBar(IMenuManager menuBar) {
		IMenuManager fileMenu = new MenuManager("文件(&F)", IWorkbenchActionConstants.M_FILE);
		IMenuManager persMenu = new MenuManager("透视图(&P)");
		IMenuManager helpMenu = new MenuManager("帮助(&H)", IWorkbenchActionConstants.M_HELP);
		menuBar.add(fileMenu);
		menuBar.add(persMenu);
		menuBar.add(helpMenu);
		
		fileMenu.add(newWindowAction);
		fileMenu.add(new Separator());//分隔符
		fileMenu.add(quitAction);
		persMenu.add(scorePersAction);
		helpMenu.add(aboutAction);
		//super.fillMenuBar(menuBar);
	}
	
	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(SWT.FLAT | SWT.RIGHT);
		coolBar.add(new ToolBarContributionItem(toolbar, "main"));
		toolbar.add(scorePersAction);
		toolbar.add(quitAction);
		//super.fillCoolBar(coolBar);
	}
	
	private static class ScorePerspectiveAction extends Action{
		public ScorePerspectiveAction() {
			setId("ACTIONS_SCORE_PERSPECTIVE");
			setText("成绩管理");
			setImageDescriptor(Activator.getImageDescriptor("icons/eclipse16.png"));
			setActionDefinitionId("cn.com.dhw.sms.score_perspective");
		}
		
		public void run() {
			Activator.showPerspective(Constants.SCORE_PERSPECTIVE_ID);
		}
	}

}

