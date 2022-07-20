package cn.com.dhw.sms.navigator;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import cn.com.dhw.sms.model.ITreeEntry;
import cn.com.dhw.sms.system.LabelProviderAdapter;
import cn.com.dhw.sms.system.SmsFactory;
import cn.com.dhw.sms.system.TreeContentProviderAdapter;

public class NavigatorView extends ViewPart{
	public void createPartControl(Composite parent) {
		Composite topComp = new Composite(parent, SWT.None);
		topComp.setLayout(new FillLayout());
		
		TreeViewer tv = new TreeViewer(topComp, SWT.NONE);
		tv.setContentProvider(new MyContentProvider());//内容
		tv.setLabelProvider(new MyLableProvider());//标签
		
		tv.setInput(SmsFactory.createNavigatorEntryTree());
	}
	
	public void setFocus() {}
	
	//标签器
	private static final class MyLableProvider extends LabelProviderAdapter {
		public String getText(Object element) {
			return ((ITreeEntry) element).getName();
		}

		public Image getImage(Object element) {
			return ((NavigatorEntry) element).getImage();
		}
	}
	
	//内容器
	private static final class MyContentProvider extends TreeContentProviderAdapter {
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				return ((List) inputElement).toArray();
			} else {
				return new Object[0];//空数组
			}
		}

		public boolean hasChildren(Object element) {
			ITreeEntry entry = (ITreeEntry) element;
			return !entry.getChildren().isEmpty();
		}

		public Object[] getChildren(Object parentElement) {
			ITreeEntry entry = (ITreeEntry) parentElement;
			return entry.getChildren().toArray();
		}
	}
}
