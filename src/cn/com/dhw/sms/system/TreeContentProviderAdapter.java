package cn.com.dhw.sms.system;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public abstract class TreeContentProviderAdapter implements ITreeContentProvider {
	public Object getParent(Object element) {return null;}
	public void dispose() {}
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {}
}
