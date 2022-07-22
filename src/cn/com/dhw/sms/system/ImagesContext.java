package cn.com.dhw.sms.system;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import cn.com.dhw.sms.Activator;

public class ImagesContext {
	private final static String ICONS_PATH = "icons/";

	public final static String EDITING = "EDITING";
	public final static String NOTE = "NOTE";
	public final static String REPORT = "REPORT";
	public final static String STUDENT = "STUDENT";
	public final static String SYSCONFIG = "SYSCONFIG";
	public final static String USER = "USER";
	public final static String LOGON = "LOGON";
	public final static String LOGOFF = "USER";
	public final static String REMOVE = "REMOVE";
	public final static String NEXT = "NEXT";
	public final static String PREV = "PREV";
	public final static String FIRST = "FIRST";
	public final static String LAST = "LAST";

	//
	private static ImageRegistry imageRegistry;
	public static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			imageRegistry = new ImageRegistry();
			declareImages();//
		}
		return imageRegistry;
	}

	private final static void declareImages() {
		declareRegistryImage(EDITING, ICONS_PATH + "editing.gif");
		declareRegistryImage(NOTE, ICONS_PATH + "note.gif");
		declareRegistryImage(REPORT, ICONS_PATH + "report.gif");
		declareRegistryImage(STUDENT, ICONS_PATH + "student.gif");
		declareRegistryImage(SYSCONFIG, ICONS_PATH + "sysconfig.gif");
		declareRegistryImage(USER, ICONS_PATH + "user.gif");
		declareRegistryImage(LOGON, ICONS_PATH + "logon.gif");
		declareRegistryImage(REMOVE, ICONS_PATH + "remove.gif");
		declareRegistryImage(NEXT, ICONS_PATH + "next.gif");
		declareRegistryImage(PREV, ICONS_PATH + "prev.gif");
		declareRegistryImage(FIRST, ICONS_PATH + "first.gif");
		declareRegistryImage(LAST, ICONS_PATH + "last.gif");
	}

	private final static void declareRegistryImage(String key, String path) {
		try {
			URL url= FileLocator.find(Activator.getDefault().getBundle(), new Path(path), null);
			if (url != null) {
				ImageDescriptor image = ImageDescriptor.createFromURL(url);
				getImageRegistry().put(key, image);
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	public static Image getImage(String key) {
		return getImageRegistry().get(key);
	}
	public static ImageDescriptor getImageDescriptor(String key) {
		return getImageRegistry().getDescriptor(key);
	}
}
