package com.metro.dcms.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.metro.dcms.model.DATABASES;
import com.metro.dcms.model.LAST_COMMIT;
import com.metro.dcms.model.NETWORK_STATUS;
import com.metro.dcms.model.POSTS_REP_STATUS;
import com.metro.dcms.model.POSTS_SYNC_STATUS;
import com.metro.dcms.model.REPLICATION;
import com.metro.dcms.service.ReportService;




@Controller
public class HomeController implements ErrorController{
	
	List<POSTS_REP_STATUS> rep=new ArrayList<POSTS_REP_STATUS>();
	List<POSTS_SYNC_STATUS> sync=new ArrayList<POSTS_SYNC_STATUS>();
	List<NETWORK_STATUS> ns=new ArrayList<NETWORK_STATUS>();
	List<NETWORK_STATUS> all_ns=new ArrayList<NETWORK_STATUS>();
	ArrayList<String> ln=new ArrayList<String>();
	ArrayList<String> repArray=new ArrayList<String>();
	ArrayList<REPLICATION> rep_down_thread_list=new ArrayList<REPLICATION>();
	ArrayList<REPLICATION> rep_up_thread_list=new ArrayList<REPLICATION>();
	ArrayList<LAST_COMMIT> rep_last_commit_list=new ArrayList<LAST_COMMIT>();
	ArrayList<DATABASES> db_list=new ArrayList<DATABASES>();
	StringBuilder myLine=new StringBuilder();

	
	
	@Autowired
	private ReportService rs;
	
		
	
	@RequestMapping("/")
	public ModelAndView home(@RequestParam Map<String, String> request) {
		ModelAndView mv = new ModelAndView("Dashboard");
		
		try {
			
				String today=rs.currentDate();
				//System.out.println(today);
				String [] myDt=today.split("/");
				String dd=myDt[1];
				String mm=myDt[0];
				String yy=myDt[2];
			
			//request.get("mydate")
			//System.out.println("DATE "+rs.currentDate());
				
				//rs.getWindowsSpace();
				//rs.runReportScriptsToDb();
				//rs.getWindowsSpace();
				//rs.copyFile();
				
				//Get Pending Report
				rep=rs.post_rep_status();
				mv.addObject("REP_STATUS",rep);
				mv.addObject("today",rs.currentDate());
				mv.addObject("REP_NUMBER",rep.size());
				
				//Get Sync Details
				sync=rs.post_sync_status();
				mv.addObject("POST_SYNC",sync);
				mv.addObject("POST_NUMBER",sync.size());
				
				//Get Not in link Details
				ns=rs.get_all_network_not_in_link();
				mv.addObject("NET_STATUS",ns);
				mv.addObject("NET_NUMBER",ns.size());
						 							
				  
				  rep_down_thread_list=rs.dispalyReplicationDown();
				  mv.addObject("rep_down_list",rep_down_thread_list);
				  mv.addObject("rep_list_down_size",rep_down_thread_list.size());
				  
				  rep_up_thread_list=rs.dispalyReplicationUp();
				  mv.addObject("rep_up_list",rep_up_thread_list);
				  mv.addObject("rep_list_up_size",rep_up_thread_list.size());
				  //System.out.println(rep_thread_list.size());
				  
					/*
					 * for(int i=0;i<rep_up_thread_list.size();i++) {
					 * System.out.println(rep_up_thread_list.get(i).getRep_details()); }
					 */
					 
				  
				  
				  db_list=rs.dispalyDatabases();
				  mv.addObject("deb_list",db_list);
				  mv.addObject("deb_list_size",db_list.size());
				  for(int i=0;i<db_list.size();i++) {
						  mv.addObject(db_list.get(i).getDbName(), db_list.get(i).getDbSize());
				  }
					 
				 
				  	
				/*
				 * List<REP_LINES> serverSpace1=new ArrayList<REP_LINES>();
				 * serverSpace1=rs.runSpaceDetailsRepbackup();
				 * mv.addObject("repused",serverSpace1.get(0).getRepLine().substring(83, 86));
				 * mv.addObject("repavailable",serverSpace1.get(0).getRepLine().substring(89,
				 * 92));
				 * 
				 * List<REP_LINES> serverSpace131=new ArrayList<REP_LINES>();
				 * serverSpace131=rs.runSpaceDetails131();
				 * mv.addObject("used131",serverSpace131.get(0).getRepLine().substring(83, 86));
				 * mv.addObject("available131",serverSpace131.get(0).getRepLine().substring(89,
				 * 92)); //System.out.println(serverSpace131.get(0).getRepLine().toString());
				 * 
				 * List<REP_LINES> serverSpace101=new ArrayList<REP_LINES>();
				 * serverSpace101=rs.runSpaceDetails101(); mv.addObject("used101",30);
				 * mv.addObject("available101",70);
				 * //System.out.println(serverSpace101.get(0).getRepLine().substring(83, 86));
				 */				
				
				/*
				 * List<REP_LINES> repCheckDaily=new ArrayList<REP_LINES>();
				 * repCheckDaily=rs.checkReport(dd, mm, yy, "D");
				 * mv.addObject("repStatusDaily",repCheckDaily.get(0).getRepLine());
				 * System.out.println(repCheckDaily.get(0).getRepLine().toString());
				 * 
				 * List<REP_LINES> repCheckPeriodic=new ArrayList<REP_LINES>();
				 * repCheckPeriodic=rs.checkReport("01", "12", "2020", "P");
				 * mv.addObject("repStatusPeriodic",repCheckPeriodic.get(0).getRepLine());
				 * mv.addObject("periodicDate", mm+"-"+yy);
				 * //System.out.println(repCheckPeriodic.get(0).getRepLine().toString());
				 * 
				 * List<REP_LINES> repCheckMonthly=new ArrayList<REP_LINES>();
				 * repCheckMonthly=rs.checkReport(dd, mm, yy, "M");
				 * mv.addObject("repStatusMonthly",repCheckMonthly.get(0).getRepLine());
				 * mv.addObject("monthlyDate", mm+"-"+yy);
				 */
				
				
				
			if (sync.size() == 0) {
				mv.addObject("sync", "NO PENDING");
			}
			if (ns.size() == 0) {
				mv.addObject("network", "0");
			}
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
		
		return mv;
	}
	
	@RequestMapping("/error")
    public ModelAndView handleError() {
		ModelAndView mv = new ModelAndView("error");
        //do something like logging
        return mv;
    }
	@RequestMapping(value = "/SyncList", method = RequestMethod.GET)
	public @ResponseBody ModelAndView synclist() {
		ModelAndView mv = new ModelAndView("Sync_List");	
		//Get Sync Details
		sync=rs.post_sync_status();
		mv.addObject("POST_SYNC",sync);
		mv.addObject("POST_NUMBER",sync.size());
		if (sync.size() == 0) {
			mv.addObject("sync", "NO PENDING");
		}
		return mv;
	}
	@RequestMapping(value = "/Sync", method = RequestMethod.GET)
	public @ResponseBody ModelAndView sync() {
		ModelAndView mv = new ModelAndView("Sync");	
		return mv;
	}
	
	@RequestMapping(value = "/SyncTerm", method = RequestMethod.POST)
	public @ResponseBody String syncterminal(@RequestParam Map<String, String> request) {
		String terminal=request.get("ter");
		StringBuilder syncLine=new StringBuilder();
		//System.out.println(terminal);
		ln=rs.syncTerminal(terminal);
		for(int i=0;i<ln.size();i++)
		{
			syncLine.append(ln.get(i).toString());
			//System.out.print(ln.get(i).toString());
			syncLine.append(System.getProperty("line.separator"));
		}
		//System.out.print(syncLine.toString());
		return syncLine.toString();
	}
	
	
	@RequestMapping(value = "/Report", method = RequestMethod.GET)
	public @ResponseBody ModelAndView report() {
		ModelAndView mv = new ModelAndView("Report");		
		return mv;
	}
	
	@RequestMapping(value = "/ReportList", method = RequestMethod.GET)
	public @ResponseBody ModelAndView reportlist() {
		ModelAndView mv = new ModelAndView("Report_List");	
		//Get Pending Report
		rep=rs.post_rep_status();
		mv.addObject("REP_STATUS",rep);
		mv.addObject("today",rs.currentDate());
		mv.addObject("REP_NUMBER",rep.size());
		if (rep.size() == 0) {
			mv.addObject("rep", "NO PENDING");
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/Network", method = RequestMethod.GET)
	public @ResponseBody ModelAndView network() {
		ModelAndView mv = new ModelAndView("Network");
		//Get all network Details
		all_ns=rs.get_all_network();
		mv.addObject("ALL_NET_STATUS",all_ns);
		//mv.addObject("ALL_NET_NUMBER",ns.size());
		  
		
		/*
		 * for(int i=0;i<all_ns.size();i++) {
		 * System.out.println(all_ns.get(i).getTerminal()); }
		 */
		 
		return mv;
	}
	@RequestMapping(value = "/NetworkList", method = RequestMethod.GET)
	public @ResponseBody ModelAndView networklist() {
		ModelAndView mv = new ModelAndView("Network_List");	
		List<NETWORK_STATUS> fList=new ArrayList<NETWORK_STATUS>();
		//Get Pending Report
		fList=rs.get_all_network_not_in_link();
		mv.addObject("NET_STATUS",fList);
		mv.addObject("today",rs.currentDate());
		mv.addObject("NET_NUMBER",fList.size());
		if (fList.size() == 0) {
			mv.addObject("ListSize", "NO PENDING");
		}
		return mv;
	}
	
	@RequestMapping(value = "/ThreadUpList", method = RequestMethod.GET)
	public @ResponseBody ModelAndView threadup() {
		ModelAndView mv = new ModelAndView("ThreadUpList");	
		 
		 rep_up_thread_list.clear();
		 rep_up_thread_list=rs.dispalyReplicationUp();
		 mv.addObject("rep_up_list",rep_up_thread_list);
		 mv.addObject("rep_list_up_size",rep_up_thread_list.size());
		 if (rep_up_thread_list.size() == 0) {
				mv.addObject("ListSize", "NO PENDING");
			}
		 return mv;
	}
	
	@RequestMapping(value = "/ThreadDownList", method = RequestMethod.GET)
	public @ResponseBody ModelAndView threaddown() {
		ModelAndView mv = new ModelAndView("ThreadDownList");	
		 
		 rep_down_thread_list.clear();
		 rep_down_thread_list=rs.dispalyReplicationDown();
		 mv.addObject("rep_down_list",rep_down_thread_list);
		 mv.addObject("rep_list_down_size",rep_down_thread_list.size());
		 if (rep_down_thread_list.size() == 0) {
				mv.addObject("ListSize", "NO PENDING");
			}
		 return mv;
	}
	
	@RequestMapping(value = "/ReplicLastCommit", method = RequestMethod.GET)
	public @ResponseBody ModelAndView lastCommit() {
		ModelAndView mv = new ModelAndView("ReplicaLastCommit");	
		 
		 rep_last_commit_list.clear();
		 rep_last_commit_list=rs.dispalyReplicaLastCommit();
		 mv.addObject("rep_last_commit",rep_last_commit_list);
		 mv.addObject("rep_last_commit_size",rep_last_commit_list.size());
		 if (rep_last_commit_list.size() == 0) {
				mv.addObject("ListSize", "NO RECORD FOUND");
			}
		 return mv;
	}
	
	
	
	@RequestMapping(value = "/runDailyRep", method = RequestMethod.POST)
	 public @ResponseBody String runDRep(@RequestParam Map<String, String> request) { 
		
	 myLine.setLength(0);
		
	 String mydate=request.get("mydate"); 
	 String [] myDt=mydate.split("-"); 
	 String dd=myDt[2]; 
	 String mm=myDt[1]; 
	 String yy=myDt[0];
	 
	 	repArray=rs.runDailyReport(dd,mm,yy,request.get("myloc"));
		for(int i=0;i<repArray.size();i++)
		{
			myLine.append(repArray.get(i).toString());
			//System.out.print(ln.get(i).toString());
			myLine.append(System.getProperty("line.separator"));
		}
	 
	 return myLine.toString(); 
	 
	 }
	
	@RequestMapping(value = "/runPerRep", method = RequestMethod.POST)
	 public @ResponseBody String runPRep(@RequestParam Map<String, String> request) { 
		
	myLine.setLength(0);
		
	 String mydate=request.get("mydate"); 
	 String [] myDt=mydate.split("-"); 
	 String dd=myDt[2]; 
	 String mm=myDt[1]; 
	 String yy=myDt[0];
	 
	 	repArray=rs.runPerReport(dd,mm,yy,request.get("myloc"));
		for(int i=0;i<repArray.size();i++)
		{
			myLine.append(repArray.get(i).toString());
			//System.out.print(ln.get(i).toString());
			myLine.append(System.getProperty("line.separator"));
		}
	 
	 return myLine.toString(); 
	 
	 }
	
	@RequestMapping(value = "/runMonthlyRep", method = RequestMethod.POST)
	 public @ResponseBody String runMRep(@RequestParam Map<String, String> request) { 

	 myLine.setLength(0);
		
	 String mydate=request.get("mydate"); 
	 String [] myDt=mydate.split("-"); 
	 String dd=myDt[2]; 
	 String mm=myDt[1]; 
	 String yy=myDt[0];
	 
	 repArray=rs.runMonReport(dd,mm,yy,request.get("myloc"));
		for(int i=0;i<repArray.size();i++)
		{
			myLine.append(repArray.get(i).toString());
			//System.out.print(ln.get(i).toString());
			myLine.append(System.getProperty("line.separator"));
		}
	 
	 return myLine.toString(); 
	 
	 }
	
	@RequestMapping(value = "/runCronDailyReport", method = RequestMethod.POST)
	 public @ResponseBody String runDcronRep() { 

	 myLine.setLength(0);
		
	 
	 repArray=rs.runCronDailyReport();
		for(int i=0;i<repArray.size();i++)
		{
			myLine.append(repArray.get(i).toString());
			//System.out.print(ln.get(i).toString());
			myLine.append(System.getProperty("line.separator"));
		}
	 
	 return myLine.toString(); 
	 
	 }
	
	@RequestMapping(value = "/runCronPeriodicReport", method = RequestMethod.POST)
	 public @ResponseBody String runCronPeriodicReport() { 

	 myLine.setLength(0);
		
	 
	 repArray=rs.runCronPeriodicReport();
		for(int i=0;i<repArray.size();i++)
		{
			myLine.append(repArray.get(i).toString());
			//System.out.print(ln.get(i).toString());
			myLine.append(System.getProperty("line.separator"));
		}
	 
	 return myLine.toString(); 
	 
	 }
	
	@RequestMapping(value = "/runCronMonthlyReport", method = RequestMethod.POST)
	 public @ResponseBody String runCronMonthlyReport() { 

	 myLine.setLength(0);
		
	 
	 repArray=rs.runCronMonthlyReport();
		for(int i=0;i<repArray.size();i++)
		{
			myLine.append(repArray.get(i).toString());
			//System.out.print(ln.get(i).toString());
			myLine.append(System.getProperty("line.separator"));
		}
	 
	 return myLine.toString(); 
	 
	 }
	
	@RequestMapping(value = "/runDailyJMS", method = RequestMethod.POST)
	 public @ResponseBody String runDailyJMS(@RequestParam Map<String, String> request) { 

	 myLine.setLength(0);
		
	 String mydate=request.get("mydate"); 
	 String [] myDt=mydate.split("-"); 
	 String dd=myDt[2]; 
	 String mm=myDt[1]; 
	 String yy=myDt[0];
	 
	 repArray=rs.runDailyJMS(dd,mm,yy,request.get("myloc"));
		for(int i=0;i<repArray.size();i++)
		{
			myLine.append(repArray.get(i).toString());
			//System.out.print(ln.get(i).toString());
			myLine.append(System.getProperty("line.separator"));
		}
	 
	 return myLine.toString(); 
	 
	 }
	
	@RequestMapping(value = "/runPeriodicJMS", method = RequestMethod.POST)
	 public @ResponseBody String runPeriodicJMS(@RequestParam Map<String, String> request) { 

	 myLine.setLength(0);
		
	 String mydate=request.get("mydate"); 
	 String [] myDt=mydate.split("-"); 
	 String dd=myDt[2]; 
	 String mm=myDt[1]; 
	 String yy=myDt[0];
	 
	 repArray=rs.runPeriodicJMS(dd,mm,yy,request.get("myloc"));
		for(int i=0;i<repArray.size();i++)
		{
			myLine.append(repArray.get(i).toString());
			//System.out.print(ln.get(i).toString());
			myLine.append(System.getProperty("line.separator"));
		}
	 
	 return myLine.toString(); 
	 
	 }
	
	@RequestMapping(value = "/runMonthlyJMS", method = RequestMethod.POST)
	 public @ResponseBody String runMonthlyJMS(@RequestParam Map<String, String> request) { 

	 myLine.setLength(0);
		
	 String mydate=request.get("mydate"); 
	 String [] myDt=mydate.split("-"); 
	 String dd=myDt[2]; 
	 String mm=myDt[1]; 
	 String yy=myDt[0];
	 
	 repArray=rs.runMonthlyJMS(dd,mm,yy,request.get("myloc"));
		for(int i=0;i<repArray.size();i++)
		{
			myLine.append(repArray.get(i).toString());
			//System.out.print(ln.get(i).toString());
			myLine.append(System.getProperty("line.separator"));
		}
	 
	 return myLine.toString(); 
	 
	 }
	
}
