package cn.com.dhw.sms.model;

import java.util.Date;

public interface IUser {
	public Long getId();
	public void setId(Long id);

	public String getUserId();
	public void setUserId(String userId);

	public String getPassword();
	public void setPassword(String password);

	public String getName();
	public void setName(String name);

	public Date getLatestOnline();
	public void setLatestOnline(Date date);
}
