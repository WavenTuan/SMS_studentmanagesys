package cn.com.dhw.sms;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }
    
    @Override
    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    @Override
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 600));
        
        configurer.setShowCoolBar(true);//显示工具栏
        configurer.setShowStatusLine(true);//显示状态栏
        
        configurer.setShowPerspectiveBar(true);//显示perspectives
        configurer.setShowProgressIndicator(true);//显示状态栏上的进度指示器
        configurer.setShowMenuBar(true);//显示主菜单
        
        configurer.setTitle("dhw's学生数据管理系统SMS-StudentsManageSys"); //$NON-NLS-1$
    }
}
