package com.hcl.jira.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class Logout {
	
	public void logout() {
	    
		System.out.println("in logout");
		FacesContext context = FacesContext.getCurrentInstance();
	   /* HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
	    request.getSession().invalidate();*/
	    context.getExternalContext().invalidateSession();
	    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Logged Out!", "Logged Out.");
	    context.addMessage(null, msg);
	}

}
