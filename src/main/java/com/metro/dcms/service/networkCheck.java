package com.metro.dcms.service;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.metro.dcms.connection.connectHCP_ASA;


@Service
public class networkCheck {

	InetAddress address;
	boolean reachable;
	String dt;
	Connection con;
	Statement stmt;
    String qry;	
	@Autowired
	ReportService rService;
	
	@Async
	public void networkCheckFunc(String ip,String term)
	  {	
		
		try {
			
				address = InetAddress.getByName(ip);
	            reachable = address.isReachable(10000);
				dt=rService.timeStamp();
			
					  con = connectHCP_ASA.getConnection();
					  stmt=con.createStatement();
					  System.out.println("update network_ip set status='"+reachable+"',stat_time='"+dt+"' where terminal='"+ip+"'");
					  //qry="update network_ip set status='"+reachable+"',stat_time='"+dt+"' where terminal='"+ip+"'";
					  stmt.executeQuery("update network_ip set status='"+reachable+"',stat_time='"+dt+"' where terminal='"+ip+"'");
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
		 }
}