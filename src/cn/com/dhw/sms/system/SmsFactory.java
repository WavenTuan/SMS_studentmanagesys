package cn.com.dhw.sms.system;

import java.util.ArrayList;
import java.util.List;

import cn.com.dhw.sms.model.ITreeEntry;
import cn.com.dhw.sms.navigator.NavigatorEntry;

public class SmsFactory {
	public static List<ITreeEntry> createNavigatorEntryTree() {
		NavigatorEntry t1 = new NavigatorEntry("数据管理");
		t1.setImage(ImagesContext.getImage(ImagesContext.STUDENT));
		NavigatorEntry t2 = new NavigatorEntry("报表输出");
		t2.setImage(ImagesContext.getImage(ImagesContext.REPORT));
		NavigatorEntry t3 = new NavigatorEntry("系统配置");
		t3.setImage(ImagesContext.getImage(ImagesContext.SYSCONFIG));
		{
			NavigatorEntry c1 = new NavigatorEntry("档案管理");
			c1.setImage(ImagesContext.getImage(ImagesContext.NOTE));
			t1.addChild(c1);
			NavigatorEntry c2 = new NavigatorEntry("成绩管理");
			c2.setImage(ImagesContext.getImage(ImagesContext.EDITING));
			t1.addChild(c2);
		}
		
		ArrayList<ITreeEntry> list = new ArrayList<ITreeEntry>();
		list.add(t1);
		list.add(t2);
		list.add(t3);
		return list;
	}
}
