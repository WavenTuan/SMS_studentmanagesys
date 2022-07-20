package cn.com.dhw.sms.model;

import java.util.List;

public interface ITreeEntry {
	//实现设置和得到树节点名称
	void setName(String name);
	String getName();
	//实现设置和得到子节点名称
	void setChildren(List<ITreeEntry> children);
	List<ITreeEntry> getChildren();
	void addChild(ITreeEntry entry);
	boolean hasChild();
	//设置和得到节点的父节点
	void setParentEntry(ITreeEntry parentEntry);
	ITreeEntry getParentEntry();
}
