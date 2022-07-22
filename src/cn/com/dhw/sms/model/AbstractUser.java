package cn.com.dhw.sms.model;

import java.util.Date;

public abstract class AbstractUser implements IUser{
	private Long id;
    private String userId;
    private String password;
    private String name;
    private Date latestOnline;


    public Long getId() {  return id;  }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getLatestOnline() { return latestOnline; }
    public void setLatestOnline(Date latestOnline) {this.latestOnline = latestOnline;}
}
