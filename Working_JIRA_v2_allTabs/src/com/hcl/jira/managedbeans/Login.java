package com.hcl.jira.managedbeans;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.hcl.jira.database.JiraDAO;

@ManagedBean
@SessionScoped
public class Login {
	private String username;
	private String userNameDisplay;
	public String getUserNameDisplay() {
		return userNameDisplay;
	}
	public void setUserNameDisplay(String userNameDisplay) {
		this.userNameDisplay = userNameDisplay;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String password;
	
	private String role;
	
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	 @ManagedProperty(value="#{guestPreferences}")
	    private GuestPreferences gp;

	    public void setGp(GuestPreferences gp) {
	        this.gp = gp;
	    }
	    
	    
	private boolean valid = false;
	
	@ManagedProperty(value="#{jiraBean}")
	private JiraBean jiraBean;
		
	public JiraBean getJiraBean() {
		return jiraBean;
	}
	public void setJiraBean(JiraBean jiraBean) {
		this.jiraBean = jiraBean;
	}
	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	@SuppressWarnings("unused")
	public void validate(ActionEvent ae) {
		System.out.println("In validate  = username" + username + "password = " + password);
		
		if ((this.username != null) && (this.password != null)) {
			Map<String,String> statusMap = JiraDAO.validate(username, password);
			if(statusMap.isEmpty()){
				valid = false;
			}else{
				valid = true;
				role = statusMap.get("ROLE");
				userNameDisplay = statusMap.get("USERNAME");
				String theme = statusMap.get("THEME");
				gp.setTheme(theme);
			}
		}
		RequestContext context = RequestContext.getCurrentInstance();
	    FacesMessage msg = null;
	    boolean loggedIn = false;
	   
	    
	    
	    if(valid){
	      loggedIn = true;
	      msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome!", this.username);
	      jiraBean.loadJiras(username,role);
	      //context.addCallbackParam("loggedIn", true);
	      //  context.execute("dlg.hide()");
	    } else {
	      loggedIn = false;
	      msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid credentials : USERNAME/PASSWORD is Wrong, Please Check. ");	      
	      //context.addCallbackParam("loggedIn", false);
	    }
	    //context.addCallbackParam("loggedIn", Boolean.valueOf(loggedIn));
	    FacesContext.getCurrentInstance().addMessage("messages", msg);
	}
}


