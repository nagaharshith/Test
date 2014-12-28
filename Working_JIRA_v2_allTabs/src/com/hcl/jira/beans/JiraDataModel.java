package com.hcl.jira.beans;

import java.util.List;

import javax.faces.model.ListDataModel;



import org.primefaces.model.SelectableDataModel;


public class JiraDataModel extends ListDataModel<Jira> implements SelectableDataModel<Jira>{

	 public JiraDataModel() {  
	    }  
	  
	    public JiraDataModel(List<Jira> data) {  
	        super(data);  
	    }  
	
	@SuppressWarnings("unchecked")
	@Override
	public Jira getRowData(String rowKey) {
		
		List<Jira> jiraList = (List<Jira>) getWrappedData();
        //System.out.println("testting rowKey "+rowKey);
        //System.out.println("jiraList.size() = "+jiraList.size());
        //System.out.println(jiraList);
        for(Jira jira : jiraList) {
        	//System.out.println("testting 1"); 
        	//System.out.println("jira.getJiraId() "+jira.getJiraId());
        	if(jira.getJiraId().equals(rowKey))        	{	
        		//System.out.println(jira);
        		//System.out.println("testting ........................");
        		return jira;
        	}    
        	System.out.println("testting 2");
        }
		return null;
	}

	@Override
	public Object getRowKey(Jira jira) {
		// TODO Auto-generated method stub
		//System.out.println("testting 3");
		//System.out.println(jira.getJiraId());
		return jira.getJiraId();
	}
	

}
