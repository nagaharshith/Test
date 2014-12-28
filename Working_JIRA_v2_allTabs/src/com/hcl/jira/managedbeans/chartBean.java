package com.hcl.jira.managedbeans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.chart.PieChartModel;

import com.hcl.jira.database.JiraDAO;

@SessionScoped
@ManagedBean(name="chartBean1")
public class chartBean implements Serializable  {
    //public static String[] projectName; 
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  PieChartModel projectpieModel=new PieChartModel();;
	 private  PieChartModel singleprojectpieModel=new PieChartModel();	 
	 SortedMap<String,Integer> projects=new TreeMap<String,Integer>();
	 private Date date1=null;
	 private Date date2=null;
	 private Date singlechartdate1=null;
	 private Date singlechartdate2=null;
	 
	 
	 
	 public Date getSinglechartdate1() {
		return singlechartdate1;
	}

	public void setSinglechartdate1(Date singlechartdate1) {
		this.singlechartdate1 = singlechartdate1;
	}

	public Date getSinglechartdate2() {
		return singlechartdate2;
	}

	public void setSinglechartdate2(Date singlechartdate2) {
		this.singlechartdate2 = singlechartdate2;
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



		Calendar now = Calendar.getInstance();
	private static String[] strMonths = new String[]{
	             "Jan",
	             "Feb",
	             "Mar",
	             "Apr",
	             "May",
	             "Jun",
	             "Jul",
	             "Aug",
	             "Sep",
	             "Oct",
	             "Nov",
	             "Dec"
	           };
	 private String projectName;
	 private String chartDate=strMonths[now.get(Calendar.MONTH)];
	 
	 public String getChartDate() {
	//System.out.println("!@!@!@!@!@!@!"+chartDate);	
		 return chartDate;
	}

	public void setChartDate(String chartDate) {
		this.chartDate = chartDate;
	}


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public SortedMap<String, Integer> getProjects() {
		return projects;
	}

	public void setProjects(SortedMap<String, Integer> projects) {
		this.projects = projects;
	}

	private int indProject;
	private static int totalpeojecteffort;
	private int teameffort;


	public int getTeameffort() {
		//System.out.println("inside teameffort~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ");
		
		return teameffort;
	}

	public void setTeameffort(int teameffort) {
		this.teameffort = teameffort;
	}

	public int getTotalpeojecteffort() {
		//System.out.println("inside totalpeojecteffort~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ");
		return totalpeojecteffort;
	}

	@SuppressWarnings("static-access")
	public void setTotalpeojecteffort(int totalpeojecteffort) {
		this.totalpeojecteffort = totalpeojecteffort;
	}

	@SuppressWarnings("rawtypes")
	List project_list=null;
	 
	
	 public void onTabChange(TabChangeEvent event) { 
		// System.out.println("in tab change event listener......");
		 //project_list=JiraDAO.TotalEffort(date1,date2);
	     //ProjectPieModel(); 
	  }
	
	
	public chartBean()
	{
		project_list=JiraDAO.TotalEffort(date1,date2);
		 projects=JiraDAO.getProjects();
		 //System.out.println("in the Bean...........................");
	    ProjectPieModel(); 
	}
	
	public void monthlyData(ActionEvent actionEvent)
	{
		//System.out.println(date1);
		//System.out.println(date2);
		 project_list=JiraDAO.TotalEffort(date1,date2);
		 ProjectPieModel(); 
	}

	
	@SuppressWarnings("unchecked")
	private void ProjectPieModel() 
	 
	   {  
		
		totalpeojecteffort=0;
		//System.out.println("inside pie model !~!~!~!!!!!!!!~!~!!!!!!!!~~~~~~~~~~~");
		 List<String> list1=(List<String>) project_list.get(0);
		 List<Integer> list2=(List<Integer>) project_list.get(1);
		 projectpieModel = new PieChartModel();  
		 
		 for(int i=0;i<list1.size();i++)
		 {
		   projectpieModel.set(list1.get(i),(Number)list2.get(i));
		 }
		 //System.out.println(projectpieModel.getData());
		 for(int i:list2)
		 {
			 totalpeojecteffort +=i;
		 }
	   } 
	 
	 
	
	public void singleproject(ActionEvent actionEvent) 
	 {
		teameffort=0;
		singleprojectpieModel=new PieChartModel();	
		//System.out.println(indProject);
		 //System.out.println("!!!!!!!!$#@!$#!@%$!$#%^!$#^%!%$");
	//	 singleProjectPieModel();
	//	String indProject1 = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{chartBean1.indProject}", String.class);
	//	System.out.println("1111111111111111111111"+indProject);
		
		 List Singleprojectchart=JiraDAO.genrateSingleChart(indProject,singlechartdate1,singlechartdate2);
		 //System.out.println(Singleprojectchart);
		 List<String> l1=(List<String>) Singleprojectchart.get(0);
		 List<Number>l2=(List<Number>) Singleprojectchart.get(1);
		 List<Integer>l3=(List<Integer>) Singleprojectchart.get(1);
		 //System.out.println("li size^^^^^^^^^^^^^^^^^^^^"+l1.size());
		 if(l1.size()>0)
		 {

		 //System.out.println(l2.size());
		//System.out.println("generating chart : ");
		 for(int i=0;i<l2.size();i++)
		 {
			 singleprojectpieModel.set(l1.get(i),l2.get(i)); 
		 }
		}
		 
		 for(int i:l3)
		 {
			 teameffort +=i;
		 }
		 
		 for (Entry<String, Integer> entry : projects.entrySet()) { 
	            if (entry.getValue().equals(indProject)) { 
	            	System.out.println("Selected Project"+entry.getKey()); 
	                projectName=entry.getKey();
	            } 
	        } 
 
		 //if(singleprojectpieModel.getData())
		
	//	 System.out.println("aildufg aklsdfo;asdgpadufgh ;ll;fkj;kdfjghl; k;lsdkfklsjdfgh");
   	}
	 

/*	 public void setSingleprojectpieModel(PieChartModel singleprojectpieModel) {
		
		 System.out.println("chart value setting.............. : "+singleprojectpieModel);
			//selectedJira.setReqchangecopy(selectedJira.getReqchange());
			if((singleprojectpieModel.getData())==null){singleprojectpieModel=new PieChartModel();}		
		    this.singleprojectpieModel = singleprojectpieModel;
		}*/
	 
	public PieChartModel getSingleprojectpieModel() { 
		
		if(!singleprojectpieModel.getData().isEmpty()){
		//	System.out.println("if *****************");
		   return singleprojectpieModel; }
		else{
			singleprojectpieModel.set("No Assignee!!!",00);
			//System.out.println("else  *************************");
		}
			return singleprojectpieModel;

	} 
	    
	    public PieChartModel getProjectpieModel() {  
	      //System.out.println("asdasdhf gksdjfh sdkflsd"+projectpieModel.getData()); 
	    	
	    	if(!projectpieModel.getData().isEmpty()){
	    		//	System.out.println("if *****************");
	    		   return projectpieModel; }
	    		else{
	    			projectpieModel.set("No Jiras!!!",00);
	    			//System.out.println("else  *************************");
	    		}
	    			
	    	return projectpieModel;  
	    } 
	    
	    public int getIndProject() {
			return indProject;
		}


		public void setIndProject(int indProject) {
			this.indProject = indProject;
		}
	  

}
