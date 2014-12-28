package com.hcl.jira.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import com.hcl.jira.beans.Jira;
import com.hcl.jira.managedbeans.JiraBean;

public class JiraDAO {

	public static List<Jira> loadJiras(String username, String role, Date date1, Date date2) {
		List<Jira> jiraList = new ArrayList<Jira>();
		Connection con = null;
		PreparedStatement prepStat = null;
		ResultSet rs = null;
		try{
			
			con = DataSourceManager.getDSM().conFromPool();
			String sql = "SELECT b.jira_id,  b.project,  c.project_name,  assignee,  reporter, updated,  summary,  priority,  status,  created,CLOSE_DATE,EST_EFFORT,ANA_EFFORT,REV_EFFORT,DEV_EFFORT,TEST_EFFORT,ADD1_EFFORT,ADD2_EFFORT,ENVIRONMENT,STAGE,TASKTYPE,TASKSUBTYPE,REQCHANGE,REQ_CHANGE_ADD_EFFORT,OUTAGE_TICKET FROM jira_details a, jira_stats b,  project_info c WHERE b.assignee=? and b.project=c.project and a.jira_id(+)=b.jira_id";
			if("admin".equalsIgnoreCase(role)){
				//sql = "SELECT distinct B.JIRA_ID,B.PROJECT,C.PROJECT_NAME,JIRA_NAME,USERNAME,ASSIGNEE,REPORTER,UPDATED,CLOSE_DATE,EST_EFFORT,ANA_EFFORT,REV_EFFORT,DEV_EFFORT,TEST_EFFORT,ADD1_EFFORT,ADD2_EFFORT,JIRA_DESC,ENVIRONMENT,STAGE,TASKTYPE,TASKSUBTYPE,REQCHANGE,REQ_CHANGE_ADD_EFFORT,SUMMARY,PRIORITY,STATUS,CREATED FROM JIRA_DETAILS A, JIRA_STATS B, PROJECT_INFO C WHERE  A.PROJECT=C.PROJECT AND A.JIRA_ID=B.JIRA_ID";
				sql="SELECT b.jira_id,  b.project,  c.project_name,  assignee,  reporter, updated,  summary,  priority,  status,  created,CLOSE_DATE,EST_EFFORT,ANA_EFFORT,REV_EFFORT,DEV_EFFORT,TEST_EFFORT,ADD1_EFFORT,ADD2_EFFORT,ENVIRONMENT,STAGE,TASKTYPE,TASKSUBTYPE,REQCHANGE,REQ_CHANGE_ADD_EFFORT,OUTAGE_TICKET FROM jira_details a, jira_stats b,  project_info c WHERE b.project=c.project and a.jira_id(+)=b.jira_id";
			}
			else if("projectlead".equalsIgnoreCase(role)){
				//sql = "select JIRA_ID,JIRA_NAME,USERNAME,CLOSE_DATE,EST_EFFORT,ANA_EFFORT,REV_EFFORT,DEV_EFFORT,TEST_EFFORT,ADD1_EFFORT,ADD2_EFFORT,JIRA_DESC,ENVIRONMENT,STAGE,TASKTYPE,TASKSUBTYPE,REQCHANGE,REQ_CHANGE_ADD_EFFORT,JIRA_MAIN_ID,SUMMARY,ASSIGNE,PRIORITY,STATUS,RESOLUTION,CREATED,PROJECT from jira_details a, jira_info b where a.PROJECT_LEAD=?  and a.JIRA_ID=b.JIRA_MAIN_ID";
				sql = "SELECT b.jira_id,  b.project,  c.project_name,  assignee,  reporter, updated,  summary,priority,  status,  created,CLOSE_DATE,EST_EFFORT,ANA_EFFORT,REV_EFFORT,DEV_EFFORT,TEST_EFFORT,ADD1_EFFORT,ADD2_EFFORT,ENVIRONMENT,STAGE,TASKTYPE,TASKSUBTYPE,REQCHANGE,REQ_CHANGE_ADD_EFFORT,OUTAGE_TICKET FROM jira_details a, jira_stats b,  project_info c, lead_info d  WHERE b.project=c.project and c.project=d.project and d.project_lead=? and a.jira_id(+)=b.jira_id";
			}
			String load30daysBack = null;
			if(date1 == null && date2==null){
				load30daysBack = "WHERE b.updated>trunc(sysdate)-30  and "; 				
			}else{
				load30daysBack = "WHERE b.updated between ? AND ? and ";				
			}
			sql = sql.replace("WHERE", load30daysBack);
			//System.out.println("sql = "+sql);
			prepStat = con.prepareStatement(sql);	
			int i = 0;
			
			if(date1 != null && date2!=null){
		/*		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
				Date D1=new java.sql.Date(date1.getTime());
				Date D2=new java.sql.Date(date2.getTime());
					System.out.println(sdf.format(D1));
					System.out.println(sdf.format(D2));
				Date dd1=new Date(sdf.format(D1));
				Date dd2=new Date(sdf.format(D2));
				prepStat.setDate(++i,(java.sql.Date) dd1);
				System.out.println((java.sql.Date) dd1);
				prepStat.setDate(++i,(java.sql.Date) dd2);
				System.out.println((java.sql.Date) dd2);
				*/
				prepStat.setDate(++i, new java.sql.Date(date1.getTime()));
				prepStat.setDate(++i, new java.sql.Date(date2.getTime()));
				//System.out.println(new java.sql.Date(date2.getTime()));
				
			}
			
			System.out.println("username = "+username);
			if(!"admin".equalsIgnoreCase(role) || "projectlead".equalsIgnoreCase(role)){
				prepStat.setString(++i, username);
			}
						
			//prepStat.setFetchSize(999);
			rs = prepStat.executeQuery();
			Set<String> set = new TreeSet<String>();
			while(rs.next()){
				Jira jira = new Jira();
				jira.setJiraId(rs.getString(1));
				//System.out.println("rs.getString(1) = "+rs.getString(1));
				jira.setProjectId(rs.getInt(2));
				//System.out.println("rs.getString(2) = "+rs.getString(2));
				jira.setProjectName(rs.getString(3));
				//System.out.println("rs.getString(3) = "+rs.getString(3));
				set.add(rs.getString(3));
				//jira.setJiraName(rs.getString("JIRA_NAME"));
				//jira.setUsername(rs.getString("USERNAME"));
				jira.setAssignee(rs.getString("ASSIGNEE"));
				jira.setReporter(rs.getString("REPORTER"));
				jira.setUpdated(rs.getDate("UPDATED"));
				jira.setCloseDate(rs.getDate("CLOSE_DATE"));
				jira.setEstEffort(rs.getInt("EST_EFFORT"));
				jira.setAnaEffort(rs.getInt("ANA_EFFORT"));
				jira.setRevEffort(rs.getInt("REV_EFFORT"));
				jira.setDevEffort(rs.getInt("DEV_EFFORT"));
				jira.setTestEffort(rs.getInt("TEST_EFFORT"));
				jira.setAdd1Effort(rs.getInt("ADD1_EFFORT"));
				jira.setAdd2Effort(rs.getInt("ADD2_EFFORT"));
				//jira.setJiraDesc(rs.getString("JIRA_DESC"));
				/*jira.setEnvironment(rs.getString("ENVIRONMENT"));
				jira.setStage(rs.getString("STAGE"));*/
				jira.setTasktype(rs.getString("TASKTYPE"));
				jira.setTasksubtype(rs.getString("TASKSUBTYPE"));
				jira.setReqchange(rs.getInt("REQCHANGE"));
				jira.setReqChangeAddEffort(rs.getInt("REQ_CHANGE_ADD_EFFORT"));
				jira.setOutageTicket(rs.getString("OUTAGE_TICKET"));
				jira.setSummary(rs.getString("SUMMARY"));
				jira.setPriority(rs.getString("PRIORITY"));
				jira.setStatus(rs.getString("STATUS"));	
				jira.setCreated(rs.getDate("CREATED"));
				
				jiraList.add(jira);
			}
			
			/* String sql2="select PROJECT_NAME from project_info ";
				
			if("projectlead".equalsIgnoreCase(role))
			{
				sql2="select distinct project_name from project_info a,lead_info b where b.project_lead=? and a.project=b.project";
			
			}
			prepStat = con.prepareStatement(sql2);
			
			
			if("projectlead".equalsIgnoreCase(role))
			{
				prepStat.setString(1, username);
			
			}
			
			rs = prepStat.executeQuery();		
			
			System.out.println("sql2 calling^^^^^^^^^");
			while(rs.next()){
				list.add(rs.getString("PROJECT_NAME"));
				
			}*/
			//System.out.println("List~~~~~~~~~~~~"+set);
			JiraBean.createFilterOptions(set);
			//System.out.println("projectOptions"+JiraBean.projectOptions);
			
			
		}catch(SQLException sqlExcep){
			sqlExcep.printStackTrace();
		}finally{
			DataSourceManager.getDSM().release(rs,prepStat,con);			
		}
		return jiraList;
	}

	public static Map<String,String> validate(String username,String password){
		Map<String,String> statusMap  = new HashMap<String,String>();
		Connection con = null;
		PreparedStatement prepStat = null;
		ResultSet resSet = null;
		try{
			con = DataSourceManager.getDSM().conFromPool();
			//System.out.println("hzdjkhdkj"+con.getMetaData().getDatabaseProductName());
			prepStat = con.prepareStatement("SELECT a.USER_ID,PASSWORD,ROLE_ID,USER_NAME,THEME FROM jira_creds a, USER_ROLE b where a.USER_ID=? and a.PASSWORD=? and b.USER_ID=a.USER_ID");
			prepStat.setString(1, username);
			prepStat.setString(2, password);
			System.out.println(username);
			//System.out.println(password);
			resSet = prepStat.executeQuery();
			if(resSet.next()){
				//System.out.println("username from db = "+resSet.getString(1));
				//System.out.println("password from db = "+resSet.getString(2));
				//System.out.println("role from db = "+resSet.getString(3));
				statusMap.put("ROLE", resSet.getString(3));
				statusMap.put("USERNAME", resSet.getString(4));
				statusMap.put("THEME", resSet.getString(5));
			}
		}catch(SQLException sqle){
			//System.out.println(sqle);
			sqle.printStackTrace();
		}finally{
			DataSourceManager.getDSM().release(resSet,prepStat,con);
		}
		return statusMap;
	}

	public static void updateList(List<Jira> jiraUpdateList) {// not using.....will be used only for the batch update (list of data's)
		Connection con = null;
		PreparedStatement prepStat = null;
		ResultSet resSet = null;
		try{
			con = DataSourceManager.getDSM().conFromPool();
			for(Jira jira:jiraUpdateList){
				prepStat = con.prepareStatement("UPDATE JIRA_DETAILS SET USERNAME=?,CLOSE_DATE=?,EST_EFFORT=?,ANA_EFFORT=?,REV_EFFORT=?,DEV_EFFORT=?,TEST_EFFORT=?,ADD1_EFFORT=?,ADD2_EFFORT=?,JIRA_DESC=? WHERE JIRA_ID=?");
				prepStat.setString(1, jira.getUsername());
				prepStat.setDate(2,   new java.sql.Date(jira.getCloseDate().getTime()));
				prepStat.setInt(3, jira.getEstEffort());
				prepStat.setInt(4, jira.getAnaEffort());
				prepStat.setInt(5, jira.getRevEffort());
				prepStat.setInt(6, jira.getDevEffort());
				prepStat.setInt(7, jira.getTestEffort());
				prepStat.setInt(8, jira.getAdd1Effort());
				prepStat.setInt(9, jira.getAdd2Effort());
				prepStat.setString(10, jira.getJiraDesc());
				prepStat.setString(11, jira.getJiraId());
				prepStat.addBatch();
			}
			int[] updateCount = prepStat.executeBatch();
			System.out.println("Column changes were : "+Arrays.asList(updateCount));			
		}catch(SQLException sqle){
			System.out.println(sqle);
			sqle.printStackTrace();
		}finally{
			DataSourceManager.getDSM().release(resSet,prepStat,con);
		}
	}
	
	public static void update(Jira jira) {
		Connection con = null;
		PreparedStatement prepStat = null;
		ResultSet resSet = null;
		try{
			con = DataSourceManager.getDSM().conFromPool();
			prepStat = con.prepareStatement("MERGE INTO JIRA_DETAILS JD USING (SELECT  ? JIRA_ID,? CLOSE_DATE,? EST_EFFORT,? ANA_EFFORT,? REV_EFFORT,? DEV_EFFORT,? TEST_EFFORT,? ADD1_EFFORT,? ADD2_EFFORT,? TASKTYPE,? TASKSUBTYPE,? REQCHANGE,? REQ_CHANGE_ADD_EFFORT,? OUTAGE_TICKET, ? total_effort FROM DUAL) JD1 ON (JD.JIRA_ID=JD1.JIRA_ID)  WHEN MATCHED THEN UPDATE SET  JD.CLOSE_DATE=JD1.CLOSE_DATE,JD.EST_EFFORT=JD1.EST_EFFORT,JD.ANA_EFFORT=JD1.ANA_EFFORT,JD.REV_EFFORT=JD1.REV_EFFORT,JD.DEV_EFFORT=JD1.DEV_EFFORT,JD.TEST_EFFORT=JD1.TEST_EFFORT,JD.ADD1_EFFORT=JD1.ADD1_EFFORT,JD.ADD2_EFFORT=JD1.ADD2_EFFORT,JD.TASKTYPE=JD1.TASKTYPE,JD.TASKSUBTYPE=JD1.TASKSUBTYPE,JD.REQCHANGE=JD1.REQCHANGE,JD.REQ_CHANGE_ADD_EFFORT=JD1.REQ_CHANGE_ADD_EFFORT,JD.OUTAGE_TICKET=JD1.OUTAGE_TICKET,jd.total_effort=jd1.total_effort WHEN NOT MATCHED THEN INSERT (JIRA_ID, CLOSE_DATE, EST_EFFORT, ANA_EFFORT, REV_EFFORT, DEV_EFFORT, TEST_EFFORT, ADD1_EFFORT, ADD2_EFFORT, TASKTYPE, TASKSUBTYPE, REQCHANGE, REQ_CHANGE_ADD_EFFORT,OUTAGE_TICKET,total_effort) VALUES (JD1.JIRA_ID, JD1.CLOSE_DATE, JD1.EST_EFFORT, JD1.ANA_EFFORT, JD1.REV_EFFORT, JD1.DEV_EFFORT, JD1.TEST_EFFORT, JD1.ADD1_EFFORT, JD1.ADD2_EFFORT, JD1.TASKTYPE, JD1.TASKSUBTYPE, JD1.REQCHANGE, JD1.REQ_CHANGE_ADD_EFFORT,JD1.OUTAGE_TICKET,JD1.TOTAL_EFFORT)");
			prepStat.setString(1, jira.getJiraId());
			if(jira.getCloseDate()!=null){
				prepStat.setDate(2, new java.sql.Date(jira.getCloseDate().getTime()));
			}else{
				prepStat.setNull(2, java.sql.Types.DATE);
			}
			prepStat.setInt(3, jira.getEstEffort());
			prepStat.setInt(4, jira.getAnaEffort());
			prepStat.setInt(5, jira.getRevEffort());
			prepStat.setInt(6, jira.getDevEffort());
			prepStat.setInt(7, jira.getTestEffort());
			prepStat.setInt(8, jira.getAdd1Effort());
			prepStat.setInt(9, jira.getAdd2Effort());
			prepStat.setString(10, jira.getTasktype());
			prepStat.setString(11, jira.getTasksubtype());
			prepStat.setInt(12, jira.getReqchange());
			prepStat.setInt(13, jira.getReqChangeAddEffort());
			prepStat.setString(14, jira.getOutageTicket());
			prepStat.setInt(15, jira.getCumulativeEffort());
			/*}*/
			int updateCount = prepStat.executeUpdate();
			System.out.println("Column changes were : "+updateCount);			
		}catch(SQLException sqle){
			System.out.println(sqle);
			sqle.printStackTrace();
		}finally{
			DataSourceManager.getDSM().release(resSet,prepStat,con);
		}
	}

	public static void loadSelectedJiras() {
		
		// TODO Auto-generated method stub
		
	}

	public static void setThemeAsDefaultForUser(String username, String theme) {
		Connection con = null;
		PreparedStatement prepStat = null;
		ResultSet resSet = null;
		try{
			con = DataSourceManager.getDSM().conFromPool();
			prepStat = con.prepareStatement("UPDATE USER_ROLE SET THEME=?  WHERE USER_ID=?");
			prepStat.setString(1, theme);
			prepStat.setString(2, username);
			int updateCount = prepStat.executeUpdate();
			System.out.println("Column changes were : "+updateCount);			
		}catch(SQLException sqle){
			System.out.println(sqle);
			sqle.printStackTrace();
		}finally{
			DataSourceManager.getDSM().release(resSet,prepStat,con);
		}
	}
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static  List TotalEffort(Date date1,Date date2)
	{
		
		System.out.println("In DAO........~~~~~~~~"+date1+"~~~~~~~~~"+date2);
		List<String> list1 = new ArrayList<String>();
		List<Integer> list2 = new ArrayList<Integer>();
		List projectlist = new ArrayList();
		Connection con = null;
		PreparedStatement prepStat = null;
		ResultSet rs = null;
		try{
		con = DataSourceManager.getDSM().conFromPool();
		String sql = "select c.project_name,sum(nvl(total_effort,null)) from jira_details a,jira_stats b,project_info c WHERE ";
		String load30daysBack = null;
		if(date1 == null && date2==null){
			load30daysBack = "WHERE c.project in(select project from project_info) and a.jira_id(+)=b.JIRA_ID and  c.project=b.project and b.updated>trunc(sysdate)-30  group by c.project_name"; 				
		}else{
			load30daysBack = "WHERE c.project in(select project from project_info) and a.jira_id(+)=b.JIRA_ID and  c.project=b.project and b.updated between ? AND ?  group by c.project_name";				
		}
		sql = sql.replace("WHERE", load30daysBack);
		//System.out.println("sql = "+sql);
		prepStat = con.prepareStatement(sql);	
		//System.out.println(sql);
		if(date1 != null && date2!=null){
			
			prepStat.setDate(1, new java.sql.Date(date1.getTime()));
			prepStat.setDate(2, new java.sql.Date(date2.getTime()));
			
		}
		rs = prepStat.executeQuery();		
		//System.out.println("~~~~~~~~~~~~~getting project list~~~~~~~~~~~~~~~~~~~");
		while(rs.next()){
			
				list1.add(rs.getString(1));
				list2.add(rs.getInt(2));
		}		
		
		projectlist.add(list1);
		projectlist.add(list2);
		//System.out.println(list.get(1));
		//System.out.println(list1);
		//System.out.println(projectlist);
		}catch(SQLException sqle){
			System.out.println(sqle);
			sqle.printStackTrace();
		}finally{
			DataSourceManager.getDSM().release(rs,prepStat,con);
		}
		//System.out.println("~!~!~!~!~!~!~!~!~!!"+list1);
		return projectlist;
	
	}
	
/*	
public static  List TotalEffort(String Month) {  Based on the Month , the values (effort) is being filtered
		
		System.out.println("In DAO........~~~~~~~~"+Month);
		
		List<String> list1 = new ArrayList<String>();
		List<Integer> list2 = new ArrayList<Integer>();
		List projectlist = new ArrayList();
		Connection con = null;
		PreparedStatement prepStat = null;
		ResultSet rs = null;
		try{
		con = DataSourceManager.getDSM().conFromPool();
		prepStat = con.prepareStatement("select c.project_name,sum(nvl(total_effort,null)) from jira_details a,jira_stats b,project_info c where c.project in(select project from project_info) and a.jira_id(+)=b.JIRA_ID and  c.project=b.project and to_char(updated, 'Mon')=?  group by c.project_name ,to_char(updated, 'Mon')");
		prepStat.setString(1, Month);
		rs = prepStat.executeQuery();		
		System.out.println("~~~~~~~~~~~~~getting project list~~~~~~~~~~~~~~~~~~~");
		while(rs.next()){
			
				list1.add(rs.getString(1));
				list2.add(rs.getInt(2));
		}		
		
		projectlist.add(list1);
		projectlist.add(list2);
		//System.out.println(list.get(1));
		//System.out.println(list1);
		System.out.println(projectlist);
		}catch(SQLException sqle){
			System.out.println(sqle);
			sqle.printStackTrace();
		}finally{
			DataSourceManager.getDSM().release(rs,prepStat,con);
		}
		System.out.println("~!~!~!~!~!~!~!~!~!!"+list1);
		return projectlist;
	
	}
	*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List genrateSingleChart(int selectedproject,Date singlechartdate1,Date singlechartdate2) {
		Connection con = null;
		List<String> l1=new ArrayList<String>();
		List<Integer> l2=new ArrayList<Integer>();
		List projectlist = new ArrayList();
		PreparedStatement prepStat = null;
		ResultSet rs = null;
		//System.out.println(selectedproject);		
	try{
		con = DataSourceManager.getDSM().conFromPool();
		String sql = "SELECT c.assignee,sum(a.total_effort) FROM jira_details a, jira_stats c WHERE";
		String load30daysBack = null;
		if(singlechartdate1 == null && singlechartdate1==null){
			load30daysBack = "WHERE a.jira_id IN (SELECT c.jira_id FROM jira_stats c WHERE c.project =?) AND a.total_effort != 0 AND a.jira_id = c.jira_id and c.updated>trunc(sysdate)-30 group by c.assignee"; 				
		}else{
			load30daysBack = "WHERE a.jira_id IN (SELECT c.jira_id FROM jira_stats c WHERE c.project =?) AND a.total_effort != 0 AND a.jira_id = c.jira_id and c.updated between ? AND ? group by c.assignee";				
		}
		sql = sql.replace("WHERE", load30daysBack);
		//sSystem.out.println("sql = "+sql);
		prepStat = con.prepareStatement(sql);	
		if(singlechartdate1 != null && singlechartdate2!=null){
			
			prepStat.setInt(1,selectedproject); 
			prepStat.setDate(2, new java.sql.Date(singlechartdate1.getTime()));
			prepStat.setDate(3, new java.sql.Date(singlechartdate2.getTime()));
			
		}
		else
		{
			prepStat.setInt(1,selectedproject); 
		}
		rs = prepStat.executeQuery();
		
		 while(rs.next())
		 	{
		 		l1.add(rs.getString(1));
		 		l2.add(rs.getInt(2));
			}
		//System.out.println(l1);
		//System.out.println(l2);
		projectlist.add(l1);
		projectlist.add(l2);
	
		 
	}catch(SQLException sqle){
		System.out.println(sqle);
		sqle.printStackTrace();
	}finally{
		DataSourceManager.getDSM().release(rs,prepStat,con);
	}
	return projectlist;
		
	}

	public static SortedMap<String, String> getmembers() {
		Connection con = null;
		SortedMap<String,String> members=new TreeMap<String,String>();	
		PreparedStatement prepStat = null;
		ResultSet rs = null;
		
		try{
			con = DataSourceManager.getDSM().conFromPool();
			prepStat = con.prepareStatement("SELECT USER_NAME,User_ID FROM JIRA_CREDS order by USER_NAME"); 
			 rs = prepStat.executeQuery();
			 while(rs.next())
			 {
				 members.put(rs.getString(1),rs.getString(2));
			 }
			}
		catch(SQLException sqle){
			System.out.println(sqle);
			sqle.printStackTrace();
		}finally{
			DataSourceManager.getDSM().release(rs,prepStat,con);
		}
		return members;
			
		}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getEmpEffort(String userid,Date empdate1,Date empdate2) {
		List<String> l1=new ArrayList<String>();
		List<Integer> l2=new ArrayList<Integer>();
		List projectlist = new ArrayList();
		Connection con = null;
		PreparedStatement prepStat = null;
		ResultSet rs = null;
		try{
		con = DataSourceManager.getDSM().conFromPool();
		String sql="select date1,sum(sum1) from (select sum(total_effort) as sum1, to_char(updated, 'MON-YY') as  date1 from jira_stats a,jira_details b WHERE a.jira_id=b.jira_id(+) and  a.assignee=? and total_effort!=0 and close_date is null  group by  to_char(updated, 'MON-YY') union all select  sum(total_effort)as sum1,to_char(close_date, 'MON-YY') as date1 from jira_stats a,jira_details b WHERE  a.jira_id=b.jira_id(+) and  b.close_date is not null and  a.assignee=? and total_effort!=0 group by  to_char(close_date, 'MON-YY') )group by date1 order by to_date( date1, 'MON-YY' )";
		String load30daysBack = null;
		if(empdate1 == null && empdate2==null){
			load30daysBack = "WHERE  a.updated>trunc(sysdate)-30 and"; 				
		}else{
			load30daysBack = "WHERE a.updated between ? and ? and ";				
		}
		sql = sql.replace("WHERE", load30daysBack);
		//System.out.println("sql = "+sql);
		prepStat = con.prepareStatement(sql);	
		if(empdate1 == null && empdate2==null){
			
			prepStat.setString(1,userid); 
			prepStat.setString(2, userid);
		}
		else
		{
			
			prepStat.setDate(1, new java.sql.Date(empdate1.getTime()));
			prepStat.setDate(2, new java.sql.Date(empdate2.getTime()));
			prepStat.setString(3,userid);
			prepStat.setDate(4, new java.sql.Date(empdate1.getTime()));
			prepStat.setDate(5, new java.sql.Date(empdate2.getTime()));
			prepStat.setString(6,userid);
			
		}
		//System.out.println("prepStat"+prepStat.toString());
		rs = prepStat.executeQuery();
		while(rs.next()){
			l1.add(rs.getString(1));
	 		l2.add(rs.getInt(2));
	 		//System.out.println(rs.getString(1)+"!~~~~~~~~~~~~~~~~~~~~~~~!"+rs.getString(2));
		}		
		projectlist.add(l1);
		projectlist.add(l2);
		//System.out.println(projectlist);
	}catch(SQLException sqle){
		System.out.println(sqle);
		sqle.printStackTrace();
	}finally{
		DataSourceManager.getDSM().release(rs,prepStat,con);
	}
	return projectlist;
		
	}

	public static SortedMap<String, Integer> getProjects() {
		Connection con = null;
		SortedMap<String,Integer> projects=new TreeMap<String,Integer>();	
		PreparedStatement prepStat = null;
		ResultSet rs = null;
		
		try{
			con = DataSourceManager.getDSM().conFromPool();
			prepStat = con.prepareStatement("SELECT PROJECT_NAME,PROJECT FROM PROJECT_INFO"); 
			 rs = prepStat.executeQuery();
			 while(rs.next())
			 {
				 projects.put(rs.getString(1),rs.getInt(2));
			 }
			}
		catch(SQLException sqle){
			System.out.println(sqle);
			sqle.printStackTrace();
		}finally{
			DataSourceManager.getDSM().release(rs,prepStat,con);
		}
		return projects;
			
		}
		
	
	
	
}
