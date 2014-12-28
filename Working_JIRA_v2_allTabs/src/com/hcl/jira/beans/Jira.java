package com.hcl.jira.beans;

import java.io.Serializable;
import java.util.Date;

public class Jira  implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
   private String jiraId;
 
   private String username;
   private Date closeDate=null;
   private int estEffort;
   private int anaEffort;
   private int revEffort;
   private int devEffort;
   private int testEffort;
   private int add1Effort;
   private int add2Effort;
   private String jiraDesc;
   private int jira_MainId;
   private String summary;
   private String assignee="-";
   private String priority;
   private String status;
   private String resolution;
   private Date created;
   private String environment;
   private String stage;
   private String tasktype;
   private String tasksubtype;
   private int reqchange;
   private int reqChangeAddEffort;
   private int cumulativeEffort;
   private int reqchangecopy;
   private String projectName;
   private int projectId;
   private String reporter;
   private Date updated;
   private String outageTicket="";
  
   
   
   
   
   public String getOutageTicket() {
	return outageTicket;
}

public void setOutageTicket(String outageTicket) {
	this.outageTicket = outageTicket;
}

public String getReporter() {
	return reporter;
}

public void setReporter(String reporter) {
	this.reporter = reporter;
}

public Date getUpdated() {
	return updated;
}

public void setUpdated(Date updated) {
	this.updated = updated;
}

   
  /* public Jira(Jira selectedJira) {
	   this.jiraId=selectedJira.getJiraId();
	   this.jiraName=selectedJira.getJiraName();
	   this.username=selectedJira.getUsername();
	   this.closeDate=selectedJira.getCloseDate();
	   this.estEffort=selectedJira.getEstEffort();
	   this.anaEffort=selectedJira.getAnaEffort();
	   this.revEffort=selectedJira.getRevEffort();
	   this.devEffort=selectedJira.getDevEffort();
	   this.testEffort=selectedJira.getTestEffort();
	   this.add1Effort=selectedJira.getAdd1Effort();
	   this.add2Effort=selectedJira.getAdd2Effort();
	   this.jiraDesc=selectedJira.getJiraDesc();
	   this.jira_MainId=selectedJira.getJira_MainId();
	   this.summary=selectedJira.getSummary();
	   this.assigne=selectedJira.getAssigne();
	   this.priority=selectedJira.getPriority();
	   this.status=selectedJira.getStatus();
	   this.resolution=selectedJira.getResolution();
	   this.created=selectedJira.getCreated();
	   this.environment=selectedJira.getEnvironment();
	   this.stage=selectedJira.getStage();
	   this.tasktype=selectedJira.getTasktype();
	   this.tasksubtype=selectedJira.getTasksubtype();
	   this.reqchange=selectedJira.getReqchange();
	   this.reqChangeAddEffort=selectedJira.getReqChangeAddEffort();
   }*/

	public int getProjectId() {
	return projectId;
}

public void setProjectId(int projectId) {
	this.projectId = projectId;
}

	public String getProjectName() {
	return projectName;
}

public void setProjectName(String projectName) {
	this.projectName = projectName;
}

	public int getReqchangecopy() {
	return reqchangecopy;
}

public void setReqchangecopy(int reqchangecopy) {
	this.reqchangecopy = reqchange;
}

	public int getCumulativeEffort() {
		cumulativeEffort = reqChangeAddEffort+testEffort+estEffort+anaEffort+revEffort+devEffort+add1Effort+add2Effort+reqChangeAddEffort;
		return cumulativeEffort;
	}
	
	public void setCumulativeEffort(int cumulativeEffort) {
		this.cumulativeEffort = reqChangeAddEffort+testEffort+estEffort+anaEffort+revEffort+devEffort+add1Effort+add2Effort+reqChangeAddEffort;
		//this.cumulativeEffort = cumulativeEffort;
	}

	public Jira() {
		// TODO Auto-generated constructor stub
	}

	public int getReqChangeAddEffort() {
	   return reqChangeAddEffort;
   }
	
	public void setReqChangeAddEffort(int reqChangeAddEffort) {
		this.reqChangeAddEffort = reqChangeAddEffort;
	}

public int getReqchange() {
	return reqchange;
}

public void setReqchange(int reqchange) {
	this.reqchange = reqchange;
}

public String getStage() {
	return stage;
}

public void setStage(String stage) {
	this.stage = stage;
}


public Date getCloseDate() { 
	return closeDate;
}

public void setCloseDate(Date closeDate) {
	this.closeDate = closeDate;
}


public String getTasktype() {
	return tasktype;
}

public void setTasktype(String tasktype) {
	this.tasktype = tasktype;
}

public String getTasksubtype() {
	return tasksubtype;
}

public void setTasksubtype(String tasksubtype) {
	this.tasksubtype = tasksubtype;
}

   
   public String getEnvironment() {
	return environment;
}

public void setEnvironment(String environment) {
	this.environment = environment;
}

public int getJira_MainId() {
	return jira_MainId;
}

public void setJira_MainId(int jira_MainId) {
	this.jira_MainId = jira_MainId;
}

public String getSummary() {
	return summary;
}

public void setSummary(String summary) {
	this.summary = summary;
}

public String getAssignee() {
	return assignee;
}

public void setAssignee(String assignee) {
	this.assignee = assignee;
}

public String getPriority() {
	return priority;
}

public void setPriority(String priority) {
	this.priority = priority;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getResolution() {
	return resolution;
}

public void setResolution(String resolution) {
	this.resolution = resolution;
}

public Date getCreated() {
	return created;
}

public void setCreated(Date created) {
	this.created = created;
}

   @Override
	public String toString() {
		return "Jira [jiraId=" + jiraId + ",username=" + username + ", closeDate=" + closeDate
				+ ", estEffort=" + estEffort + ", anaEffort=" + anaEffort
				+ ", revEffort=" + revEffort + ", devEffort=" + devEffort
				+ ", testEffort=" + testEffort + ", add1Effort=" + add1Effort
				+ ", add2Effort=" + add2Effort + ", jiraDesc=" + jiraDesc + ", ReqChange=" + reqchange +", ReqChangecopy=" + reqchangecopy + ",outage=" +outageTicket+ ",task sub type =" +tasksubtype+"]";
	}
   
	public String getJiraId() {
		return jiraId;
	}
	public void setJiraId(String jiraId) {
		this.jiraId = jiraId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public int getEstEffort() {
		return estEffort;
	}
	public void setEstEffort(int estEffort) {
		this.estEffort = estEffort;
	}
	public int getAnaEffort() {
		return anaEffort;
	}
	public void setAnaEffort(int anaEffort) {
		this.anaEffort = anaEffort;
	}
	public int getRevEffort() {
		return revEffort;
	}
	public void setRevEffort(int revEffort) {
		this.revEffort = revEffort;
	}
	public int getDevEffort() {
		return devEffort;
	}
	public void setDevEffort(int devEffort) {
		this.devEffort = devEffort;
	}
	public int getTestEffort() {
		return testEffort;
	}
	public void setTestEffort(int testEffort) {
		this.testEffort = testEffort;
	}
	public int getAdd1Effort() {
		return add1Effort;
	}
	public void setAdd1Effort(int add1Effort) {
		this.add1Effort = add1Effort;
	}
	public int getAdd2Effort() {
		return add2Effort;
	}
	public void setAdd2Effort(int add2Effort) {
		this.add2Effort = add2Effort;
	}
	public String getJiraDesc() {
		return jiraDesc;
	}
	public void setJiraDesc(String jiraDesc) {
		this.jiraDesc = jiraDesc;
	}

	
	
		

}
