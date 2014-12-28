package com.hcl.jira.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.hcl.jira.database.JiraDAO;

@SessionScoped
@ManagedBean(name="personBean")
public class personBean implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CartesianChartModel categoryModel=new CartesianChartModel();;
	SortedMap<String,String> members=new TreeMap<String,String>();
	@SuppressWarnings("rawtypes")
	List memberEffort=new ArrayList();
    private String person;
    private Date empchartdate1=null;
    private Date empchartdate2=null;
	ChartSeries emp = new ChartSeries();
    
    public Date getEmpchartdate1() {
		return empchartdate1;
	}

	public void setEmpchartdate1(Date empchartdate1) {
		this.empchartdate1 = empchartdate1;
	}

	public Date getEmpchartdate2() {
		return empchartdate2;
	}

	public void setEmpchartdate2(Date empchartdate2) {
		this.empchartdate2 = empchartdate2;
	}

	
	 
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	List<String> l1=new ArrayList();
	 List<Integer> l2=null;
	  
		public String getPerson() {
			return person;
		}

		public void setPerson(String person) {
			this.person = person;
		}
		
		public CartesianChartModel getCategoryModel() {
			if(!categoryModel.getCategories().isEmpty()){
			//System.out.println("in if.....!@!@@!@!"+categoryModel);	
				return categoryModel;
			}	
			else
			{
				categoryModel = new CartesianChartModel();
				emp.set("Zero Effort",0);
				categoryModel.addSeries(emp);
			//	System.out.println("in else.....!@!@@!@!"+categoryModel);	
	     	}
			return categoryModel;
			}

		public void setCategoryModel(CartesianChartModel categoryModel) {
			this.categoryModel = categoryModel;
		}

		
	
		public personBean()
		{
		    members=JiraDAO.getmembers();
			//System.out.println(members);
		}
		
		
		public Map<String, String> getMembers() {
			return members;
		}

		public void setMembers(SortedMap<String, String> members) {
			this.members = members;
		}

		@SuppressWarnings("unchecked")
		public void createCategoryModel(ActionEvent actionEvent) {  
		       
			
			memberEffort=JiraDAO.getEmpEffort(person,empchartdate1,empchartdate2);
			
			//System.out.println(memberEffort);
			System.out.println(empchartdate1);
			System.out.println(empchartdate2);
			
			 l1=(List<String>) memberEffort.get(0);
			 l2=(List<Integer>) memberEffort.get(1);
			System.out.println(person);
			categoryModel = new CartesianChartModel();  
			emp = new ChartSeries();
	          
	        emp.setLabel(person);  
	
	        for(int i=0;i<l1.size();i++)
	        {
	        	emp.set(l1.get(i),l2.get(i));
	        }
	        	categoryModel.addSeries(emp);  
	        }  

}
