package com.hcl.jira.managedbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SlideEndEvent;

import com.hcl.jira.beans.Jira;
import com.hcl.jira.beans.JiraDataModel;
import com.hcl.jira.database.JiraDAO;

@SessionScoped
@ManagedBean(name="jiraBean")
public class JiraBean {
	

	
	private Jira selectedJira;
	private Jira selectedJira1;
	
	 private int i=1;  

	//private final static String[] projects;
	private JiraDataModel jiraDataModel;
	
	private Date date1=null;
	   private Date date2=null;
	   private boolean valid = false;
	   
	   
    public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	
	
	public static SelectItem[] projectOptions=null;
	
    //public static String[] projectName; 
	
	
    
    
    /*   public JiraBean()
	{
		projectOptions = projectName; 
	}*/
		
	
	 public static void createFilterOptions(Set<String> data)  {  
	        SelectItem[] options = new SelectItem[data.size() + 1];  
	  
	        options[0] = new SelectItem("", "ALL"); 
	        int i=0;
	        for(String s:data) {  
	            options[i + 1] = new SelectItem(s, s);  
	            i++;
	           //System.out.println("selected projects"); 
	        }  
	        projectOptions=options;
	        //return options;  
	    }  
	  
	    public SelectItem[] getProjectOptions() {  
	        return projectOptions;  
	    }  
	    
	 
	    public void applyFilter(ValueChangeEvent e1){
	        String filter = (String)e1.getNewValue();
	        //System.out.println(filter);
	    }

 
	    
	public Jira getSelectedJira() {
		if(selectedJira==null)selectedJira=new Jira();
		return selectedJira;
	}
	public void setSelectedJira(Jira selectedJira) {
		//System.out.println("selectedJira setting.............. : "+selectedJira);
		
		//selectedJira.setReqchangecopy(selectedJira.getReqchange());
		if(selectedJira==null){selectedJira=new Jira();}		
		this.selectedJira = selectedJira;
	    
		/*selectedJira1=selectedJira;
		System.out.println("test 1............."+selectedJira1);
*/
	}
	
	

	public String resetValues()
	{
		//System.out.println("resetting......");
		setSelectedJira(selectedJira1);
		
		return null;
	}
	
	public JiraDataModel getJiraDataModel() {
		return jiraDataModel;
	}
	public void setJiraDataModel(JiraDataModel jiraDataModel) {
		this.jiraDataModel = jiraDataModel;
	}
	

	
	private List<Jira> jiraList = new ArrayList<Jira>();
	
	
	public List<Jira> getJiraList() {
		return jiraList;
	}
	
	public void setJiraList(List<Jira> jiraList) {
		this.jiraList = jiraList;
	}

	public void loadJiras(String username, String role){
		System.out.println("begin date1 = "+date1);
		System.out.println("begin date2 = "+date2);
		jiraList = JiraDAO.loadJiras(username,role,date1,date2);
		jiraDataModel = new JiraDataModel(jiraList);
		//System.out.println(jiraDataModel.toString());
		//System.out.println("asdasdsda");
		//System.out.println(Login.role);
	}
	
	/*public void updateList(RowEditEvent rowEditEvent){
		List<Jira> jiraUpdateList = new ArrayList<Jira>();
		for(Jira jira:jiraList){
			for(Jira refJira:refJiraList){
				if(!refJira.toString().equalsIgnoreCase(jira.toString())){
					jiraUpdateList.add(jira);					
				}
			}
		}
		if(!jiraUpdateList.isEmpty()){
			JiraDAO.updateList(jiraUpdateList);
			String username = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{login.username}", String.class);
			System.out.println("username for update : "+username);
			loadJiras(username);
		}
	}*/
	
	/*public void update(RowEditEvent rowEditEvent){
		boolean hasUpdated = false;
		for(Jira jira:jiraList){
			for(Jira refJira:refJiraList){
				if(refJira.getJiraId()==jira.getJiraId() && !refJira.toString().equalsIgnoreCase(jira.toString())){
					System.out.println("CHANGED, UPDATING . . . : "+refJira.toString().equalsIgnoreCase(jira.toString()));
					JiraDAO.update(jira);
					hasUpdated = true;
				}
			}
		}
		if(hasUpdated){
			String username = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{login.username}", String.class);
			System.out.println("username for update : "+username);
			loadJiras(username);
		}
	}*/
	
	/*public void rowupdate(RowEditEvent rowEditEvent){
		Jira jira = (Jira)rowEditEvent.getObject();
		JiraDAO.update(jira);
		String username = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{login.username}", String.class);
		String role = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{login.role}", String.class);
		System.out.println("username for update : "+username);
		loadJiras(username,role);
	}*/
		
	private boolean hasUpdated;
	
	
	public boolean isHasUpdated() {
		return hasUpdated;
	}

	public void setHasUpdated(boolean hasUpdated) {
		this.hasUpdated = hasUpdated;
	}
	
	public void validating(ValueChangeEvent  event)
	{
	//System.out.println(selectedJira.getTasksubtype());
	}
	
	
	public void update(ActionEvent actionEvent){
		
		System.out.println("username for update : ");
		//FacesContext.getCurrentInstance().renderResponse();
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("updated", true);  
		//Jira jira = (Jira) actionEvent.getSource();
		JiraDAO.update(selectedJira);
		//System.out.print("");
		//hasUpdated = true;
		//System.out.println("hasUpdated : "+hasUpdated);
		/*String username = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{login.username}", String.class);
		String role = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{login.role}", String.class);
		System.out.println("username for update : "+username);
		loadJiras(username,role);*/
	}
	
			
	
	public void updateselect(ActionEvent actionEvent){
		
		System.out.println("date1 = "+date1);
		System.out.println("date2 = "+date2);
		String role = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{login.role}", String.class);
		String username = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{login.username}", String.class);
		System.out.println("role =  "+role);
		jiraList = JiraDAO.loadJiras(username,role,date1,date2);
		jiraDataModel = new JiraDataModel(jiraList);
		System.out.println(jiraDataModel.toString());
		System.out.println("asdasdsda");
		
	}
	
	public void onSlideEnd(SlideEndEvent event) {  
		//System.out.println("onSlideEnd");
      /* FacesMessage msg = new FacesMessage("Slide Ended", "Value: " + event.getValue());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  */
		selectedJira.setReqchange(event.getValue());
	}  

	
	 
	
}
