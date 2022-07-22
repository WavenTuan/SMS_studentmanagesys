package cn.com.dhw.sms.system;

import java.util.HashSet;
import java.util.Set;

import cn.com.dhw.sms.model.IUser;

public class Context {
	private static Context context = new Context();
	private Context() {}
	public static Context getInstance() {	return context;	}

	private IUser currentUser = null;
	public IUser getCurrentUser() {return currentUser;	}
	public void setCurrentUser(IUser currentUser) {this.currentUser = currentUser;}

	public boolean isLogon() {return currentUser != null;}

	private Set<ILogonListener> logonListeners = new HashSet<ILogonListener>(); 

	public void addLogonListener(ILogonListener listener) {	logonListeners.add(listener);}

	public void removeLogonListener(ILogonListener listener) {logonListeners.remove(listener);}

	public void fireLogonEvent() {
		for (ILogonListener listener : new HashSet<ILogonListener>(logonListeners))
			listener.logon();
	}

	public void fireLogoffEvent() {
		for (ILogonListener listener : new HashSet<ILogonListener>(logonListeners))
			listener.logoff();
		currentUser = null;
	}
}
