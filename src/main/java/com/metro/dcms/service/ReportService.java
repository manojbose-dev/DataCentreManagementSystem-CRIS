package com.metro.dcms.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.metro.dcms.connection.connectHCP_ASA;
import com.metro.dcms.connection.connectHCP_ASE;
import com.metro.dcms.model.DATABASES;
import com.metro.dcms.model.LAST_COMMIT;
import com.metro.dcms.model.NETWORK_STATUS;
import com.metro.dcms.model.POSTS_REP_STATUS;
import com.metro.dcms.model.POSTS_SYNC_STATUS;
import com.metro.dcms.model.REPLICATION;
import com.metro.dcms.model.REP_LINES;




@Service
public class ReportService {

	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	Statement stmt;
	String dt;
	int exit=1;
	ArrayList<String> repLog=new ArrayList<String>();
	
	public List<POSTS_REP_STATUS> post_rep_status(){
		
		
		List<POSTS_REP_STATUS> mList=new ArrayList<POSTS_REP_STATUS>();
		POSTS_REP_STATUS prs;
		try {
			
			dt=currentDate();
			//System.out.println(dt);
			  con = connectHCP_ASA.getConnection();
			  stmt=con.createStatement(); 
			  rs=stmt.executeQuery("select * from report_status order by rep_date desc");
			  while(rs.next()) {
			
				  prs=new POSTS_REP_STATUS(rs.getString(1),rs.getString(2),rs.getString(3));
				  mList.add(prs);
				 
			  }
			con.close(); 
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return mList;
	
	}
	
	
	  public List<POSTS_REP_STATUS> post_rep_status(String mydate){
	  
	  
	  List<POSTS_REP_STATUS> mList=new ArrayList<POSTS_REP_STATUS>();
	  POSTS_REP_STATUS prs; try {
	  
	  //System.out.println(mydate); 
	  con = connectHCP_ASA.getConnection();
	  stmt=con.createStatement();
	  rs=stmt.executeQuery("select * from report_status where rep_date='"+mydate+"'"); 
	  while(rs.next()) {
	  
	  prs=new POSTS_REP_STATUS(rs.getString(1),rs.getString(2),rs.getString(3));
	  mList.add(prs); //System.out.println(prs); 
	  } 
	  con.close(); 
	  }
	  catch(SQLException ex) { ex.printStackTrace(); } catch(Exception e) {
	  e.printStackTrace(); }
	  
	  	  
	  return mList;
	  
	  }
	  
	  public List<POSTS_SYNC_STATUS> post_sync_status(){
		  
		  
		  List<POSTS_SYNC_STATUS> mList=new ArrayList<POSTS_SYNC_STATUS>();
		  POSTS_SYNC_STATUS pss; 
		  try { //select * from ml_subscription where last_download_time >='04/01/2021' and last_upload_time <='05/31/2021'
		  
		  //System.out.println(beforeOneMonth()); 
		  //System.out.println(today());
		  
		  con = connectHCP_ASE.getConnection(); 
		  stmt=con.createStatement();
		  rs=stmt.executeQuery("select publication_name,last_upload_time,last_download_time from ml_subscription where last_download_time >='"+beforeOneMonth()+"' and last_upload_time <='"+today()+"'"); 
		  while(rs.next())
		  {
		  
		  pss=new POSTS_SYNC_STATUS(rs.getString(1),rs.getString(2),rs.getString(3));
		  mList.add(pss);
		  //System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)); 
		  } 
		  con.close(); 
		  } 
		  catch(SQLException ex) 
		  { 
			  ex.printStackTrace(); 
		  }
		  catch(Exception e) { e.printStackTrace(); }
		  
		  
		  return mList;
		  
		  }
	  
	  
	  public String currentDate() { DateFormat df = new
	  SimpleDateFormat("dd/MM/yyyy"); df.setTimeZone(TimeZone.getTimeZone("IST"));
	  Date dt=new Date();
	  
	  Calendar c = Calendar.getInstance(); 
	  c.setTime(dt); 
	  c.add(Calendar.DATE, -1);
	  
	  // Convert calendar back to Date 
	  Date currentDatePlusOne = c.getTime();
	  return (df.format(currentDatePlusOne)); 
	  }
	  
	  public String today() { 
	  DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	  df.setTimeZone(TimeZone.getTimeZone("IST")); 
	  Date dt=new Date(); 
	  return df.format(dt);
	  
	  } 
	  public String beforeOneMonth() { 
	  DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
	  df.setTimeZone(TimeZone.getTimeZone("IST"));
	  Date dt=new Date();
	  
	  Calendar c = Calendar.getInstance(); c.setTime(dt); c.add(Calendar.DATE,-30);
	  
	  // Convert calendar back to Date 
	  Date currentDateMinusThirty = c.getTime();
	  return (df.format(currentDateMinusThirty));
	  
	  }
	  
	  public String timeStamp() { 
		  String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		  return timeStamp;
	  }
	  
	 		 	  
		
		  public ArrayList<String> runDailyReport(String d,String m,String y,String loc) {
		  
			  String REMOTE_HOST = "10.217.1.132";
			  String USERNAME = "cris";
			  String PASSWORD = "cris123";
			  int REMOTE_PORT = 22;
			  int SESSION_TIMEOUT = 10000;
			  int CHANNEL_TIMEOUT = 10000;
			  repLog.clear(); 
			  
		  String remoteShellScript = "/home/cris/sh/daily_rep_manual";
		  
		  Session jschSession = null;
		  
		  try {
		  
		  JSch jsch = new JSch();
		  
		  
		  
		  jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
		  jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		  
		  // not recommend, uses jsch.setKnownHosts
		  jschSession.setConfig("StrictHostKeyChecking", "no");
		  
		  // authenticate using password 
		  jschSession.setPassword(PASSWORD);
		  
		  // 10 seconds timeout session 
		  jschSession.connect(SESSION_TIMEOUT);
		  
		  ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		  
		  // run a shell script 
		  channelExec.setCommand("sh " + remoteShellScript +" "+d+" "+ m +" "+ y + " " +loc);
		  
		  // display errors to System.err 
		  channelExec.setErrStream(System.err);
		  
		  InputStream in = channelExec.getInputStream();
		  
		  // 5 seconds timeout channel 
		  channelExec.connect(CHANNEL_TIMEOUT);
		  
		  // read the result from remote server 
		  byte[] tmp = new byte[1024];
		  while(true) 
		  { 
			  while (in.available() > 0) 
			  { 
				  int i = in.read(tmp, 0, 1024); 
				  if(i <0) break; 
				  repLog.add(new String(tmp, 0, i));
			  }
		  if(channelExec.isClosed()) 
		  { 
			  if (in.available() > 0) 
				  continue;
			  exit=channelExec.getExitStatus(); 
			  break; 
		  } 
		  try { 
			  Thread.sleep(1000); 
			  } 
		  catch(Exception ee) { } 
		  
		  }
		  
		  	channelExec.disconnect();
		  
		  } catch (JSchException | IOException e) {
		  
			  e.printStackTrace();
		  
		  } 
		  finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
			 }
		  
		  	return repLog; 
		  }
		 
		  public ArrayList<String> runPerReport(String d,String m,String y,String loc) {
			  
			  String REMOTE_HOST = "10.217.1.132";
			  String USERNAME = "cris";
			  String PASSWORD = "cris123";
			  int REMOTE_PORT = 22;
			  int SESSION_TIMEOUT = 10000;
			  int CHANNEL_TIMEOUT = 10000;
			  repLog.clear(); 
			  
		  String remoteShellScript = "/home/cris/sh/KMTS_REP_PER_2016";
		  
		  Session jschSession = null;
		  
		  try {
		  
		  JSch jsch = new JSch();
		  
		  
		  
		  jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
		  jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		  
		  // not recommend, uses jsch.setKnownHosts
		  jschSession.setConfig("StrictHostKeyChecking", "no");
		  
		  // authenticate using password 
		  jschSession.setPassword(PASSWORD);
		  
		  // 10 seconds timeout session 
		  jschSession.connect(SESSION_TIMEOUT);
		  
		  ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		  
		  // run a shell script 
		  channelExec.setCommand("sh " + remoteShellScript +" "+d+" "+ m +" "+ y + " " +loc);
		  
		  // display errors to System.err 
		  channelExec.setErrStream(System.err);
		  
		  InputStream in = channelExec.getInputStream();
		  
		  // 5 seconds timeout channel 
		  channelExec.connect(CHANNEL_TIMEOUT);
		  
		  // read the result from remote server 
		  byte[] tmp = new byte[1024];
		  while(true) 
		  { 
			  while (in.available() > 0) 
			  { 
				  int i = in.read(tmp, 0, 1024); 
				  if(i <0) break; 
				  System.out.print(new String(tmp, 0, i)); 
				  repLog.add(new String(tmp, 0, i));
			  }
		  if(channelExec.isClosed()) 
		  { 
			  if (in.available() > 0) 
				  continue;
			  exit=channelExec.getExitStatus(); 
			  break; 
		  } 
		  try { 
			  Thread.sleep(1000); 
			  } 
		  catch(Exception ee) { } 
		  
		  }
		  
		  	channelExec.disconnect();
		  
		  } catch (JSchException | IOException e) {
		  
			  e.printStackTrace();
		  
		  } 
		  finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
			 }
		  
		  	return repLog; 
		  } 
		  
		  
		  public ArrayList<String> runMonReport(String d,String m,String y,String loc) {
			  
			  String REMOTE_HOST = "10.217.1.132";
			  String USERNAME = "cris";
			  String PASSWORD = "cris123";
			  int REMOTE_PORT = 22;
			  int SESSION_TIMEOUT = 10000;
			  int CHANNEL_TIMEOUT = 10000;
			  repLog.clear();
			  
		  String remoteShellScript = "/home/cris/sh/KMTS_REP_MON_2016";
		  
		  Session jschSession = null;
		  
		  try {
		  
		  JSch jsch = new JSch();
		  
		  
		  
		  jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
		  jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		  
		  // not recommend, uses jsch.setKnownHosts
		  jschSession.setConfig("StrictHostKeyChecking", "no");
		  
		  // authenticate using password 
		  jschSession.setPassword(PASSWORD);
		  
		  // 10 seconds timeout session 
		  jschSession.connect(SESSION_TIMEOUT);
		  
		  ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		  
		  // run a shell script 
		  channelExec.setCommand("sh " + remoteShellScript +" "+d+" "+ m +" "+ y + " " +loc);
		  
		  // display errors to System.err 
		  channelExec.setErrStream(System.err);
		  
		  InputStream in = channelExec.getInputStream();
		  
		  // 5 seconds timeout channel 
		  channelExec.connect(CHANNEL_TIMEOUT);
		  
		  // read the result from remote server 
		  byte[] tmp = new byte[1024];
		  while(true) 
		  { 
			  while (in.available() > 0) 
			  { 
				  int i = in.read(tmp, 0, 1024); 
				  if(i <0) break; 
				  System.out.print(new String(tmp, 0, i));
				  repLog.add(new String(tmp, 0, i));
			  }
		  if(channelExec.isClosed()) 
		  { 
			  if (in.available() > 0) 
				  continue;
			  exit=channelExec.getExitStatus(); 
			  break; 
		  } 
		  try { 
			  Thread.sleep(1000); 
			  } 
		  catch(Exception ee) { } 
		  
		  }
		  
		  	channelExec.disconnect();
		  
		  } catch (JSchException | IOException e) {
		  
			  e.printStackTrace();
		  
		  } 
		  finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
			 }
		  
		  	return repLog; 
		  }
		  
		  
 public ArrayList<String> runCronDailyReport() {
			  
			  String REMOTE_HOST = "10.217.1.132";
			  String USERNAME = "cris";
			  String PASSWORD = "cris123";
			  int REMOTE_PORT = 22;
			  int SESSION_TIMEOUT = 10000;
			  int CHANNEL_TIMEOUT = 10000;
			  repLog.clear();
			  
		  String remoteShellScript = "/home/cris/sh/metrorep_daily.sh";
		  
		  Session jschSession = null;
		  
		  try {
		  
		  JSch jsch = new JSch();
		  
		  jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
		  jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		  
		  // not recommend, uses jsch.setKnownHosts
		  jschSession.setConfig("StrictHostKeyChecking", "no");
		  
		  // authenticate using password 
		  jschSession.setPassword(PASSWORD);
		  
		  // 10 seconds timeout session 
		  jschSession.connect(SESSION_TIMEOUT);
		  
		  ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		  
		  // run a shell script 
		  channelExec.setCommand("sh " + remoteShellScript);
		  
		  // display errors to System.err 
		  channelExec.setErrStream(System.err);
		  
		  InputStream in = channelExec.getInputStream();
		  
		  // 5 seconds timeout channel 
		  channelExec.connect(CHANNEL_TIMEOUT);
		  
		  // read the result from remote server 
		  byte[] tmp = new byte[1024];
		  while(true) 
		  { 
			  while (in.available() > 0) 
			  { 
				  int i = in.read(tmp, 0, 1024); 
				  if(i <0) break; 
				  System.out.print(new String(tmp, 0, i));
				  repLog.add(new String(tmp, 0, i));
			  }
		  if(channelExec.isClosed()) 
		  { 
			  if (in.available() > 0) 
				  continue;
			  exit=channelExec.getExitStatus(); 
			  break; 
		  } 
		  try { 
			  Thread.sleep(1000); 
			  } 
		  catch(Exception ee) { } 
		  
		  }
		  
		  	channelExec.disconnect();
		  
		  } catch (JSchException | IOException e) {
		  
			  e.printStackTrace();
		  
		  } 
		  finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
			 }
		  
		  	return repLog; 
		  }
		  
 public ArrayList<String> runCronPeriodicReport() {
	  
	  String REMOTE_HOST = "10.217.1.132";
	  String USERNAME = "cris";
	  String PASSWORD = "cris123";
	  int REMOTE_PORT = 22;
	  int SESSION_TIMEOUT = 10000;
	  int CHANNEL_TIMEOUT = 10000;
	  repLog.clear();
	  
		 String remoteShellScript = "/home/cris/sh/periodic_cron.script";
		 
		 Session jschSession = null;
		 
		 try {
		 
		 JSch jsch = new JSch();
		 
		 
		 
		 jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
		 jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		 
		 // not recommend, uses jsch.setKnownHosts
		 jschSession.setConfig("StrictHostKeyChecking", "no");
		 
		 // authenticate using password 
		 jschSession.setPassword(PASSWORD);
		 
		 // 10 seconds timeout session 
		 jschSession.connect(SESSION_TIMEOUT);
		 
		 ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		 
		 // run a shell script 
		 channelExec.setCommand("sh " + remoteShellScript);
		 
		 // display errors to System.err 
		 channelExec.setErrStream(System.err);
		 
		 InputStream in = channelExec.getInputStream();
		 
		 // 5 seconds timeout channel 
		 channelExec.connect(CHANNEL_TIMEOUT);
		 
		 // read the result from remote server 
		 byte[] tmp = new byte[1024];
		 while(true) 
		 { 
			  while (in.available() > 0) 
			  { 
				  int i = in.read(tmp, 0, 1024); 
				  if(i <0) break; 
				  System.out.print(new String(tmp, 0, i)); 
				  repLog.add(new String(tmp, 0, i));
			  }
		 if(channelExec.isClosed()) 
		 { 
			  if (in.available() > 0) 
				  continue;
			  System.out.println("exit-status: " + channelExec.getExitStatus());
			  exit=channelExec.getExitStatus(); 
			  break; 
		 } 
		 try { 
			  Thread.sleep(1000); 
			  } 
		 catch(Exception ee) { } 
		 
		 }
		 
		 	channelExec.disconnect();
		 
		 } catch (JSchException | IOException e) {
		 
			  e.printStackTrace();
		 
		 } 
		 finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
			 }
 
 	return repLog; 
 }	  
		  
 public ArrayList<String> runCronMonthlyReport() {
	  
	  String REMOTE_HOST = "10.217.1.132";
	  String USERNAME = "cris";
	  String PASSWORD = "cris123";
	  int REMOTE_PORT = 22;
	  int SESSION_TIMEOUT = 10000;
	  int CHANNEL_TIMEOUT = 10000;
	  repLog.clear();
	  
			 String remoteShellScript = "/home/cris/sh/monthly_cron.script";
			 
			 Session jschSession = null;
			 
			 try {
			 
			 JSch jsch = new JSch();
			 
			 
			 
			 jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
			 jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
			 
			 // not recommend, uses jsch.setKnownHosts
			 jschSession.setConfig("StrictHostKeyChecking", "no");
			 
			 // authenticate using password 
			 jschSession.setPassword(PASSWORD);
			 
			 // 10 seconds timeout session 
			 jschSession.connect(SESSION_TIMEOUT);
			 
			 ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
			 
			 // run a shell script 
			 channelExec.setCommand("sh " + remoteShellScript);
			 
			 // display errors to System.err 
			 channelExec.setErrStream(System.err);
			 
			 InputStream in = channelExec.getInputStream();
			 
			 // 5 seconds timeout channel 
			 channelExec.connect(CHANNEL_TIMEOUT);
			 
			 // read the result from remote server 
			 byte[] tmp = new byte[1024];
			 while(true) 
			 { 
				  while (in.available() > 0) 
				  { 
					  int i = in.read(tmp, 0, 1024); 
					  if(i <0) break; 
					  System.out.print(new String(tmp, 0, i));
					  repLog.add(new String(tmp, 0, i));
				  }
			 if(channelExec.isClosed()) 
			 { 
				  if (in.available() > 0) 
					  continue;
				  exit=channelExec.getExitStatus(); 
				  break; 
			 } 
			 try { 
				  Thread.sleep(1000); 
				  } 
			 catch(Exception ee) { } 
			 
			 }
			 
			 	channelExec.disconnect();
			 
			 } catch (JSchException | IOException e) {
			 
				  e.printStackTrace();
			 
			 } 
			 finally { 
					  if (jschSession != null) 
					  { 
						  jschSession.disconnect(); 
					  } 
				 }
 
 	return repLog; 
 }		  

 public ArrayList<String> runDailyJMS(String d,String m,String y,String loc) {
	  
	  String REMOTE_HOST = "10.217.1.132";
	  String USERNAME = "cris";
	  String PASSWORD = "cris123";
	  int REMOTE_PORT = 22;
	  int SESSION_TIMEOUT = 10000;
	  int CHANNEL_TIMEOUT = 10000;
	  repLog.clear();
	  
		 String remoteShellScript = "/home/cris/sh/JMS_report.script";
		 
		 Session jschSession = null;
		 
		 try {
		 
		 JSch jsch = new JSch();
		 
		 
		 
		 jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
		 jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		 
		 // not recommend, uses jsch.setKnownHosts
		 jschSession.setConfig("StrictHostKeyChecking", "no");
		 
		 // authenticate using password 
		 jschSession.setPassword(PASSWORD);
		 
		 // 10 seconds timeout session 
		 jschSession.connect(SESSION_TIMEOUT);
		 
		 ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		 
		 // run a shell script 
		 channelExec.setCommand("sh " + remoteShellScript +" "+d+" "+ m +" "+ y + " " +loc);
		 
		 // display errors to System.err 
		 channelExec.setErrStream(System.err);
		 
		 InputStream in = channelExec.getInputStream();
		 
		 // 5 seconds timeout channel 
		 channelExec.connect(CHANNEL_TIMEOUT);
		 
		 // read the result from remote server 
		 byte[] tmp = new byte[1024];
		 while(true) 
		 { 
			  while (in.available() > 0) 
			  { 
				  int i = in.read(tmp, 0, 1024); 
				  if(i <0) break; 
				  System.out.print(new String(tmp, 0, i));
				  repLog.add(new String(tmp, 0, i));
			  }
		 if(channelExec.isClosed()) 
		 { 
			  if (in.available() > 0) 
				  continue;
			  exit=channelExec.getExitStatus(); 
			  break; 
		 } 
		 try { 
			  Thread.sleep(1000); 
			  } 
		 catch(Exception ee) { } 
		 
		 }
		 
		 	channelExec.disconnect();
		 
		 } catch (JSchException | IOException e) {
		 
			  e.printStackTrace();
		 
		 } 
		 finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
			 }
		 
		 	return repLog; 
 }
		  
 public ArrayList<String> runPeriodicJMS(String d,String m,String y,String loc) {
	  
	  String REMOTE_HOST = "10.217.1.132";
	  String USERNAME = "cris";
	  String PASSWORD = "cris123";
	  int REMOTE_PORT = 22;
	  int SESSION_TIMEOUT = 10000;
	  int CHANNEL_TIMEOUT = 10000;
	  repLog.clear();
	  
		 String remoteShellScript = "/home/cris/sh/KMTS_REP_PER_2016_jmsdb";
		 
		 Session jschSession = null;
		 
		 try {
		 
		 JSch jsch = new JSch();
		 
		 
		 
		 jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
		 jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		 
		 // not recommend, uses jsch.setKnownHosts
		 jschSession.setConfig("StrictHostKeyChecking", "no");
		 
		 // authenticate using password 
		 jschSession.setPassword(PASSWORD);
		 
		 // 10 seconds timeout session 
		 jschSession.connect(SESSION_TIMEOUT);
		 
		 ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		 
		 // run a shell script 
		 channelExec.setCommand("sh " + remoteShellScript +" "+d+" "+ m +" "+ y + " " +loc);
		 
		 // display errors to System.err 
		 channelExec.setErrStream(System.err);
		 
		 InputStream in = channelExec.getInputStream();
		 
		 // 5 seconds timeout channel 
		 channelExec.connect(CHANNEL_TIMEOUT);
		 
		 // read the result from remote server 
		 byte[] tmp = new byte[1024];
		 while(true) 
		 { 
			  while (in.available() > 0) 
			  { 
				  int i = in.read(tmp, 0, 1024); 
				  if(i <0) break; 
				  System.out.print(new String(tmp, 0, i));
				  repLog.add(new String(tmp, 0, i));
			  }
		 if(channelExec.isClosed()) 
		 { 
			  if (in.available() > 0) 
				  continue;
			  exit=channelExec.getExitStatus(); 
			  break; 
		 } 
		 try { 
			  Thread.sleep(1000); 
			  } 
		 catch(Exception ee) { } 
		 
		 }
		 
		 	channelExec.disconnect();
		 
		 } catch (JSchException | IOException e) {
		 
			  e.printStackTrace();
		 
		 } 
		 finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
			 }
		 
		 	return repLog; 
 }
 
 public ArrayList<String> runMonthlyJMS(String d,String m,String y,String loc) {
	  
	  String REMOTE_HOST = "10.217.1.132";
	  String USERNAME = "cris";
	  String PASSWORD = "cris123";
	  int REMOTE_PORT = 22;
	  int SESSION_TIMEOUT = 10000;
	  int CHANNEL_TIMEOUT = 10000;
	  repLog.clear();
	  
		 String remoteShellScript = "/home/cris/sh/KMTS_REP_MON_2016_jmsdb";
		 
		 Session jschSession = null;
		 
		 try {
		 
		 JSch jsch = new JSch();
		 
		 
		 
		 jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
		 jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		 
		 // not recommend, uses jsch.setKnownHosts
		 jschSession.setConfig("StrictHostKeyChecking", "no");
		 
		 // authenticate using password 
		 jschSession.setPassword(PASSWORD);
		 
		 // 10 seconds timeout session 
		 jschSession.connect(SESSION_TIMEOUT);
		 
		 ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		 
		 // run a shell script 
		 channelExec.setCommand("sh " + remoteShellScript +" "+d+" "+ m +" "+ y + " " +loc);
		 
		 // display errors to System.err 
		 channelExec.setErrStream(System.err);
		 
		 InputStream in = channelExec.getInputStream();
		 
		 // 5 seconds timeout channel 
		 channelExec.connect(CHANNEL_TIMEOUT);
		 
		 // read the result from remote server 
		 byte[] tmp = new byte[1024];
		 while(true) 
		 { 
			  while (in.available() > 0) 
			  { 
				  int i = in.read(tmp, 0, 1024); 
				  if(i <0) break; 
				  System.out.print(new String(tmp, 0, i)); 
				  repLog.add(new String(tmp, 0, i));
			  }
		 if(channelExec.isClosed()) 
		 { 
			  if (in.available() > 0) 
				  continue;
			  System.out.println("exit-status: " + channelExec.getExitStatus());
			  exit=channelExec.getExitStatus(); 
			  break; 
		 } 
		 try { 
			  Thread.sleep(1000); 
			  } 
		 catch(Exception ee) { } 
		 
		 }
		 
		 	channelExec.disconnect();
		 
		 } catch (JSchException | IOException e) {
		 
			  e.printStackTrace();
		 
		 } 
		 finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
			 }
		 
		 	return repLog; 
 }
 
 
		  public int runReportScriptsToDb() {
			  
			  
			  String REMOTE_HOST = "10.217.1.132";
			  String USERNAME = "cris";
			  String PASSWORD = "cris123";
			  int REMOTE_PORT = 22;
			  int SESSION_TIMEOUT = 10000;
			  int CHANNEL_TIMEOUT = 10000;
			  
			  
			  String remoteShellScript = "/home/cris/sh/ManojBose/callReps";
			  
			  Session jschSession = null;
			  
			  try {
			  
			  JSch jsch = new JSch();
			  
			  
			  
			  jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
			  jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
			  
			  // not recommend, uses jsch.setKnownHosts
			  jschSession.setConfig("StrictHostKeyChecking", "no");
			  
			  // authenticate using password 
			  jschSession.setPassword(PASSWORD);
			  
			  // 10 seconds timeout session 
			  jschSession.connect(SESSION_TIMEOUT);
			  
			  ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
			  
			  // run a shell script 
			  channelExec.setCommand("sh " + remoteShellScript);
			  
			  // display errors to System.err 
			  channelExec.setErrStream(System.err);
			  
			  InputStream in = channelExec.getInputStream();
			  
			  // 5 seconds timeout channel 
			  channelExec.connect(CHANNEL_TIMEOUT);
			  
			  // read the result from remote server 
			  byte[] tmp = new byte[1024];
			  while(true) 
			  { 
				  while (in.available() > 0) 
				  { 
					  int i = in.read(tmp, 0, 1024); 
					  if(i <0) break; 
					  //System.out.print(new String(tmp, 0, i)); 
				  }
			  if(channelExec.isClosed()) 
			  { 
				  if (in.available() > 0) 
					  continue;
			  //System.out.println("exit-status: " + channelExec.getExitStatus());
			  exit=channelExec.getExitStatus(); 
			  break; 
			  } 
			  try { 
				  Thread.sleep(1000); 
				  } 
			  catch(Exception ee) { } }
			  
			  channelExec.disconnect();
			  
			  } catch (JSchException | IOException e) {
			  
			  e.printStackTrace();
			  
			  } 
			  finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
				 }
			  
			  return exit; }
		  
		  
		  
		  public int getWindowsSpace() throws SftpException
		  {
			  String USERNAME="cris";
			  String PASSWORD="1234";
			  String REMOTE_HOST="10.217.3.55";
			  int REMOTE_PORT = 22;
			  int SESSION_TIMEOUT = 10000;
			  int CHANNEL_TIMEOUT = 10000;
				
			  //String remoteShellScript = "/home/cris/sh/daily_rep_manual";
			  
			  Session jschSession = null;
			  
			  try {
			  
				  
			  JSch jsch = new JSch();
			  
			  //jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
			  jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
			  
			  // not recommend, uses jsch.setKnownHosts
			  jschSession.setConfig("StrictHostKeyChecking", "no");
			  
			  // authenticate using password 
			  jschSession.setPassword(PASSWORD);
			  
			  // 10 seconds timeout session 
			  jschSession.connect(SESSION_TIMEOUT);
			  
			  
			  Channel channel = jschSession.openChannel("sftp");
	            channel.connect();
	            ChannelSftp sftpChannel = (ChannelSftp) channel;

	            InputStream stream = sftpChannel.get("F:/ManojBose/WindowsDetails/winlog.txt");
	            try {
	                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
	                String line;
	                while ((line = br.readLine()) != null) {
	                    System.out.println(line);
	                }

	            } catch (IOException io) {
	                System.out.println("Exception occurred during reading file from SFTP server due to " + io.getMessage());
	                io.getMessage();

	            } catch (Exception e) {
	                System.out.println("Exception occurred during reading file from SFTP server due to " + e.getMessage());
	                e.getMessage();

	            }

	            sftpChannel.exit();
	            jschSession.disconnect();
			  
			  try { 
				  Thread.sleep(1000); 
				  } 
			  catch(Exception ee) { } 
			  
			  sftpChannel.disconnect();
			  
			  } catch (JSchException e) {
			  
				  e.printStackTrace();
			  
			  } 
			  finally { 
				  if (jschSession != null) 
				  { 
					  jschSession.disconnect(); 
				  } 
				 }
			  
			  return exit;
		  }
	  
		/*
		 * public ArrayList<REP_LINES> dispalyDailyRep() { ArrayList<REP_LINES>
		 * mydata=new ArrayList<REP_LINES>(); REP_LINES line; String remoteShellScript =
		 * "/repbackup/REPORTS/2021/DEC2021/Daily_shift_detail13122021.KBEL";
		 * 
		 * Session jschSession = null;
		 * 
		 * try {
		 * 
		 * JSch jsch = new JSch();
		 * 
		 * 
		 * 
		 * jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); jschSession =
		 * jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		 * 
		 * // not recommend, uses jsch.setKnownHosts
		 * jschSession.setConfig("StrictHostKeyChecking", "no");
		 * 
		 * // authenticate using password jschSession.setPassword(PASSWORD);
		 * 
		 * // 10 seconds timeout session jschSession.connect(SESSION_TIMEOUT);
		 * 
		 * ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		 * 
		 * // run a shell script channelExec.setCommand("cat "+ remoteShellScript);
		 * 
		 * // display errors to System.err channelExec.setErrStream(System.err);
		 * 
		 * InputStream in = channelExec.getInputStream();
		 * 
		 * // 5 seconds timeout channel channelExec.connect(CHANNEL_TIMEOUT);
		 * 
		 * // read the result from remote server byte[] tmp = new byte[1024]; while
		 * (true) { while (in.available() > 0) { int i = in.read(tmp, 0, 1024); if (i <
		 * 0) break; //System.out.print(new String(tmp, 0, i)); line=new REP_LINES(new
		 * String(tmp, 0, i)); mydata.add(line); } if (channelExec.isClosed()) { if
		 * (in.available() > 0) continue; //System.out.println("exit-status: " +
		 * channelExec.getExitStatus()); exit=channelExec.getExitStatus(); break; } try
		 * { Thread.sleep(1000); } catch (Exception ee) { } }
		 * 
		 * channelExec.disconnect();
		 * 
		 * } catch (JSchException | IOException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * } finally { if (jschSession != null) { jschSession.disconnect(); } }
		 * 
		 * return mydata; }
		 */
	  
		/*
		 * public ArrayList<REP_LINES> runSpaceDetailsRepbackup() {
		 * 
		 * ArrayList<REP_LINES> mydata=new ArrayList<REP_LINES>(); REP_LINES line;
		 * //String remoteShellScript = "/home/cris/sh/daily_rep_manual"; String
		 * myCommand="df -h /repbackup"; Session jschSession = null;
		 * 
		 * try {
		 * 
		 * JSch jsch = new JSch();
		 * 
		 * 
		 * 
		 * jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); jschSession =
		 * jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		 * 
		 * // not recommend, uses jsch.setKnownHosts
		 * jschSession.setConfig("StrictHostKeyChecking", "no");
		 * 
		 * // authenticate using password jschSession.setPassword(PASSWORD);
		 * 
		 * // 10 seconds timeout session jschSession.connect(SESSION_TIMEOUT);
		 * 
		 * ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		 * 
		 * // run a shell script channelExec.setCommand(myCommand);
		 * 
		 * // display errors to System.err channelExec.setErrStream(System.err);
		 * 
		 * InputStream in = channelExec.getInputStream();
		 * 
		 * // 5 seconds timeout channel channelExec.connect(CHANNEL_TIMEOUT); // read
		 * the result from remote server byte[] tmp = new byte[1024]; while (true) {
		 * while (in.available() > 0) { int i = in.read(tmp, 0, 1024); if (i < 0) break;
		 * line=new REP_LINES(new String(tmp, 0, i)); //System.out.println(count);
		 * mydata.add(line); } if (channelExec.isClosed()) { if (in.available() > 0)
		 * continue; System.out.println("exit-status: " + channelExec.getExitStatus());
		 * exit=channelExec.getExitStatus(); break; } try { Thread.sleep(1000); } catch
		 * (Exception ee) { } }
		 * 
		 * channelExec.disconnect();
		 * 
		 * } catch (JSchException | IOException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * } finally { if (jschSession != null) { jschSession.disconnect(); } }
		 * 
		 * return mydata; }
		 */
	  
		/*
		 * public ArrayList<REP_LINES> runSpaceDetails131() {
		 * 
		 * String REMOTE_HOST131 = "10.217.1.131"; String USERNAME131 = "sybasa"; String
		 * PASSWORD131 = "sybasa"; int REMOTE_PORT131 = 22; int SESSION_TIMEOUT131 =
		 * 10000; int CHANNEL_TIMEOUT131 = 5000;
		 * 
		 * ArrayList<REP_LINES> mydata=new ArrayList<REP_LINES>(); REP_LINES line;
		 * //String remoteShellScript = "/home/cris/sh/daily_rep_manual"; String
		 * myCommand="df -h"; Session jschSession = null;
		 * 
		 * try {
		 * 
		 * JSch jsch = new JSch();
		 * 
		 * 
		 * 
		 * jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); jschSession =
		 * jsch.getSession(USERNAME131, REMOTE_HOST131, REMOTE_PORT131);
		 * 
		 * // not recommend, uses jsch.setKnownHosts
		 * jschSession.setConfig("StrictHostKeyChecking", "no");
		 * 
		 * // authenticate using password jschSession.setPassword(PASSWORD131);
		 * 
		 * // 10 seconds timeout session jschSession.connect(SESSION_TIMEOUT131);
		 * 
		 * ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		 * 
		 * // run a shell script channelExec.setCommand(myCommand);
		 * 
		 * // display errors to System.err channelExec.setErrStream(System.err);
		 * 
		 * InputStream in = channelExec.getInputStream();
		 * 
		 * // 5 seconds timeout channel channelExec.connect(CHANNEL_TIMEOUT131); // read
		 * the result from remote server byte[] tmp = new byte[1024]; while (true) {
		 * while (in.available() > 0) { int i = in.read(tmp, 0, 1024); if (i < 0) break;
		 * line=new REP_LINES(new String(tmp, 0, i)); //System.out.println(count);
		 * mydata.add(line); } if (channelExec.isClosed()) { if (in.available() > 0)
		 * continue; System.out.println("exit-status: " + channelExec.getExitStatus());
		 * exit=channelExec.getExitStatus(); break; } try { Thread.sleep(1000); } catch
		 * (Exception ee) { } }
		 * 
		 * channelExec.disconnect();
		 * 
		 * } catch (JSchException | IOException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * } finally { if (jschSession != null) { jschSession.disconnect(); } }
		 * 
		 * return mydata; }
		 */
	  
	  
		/*
		 * public ArrayList<REP_LINES> runSpaceDetails101() {
		 * 
		 * String REMOTE_HOST101 = "10.14.50.101"; String USERNAME101 = "scs"; String
		 * PASSWORD101 = "scs"; int REMOTE_PORT101 = 22; int SESSION_TIMEOUT101 = 10000;
		 * int CHANNEL_TIMEOUT101 = 5000;
		 * 
		 * ArrayList<REP_LINES> mydata=new ArrayList<REP_LINES>(); REP_LINES line;
		 * //String remoteShellScript = "/home/cris/sh/daily_rep_manual"; String
		 * myCommand="df -h"; Session jschSession = null;
		 * 
		 * try {
		 * 
		 * JSch jsch = new JSch();
		 * 
		 * 
		 * 
		 * jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); jschSession =
		 * jsch.getSession(USERNAME101, REMOTE_HOST101, REMOTE_PORT101);
		 * 
		 * // not recommend, uses jsch.setKnownHosts
		 * jschSession.setConfig("StrictHostKeyChecking", "no");
		 * 
		 * // authenticate using password jschSession.setPassword(PASSWORD101);
		 * 
		 * // 10 seconds timeout session jschSession.connect(SESSION_TIMEOUT101);
		 * 
		 * ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		 * 
		 * // run a shell script channelExec.setCommand(myCommand);
		 * 
		 * // display errors to System.err channelExec.setErrStream(System.err);
		 * 
		 * InputStream in = channelExec.getInputStream();
		 * 
		 * // 5 seconds timeout channel channelExec.connect(CHANNEL_TIMEOUT101); // read
		 * the result from remote server byte[] tmp = new byte[1024]; while (true) {
		 * while (in.available() > 0) { int i = in.read(tmp, 0, 1024); if (i < 0) break;
		 * line=new REP_LINES(new String(tmp, 0, i)); //System.out.println(count);
		 * mydata.add(line); } if (channelExec.isClosed()) { if (in.available() > 0)
		 * continue; System.out.println("exit-status: " + channelExec.getExitStatus());
		 * exit=channelExec.getExitStatus(); break; } try { Thread.sleep(1000); } catch
		 * (Exception ee) { } }
		 * 
		 * channelExec.disconnect();
		 * 
		 * } catch (JSchException | IOException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * } finally { if (jschSession != null) { jschSession.disconnect(); } }
		 * 
		 * return mydata; }
		 */
	  
	  
		/*
		 * public ArrayList<REP_LINES> checkReport(String d,String m,String y,String
		 * flag) { ArrayList<REP_LINES> resp=new ArrayList<REP_LINES>(); REP_LINES line;
		 * 
		 * String remoteShellScript=null; if(flag.equals("D")) { remoteShellScript =
		 * "/home/cris/sh/scanDAILY"; } else if(flag.equals("P")) { remoteShellScript =
		 * "/home/cris/sh/scanPERIODIC"; } else if(flag.equals("M")) { remoteShellScript
		 * = "/home/cris/sh/scanMONTHLY"; } Session jschSession = null;
		 * 
		 * try {
		 * 
		 * JSch jsch = new JSch();
		 * 
		 * jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); jschSession =
		 * jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
		 * 
		 * // not recommend, uses jsch.setKnownHosts
		 * jschSession.setConfig("StrictHostKeyChecking", "no");
		 * 
		 * // authenticate using password jschSession.setPassword(PASSWORD);
		 * 
		 * // 10 seconds timeout session jschSession.connect(SESSION_TIMEOUT);
		 * 
		 * ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
		 * if(flag.equals("D")) { // run a shell script channelExec.setCommand("sh " +
		 * remoteShellScript + " " +d+" "+ m +" "+ y); } else if(flag.equals("P") ||
		 * flag.equals("M")) { // run a shell script channelExec.setCommand("sh " +
		 * remoteShellScript + " " + m +" "+ y); } // display errors to System.err
		 * channelExec.setErrStream(System.err);
		 * 
		 * InputStream in = channelExec.getInputStream();
		 * 
		 * // 5 seconds timeout channel channelExec.connect(CHANNEL_TIMEOUT);
		 * 
		 * // read the result from remote server byte[] tmp = new byte[3072]; while
		 * (true) { while (in.available() > 0) { int i = in.read(tmp, 0, 3072); if (i <
		 * 0) break; line=new REP_LINES(new String(tmp, 0, i));
		 * //System.out.println(count); resp.add(line); } if (channelExec.isClosed()) {
		 * if (in.available() > 0) continue; System.out.println("exit-status: " +
		 * channelExec.getExitStatus()); exit=channelExec.getExitStatus(); break; } try
		 * { Thread.sleep(1000); } catch (Exception ee) { } }
		 * 
		 * channelExec.disconnect();
		 * 
		 * } catch (JSchException | IOException e) {
		 * 
		 * e.printStackTrace();
		 * 
		 * } finally { if (jschSession != null) { jschSession.disconnect(); } }
		 * 
		 * return resp; }
		 */
	
		  public List<NETWORK_STATUS> get_all_network(){
			  
			  
			  List<NETWORK_STATUS> ns=new ArrayList<NETWORK_STATUS>();
			  NETWORK_STATUS net;
			 try {
			  
			  con = connectHCP_ASA.getConnection();
			  stmt=con.createStatement();
			  rs=stmt.executeQuery("select terminal,location,device,ip,status,stat_time from network_ip"); 
			  while(rs.next()) {
			  
			  net=new NETWORK_STATUS(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
			  ns.add(net);
			  } 
			  con.close(); 
			  }
			  catch(SQLException ex) { ex.printStackTrace(); } catch(Exception e) {
			  e.printStackTrace(); }
			  
			  	  
			  return ns;
			  
			  }
		  
		  
		  	public List<NETWORK_STATUS> get_all_network_not_in_link(){
			  
			  
			  List<NETWORK_STATUS> nl=new ArrayList<NETWORK_STATUS>();
			  NETWORK_STATUS net;
			  try {
			  
			  con = connectHCP_ASA.getConnection();
			  stmt=con.createStatement();
			  rs=stmt.executeQuery("select terminal,location,device,ip,status,stat_time from network_ip where status <> 0"); 
			  while(rs.next()) {
			  
			  net=new NETWORK_STATUS(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
			  nl.add(net);
			  } 
			  con.close(); 
			  }
			  catch(SQLException ex) { ex.printStackTrace(); } catch(Exception e) {
			  e.printStackTrace(); }
			  
			  	  
			  return nl;
			  
			  }

			  public ArrayList<REPLICATION> dispalyReplicationDown() { 
				  
				  ArrayList<REPLICATION> rep=new ArrayList<REPLICATION>();
				  REPLICATION rObject;
				 try {
				  
				  con = connectHCP_ASA.getConnection();
				  stmt=con.createStatement();
				  rs=stmt.executeQuery("select * from replica_status where rep_name='RS1_DOWN' or rep_name='RS2_DOWN'"); 
				  while(rs.next()) {
				  
					  rObject=new REPLICATION(rs.getString(1),rs.getString(2),rs.getString(3));
					  //System.out.println(rs.getString(2).toString());
					  rep.add(rObject);
				  } 
				  con.close(); 
				  }
				  catch(SQLException ex) { 
					  ex.printStackTrace(); 
					  } 
				 catch(Exception e) {
				  e.printStackTrace(); 
				  }
				  
				  return rep;
			  
			  }
			  
			  public ArrayList<REPLICATION> dispalyReplicationUp() { 
				  
				  ArrayList<REPLICATION> rep=new ArrayList<REPLICATION>();
				  REPLICATION rObject;
				 try {
				  
				  con = connectHCP_ASA.getConnection();
				  stmt=con.createStatement();
				  rs=stmt.executeQuery("select * from replica_status where rep_name='RS1_UP' or rep_name='RS2_UP'"); 
				  while(rs.next()) {
				  
					  rObject=new REPLICATION(rs.getString(1),rs.getString(2),rs.getString(3));
					  //System.out.println(rs.getString(2).toString());
					  rep.add(rObject);
				  } 
				  con.close(); 
				  }
				  catch(SQLException ex) { 
					  ex.printStackTrace(); 
					  } 
				 catch(Exception e) {
				  e.printStackTrace(); 
				  }
				  
				  return rep;
			  
			  }
			  
			  
			  public ArrayList<LAST_COMMIT> dispalyReplicaLastCommit() { 
				  
				  ArrayList<LAST_COMMIT> rep=new ArrayList<LAST_COMMIT>();
				  LAST_COMMIT rObject;
				 try {
				  
				  con = connectHCP_ASE.getConnection();
				  stmt=con.createStatement();
				  rs=stmt.executeQuery("select top 1 origin,origin_time,dest_commit_time from rs_lastcommit order by origin desc"); 
				  while(rs.next()) {
				  
					  rObject=new LAST_COMMIT(rs.getString(1),rs.getString(2),rs.getString(3));
					  //System.out.println(rs.getString(2).toString());
					  rep.add(rObject);
				  } 
				  con.close(); 
				  }
				  catch(SQLException ex) { 
					  ex.printStackTrace(); 
					  } 
				 catch(Exception e) {
				  e.printStackTrace(); 
				  }
				  
				  return rep;
			  
			  }
			  
			  
			  public ArrayList<DATABASES> dispalyDatabases() { 
				  
				  ArrayList<DATABASES> deb=new ArrayList<DATABASES>();
				  DATABASES dObject;
				 try {
				  
				  con = connectHCP_ASA.getConnection();
				  stmt=con.createStatement();
				  rs=stmt.executeQuery("select * from central_db"); 
				  while(rs.next()) {
				  
					  dObject=new DATABASES(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
					  //System.out.println(rs.getString(2).toString());
					  deb.add(dObject);
				  } 
				  con.close(); 
				  
				  }
				  catch(SQLException ex) { 
					  ex.printStackTrace(); 
					  } 
				 catch(Exception e) {
				  e.printStackTrace(); 
				  }
				  
				  return deb;
			  
			  }
			  
			  
			  
			  
			 	  
			  public ArrayList<String> syncTerminal(String t) {
				  
				  String REMOTE_HOST = "10.217.1.133";
				  String USERNAME = "sybasa";
				  String PASSWORD = "sybasa";
				  int REMOTE_PORT = 22;
				  int SESSION_TIMEOUT = 10000;
				  int CHANNEL_TIMEOUT = 10000;
				  ArrayList<String> lines=new ArrayList<String>();
				  
					 String remoteShellScript = "/home1/sybasa/manoj/syncTerminal.sh";
					 
					 Session jschSession = null;
					 
					 try {
					 
					 JSch jsch = new JSch();
					 					 
					 //jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); 
					 jschSession =jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
					 
					 // not recommend, uses jsch.setKnownHosts
					 jschSession.setConfig("StrictHostKeyChecking", "no");
					 
					 // authenticate using password 
					 jschSession.setPassword(PASSWORD);
					 
					 // 10 seconds timeout session 
					 jschSession.connect(SESSION_TIMEOUT);
					 
					 ChannelExec channelExec = (ChannelExec) jschSession.openChannel("exec");
					 
					 // run a shell script 
					 channelExec.setCommand("sh " + remoteShellScript +" "+t);
					 
					 // display errors to System.err 
					 channelExec.setErrStream(System.err);
					 
					 InputStream in = channelExec.getInputStream();
					 
					 // 5 seconds timeout channel 
					 channelExec.connect(CHANNEL_TIMEOUT);
					 
					 // read the result from remote server 
					 byte[] tmp = new byte[1024];
					 while(true) 
					 { 
						  while (in.available() > 0) 
						  { 
							  int i = in.read(tmp, 0, 1024); 
							  if(i <0) break; 
							  //System.out.print(new String(tmp, 0, i));
							  lines.add(new String(tmp, 0, i));
						  }
					 if(channelExec.isClosed()) 
					 { 
						  if (in.available() > 0) 
							  continue;
						  System.out.println(t+" exit-status: " + channelExec.getExitStatus());
						  exit=channelExec.getExitStatus(); 
						  break; 
					 } 
					 try { 
						  Thread.sleep(1000); 
						  } 
					 catch(Exception ee) { } 
					 
					 }
					 
					 	channelExec.disconnect();
					 
					 } catch (JSchException | IOException e) {
					 
						  e.printStackTrace();
					 
					 } 
					 finally { 
							  if (jschSession != null) 
							  { 
								  jschSession.disconnect(); 
							  } 
						 }
					 
					 	return lines; 
			 }
		  
		  
		  
				/*
				 * public String copyFile() throws JSchException, SftpException {
				 * 
				 * final String REMOTE_HOST5 = "10.217.3.55"; final String USERNAME5 = "cris";
				 * final String PASSWORD5 = "1234"; final int REMOTE_PORT5 = 22; final int
				 * SESSION_TIMEOUT5 = 10000;
				 * 
				 * Session jschSession = null;
				 * 
				 * 
				 * JSch jsch = new JSch();
				 * 
				 * 
				 * //jsch.setKnownHosts("/home/cris/.ssh/known_hosts"); jschSession
				 * =jsch.getSession(USERNAME5, REMOTE_HOST5, REMOTE_PORT5);
				 * 
				 * // not recommend, uses jsch.setKnownHosts
				 * jschSession.setConfig("StrictHostKeyChecking", "no"); // authenticate using
				 * password jschSession.setPassword(PASSWORD5);
				 * 
				 * // 10 seconds timeout session jschSession.connect(SESSION_TIMEOUT5);
				 * 
				 * ChannelSftp sftpChannel = (ChannelSftp) jschSession.openChannel("sftp");
				 * sftpChannel.connect();
				 * 
				 * sftpChannel.put(
				 * "F:/ManojBose/WindowsDetails/winlog.txt","C:/Users/Manoj Bose/Documents/SystemReport/temp"
				 * ); System.out.println("File copied"); //To upload a file //sftpChannel.get(
				 * "/repbackup/REPORTS/2021/DEC2021/Daily_shift_detail13122021.KBEL", "./rep");
				 * 
				 * return "OK";
				 * 
				 * }
				 */
	 
	
}
