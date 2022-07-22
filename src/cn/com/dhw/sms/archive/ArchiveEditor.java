package cn.com.dhw.sms.archive;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.ToolBar;

import cn.com.dhw.sms.model.Course;
import cn.com.dhw.sms.model.IUser;
import cn.com.dhw.sms.model.SchoolClass;
import cn.com.dhw.sms.model.Student;
import cn.com.dhw.sms.model.Teacher;
import cn.com.dhw.sms.system.EditorPartAdapter;
import cn.com.dhw.sms.system.SmsContentProvider;
import cn.com.dhw.sms.system.TableLabelProviderAdapter;

@SuppressWarnings("deprecation")
public class ArchiveEditor extends EditorPartAdapter{
	private TableViewer tv;

	public void createPartControl(Composite parent) {
		ViewForm topComp = new ViewForm(parent, SWT.NONE);
		topComp.setLayout(new FillLayout());
		createTableViewer(topComp);// 自定义一个方法：创建一个table_viewer表格。
		tv.setContentProvider(new SmsContentProvider());// 内容器
		tv.setLabelProvider(new TableViewerLabelProvider());//标签器
		// 工具栏
		ArchiveEditorActionGroup actionGroup = new ArchiveEditorActionGroup(tv);
		ToolBar toolBar = new ToolBar(topComp, SWT.FLAT);
		ToolBarManager toolBarManager = new ToolBarManager(toolBar);
		actionGroup.fillActionToolBars(toolBarManager);
		// 设置表格和工具栏在视图中的位置
		topComp.setContent(tv.getControl()); // 主体：表格
		topComp.setTopLeft(toolBar); // 顶边：工具栏
		actionGroup.fireFirstAction();// 首页按钮
	}

	// 创建表格的自定义方法
	private void createTableViewer(Composite parent) {
		tv = new TableViewer(parent, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		Table table = tv.getTable();
		table.setHeaderVisible(true); // 显示表头
		table.setLinesVisible(true); // 显示表格线
		table.setLayout(new TableLayout());// 专用于表格的布局
		//表格建列
		createColumn(10, "ID");
		createColumn(20, "用户名");
		createColumn(20, "密码");// 无排序
		createColumn(20, "姓名");// 无排序
		createColumn(20, "班级");
		createColumn(20, "课程");// 无排序
		createColumn(60, "最后登陆时间");
	}

	// 表格列的自定义方法
	private TableColumn createColumn(int weight, String name) {
		Table table = tv.getTable();
		TableLayout layout = (TableLayout) table.getLayout();
		layout.addColumnData(new ColumnWeightData(weight));
		TableColumn col = new TableColumn(table, SWT.NONE);
		col.setText(name);
		return col;
	}

	//标签器
	private static final class TableViewerLabelProvider extends TableLabelProviderAdapter {
		public String getColumnText(Object element, int col) {
			// String result = "";
			IUser o = (IUser) element;
			switch (col) {
			case 0:
				return o.getId().toString();
			case 1:
				return o.getUserId();
			case 2:
				return o.getPassword();
			case 3:
				return o.getName();
			case 4:
				if (element instanceof Student) {
					SchoolClass s = ((Student) o).getSchoolclass();
					if (s != null)
						return s.getName();
				}
				return "";
			case 5:
				StringBuilder sb = new StringBuilder();
				if (element instanceof Teacher) {
					Set<Course> set = ((Teacher) o).getCourses();
					for (Iterator it = set.iterator(); it.hasNext();) {
						Course course = (Course) it.next();
						sb.append(course.getName());
						if (it.hasNext())
							sb.append(", ");
					}
				}
				return sb.toString();
			case 6:
				Date date = o.getLatestOnline();
				return date == null ? "" : date.toString();
			default:
				return "";
			}
		}
	}
}
