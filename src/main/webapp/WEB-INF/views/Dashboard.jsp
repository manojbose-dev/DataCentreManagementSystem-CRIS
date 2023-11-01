<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DATA CENTRE SYSTEM CHECK</title>

	  	<meta name="viewport" content="width=device-width, initial-scale=1">

	 	<link rel="stylesheet" href="${baseURL}/fontawesome470/css/font-awesome.min.css">
	    <link rel="stylesheet" href="${baseURL}/css/jquery-ui.css" type="text/css" media="all" /> 
		<script src="${baseURL}/js/jquery-ui.min.js"></script>
		 <link rel="stylesheet" href="${baseURL}/bootstrap400/css/bootstrap.min.css">
		 <script src="${baseURL}/bootstrap400/js/bootstrap.min.js"></script>
		 <script src="${baseURL}/js/jquery-3.6.0.min.js"></script>
		 <!-- <script src="/js/popper.min.js"></script> -->
    	<link rel="stylesheet" href="${baseURL}/css/style1.css" /> 
      	<script src="${baseURL}/js/chart.js"></script>
      	<script src="${baseURL}/js/CustomAlert.js"></script>   

</head>
<script>

$(document).ready(
		$( function() {
			
			setTimeout(function() {
				location.reload();
             }, 300000);
			
			$('#das').click(function() {
                $('html,body').animate({
                    scrollTop: $('#dashboard-sec').offset().top},
                    'slow');
            });
			
			$('#rep').click(function() {
                $('html,body').animate({
                    scrollTop: $('#report-sec').offset().top},
                    'slow');
            });
            
            $('#net').click(function() {
                $('html,body').animate({
                    scrollTop: $('#network-sec').offset().top},
                    'slow');
            });  
            
            $('#replica').click(function() {
                $('html,body').animate({
                    scrollTop: $('#replication-sec').offset().top},
                    'slow');
            });  
            
            $('#data').click(function() {
                $('html,body').animate({
                    scrollTop: $('#database-sec').offset().top},
                    'slow');
            });  
            
            $('#kmts').click(function() {
                $('html,body').animate({
                    scrollTop: $('#kmts-sec').offset().top},
                    'slow');
            });
            
            $('#mob').click(function() {
                $('html,body').animate({
                    scrollTop: $('#mobilink-sec').offset().top},
                    'slow');
            }); 
            
            
                       
        } )

);

function runDaily(dt,loc,no)
{
	//alert(dt+" AND "+loc);	
	$('#gen'+no).css("background-color","red");
	
	$.ajax({
				    url: '${baseURL}/runRep?mydate='+dt+'&myloc='+loc,
				    type: 'POST',
				    success: function(data) {
				    
						alert("Report Generated");
						location.reload();
					    
				    }
				});
						
}

function getSync(loc)
{

    $(document).ready(
		$( function() {
						$.ajax({
									    url: '${baseURL}/getSyncStatus?myloc='+loc,
									    type: 'POST',
									    success: function(data) {
									   	$.each(data, function(index, pendSync) {
             									console.log(pendSync.pubName); 
         									});  
											//alert(data);
										    
									    }
									});
		} )

	);			
	//alert(" HELLO ");
}
function displayDaily()
{
//alert("YES");
	$(document).ready(
		$( function() {
						$.ajax({
									    url: '${baseURL}/displayDailyReport',
									    type: 'POST',
									    success: function(data) {
									   	$.each(data, function(index, ln) {
             									console.log(ln.repLine); 
         									});  
											//alert(data);
										    
									    }
									});
		} )

	);	
}

     

</script>

<style>
ul li a{color:#fff; text-decoration:none;}
.fa-file-pdf-o{color:red;}
</style>
<body>
      
<div class="container">

<c:import url="/WEB-INF/views/header.jsp" />
<div id="content-sec">

  <div class="container">
   <div id="accordion">
  
  				<div class="card" id="dashboard-sec">
						<div class="card-header">
							<a class="collapsed card-link" data-toggle="collapse" href="#collapse1"> <i class="fa fa-line-chart" aria-hidden="true"></i> Problem Statistics
							</a>
						</div>
						<div id="collapse1" class="collapse show"
							data-parent="#accordion">
							<div class="card-body">
								<div id="chart1" style="width: 48%; float: left; margin: 1%;">
									<canvas id="myChart1" style="width: 100%;"></canvas>
								</div>
								<div id="chart2" style="width: 48%; float: left; margin: 1%;">
									<canvas id="myChart2" style="width: 100%;"></canvas>
								</div>

								<!-- <div id="piechart_repbackup2" style="height:200px; width:200px;"></div> -->


							</div>
						</div>
					</div>
  
    <div class="card" id="report-sec">
      <div class="card-header">
      <div id="left-part">
        <a class="card-link" data-toggle="collapse" href="#collapse2" target="_blank">
         <i class="fa fa-file-text" aria-hidden="true"></i> Report & Synchronization Status
        </a>
        </div>
        <div id="right-part">
        <a href="${baseURL}/Sync" target="_blank"><button class="btn btn-success">Sync POST</button></a>
        <a href="${baseURL}/Report" target="_blank"><button class="btn btn-success">Generate Report</button></a>
        </div>
        
      </div>
      <div id="collapse2" class="collapse show" data-parent="#accordion">
        <div class="card-body">
        
        <div id="head-row-align">
        <div id="head-item1">
        <div id="head-item1_1">
        <i class="fa fa-exclamation-triangle" aria-hidden="true"></i>Sync Pending <span class="badge badge-danger ml-2">${POST_NUMBER}</span>
        <br>
        <br>
        <span style="font-size:0.6em; color:#f57418;">Please check synchronization pending list.</span>
        <br>
        <br>
        <a href="/SyncList" onclick="window.open(this.href,'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1000,height=800,left=450,top=100'); return false;">
        <button type="button" class="btn btn-info" style="font-size:1.2vw;">
  		Show List		
		</button>
		</a> 
        </div>
        <%-- <img src="${baseURL}/images/sync.png" style="height:150px;line-height:150px;" /> --%>         
		</div>
		
		<div id="head-item2">
		<div id="head-item2_1">
		<i class="fa fa-exclamation-circle" aria-hidden="true"></i>Report Pending <span class="badge badge-danger ml-2">${REP_NUMBER}</span>
		<br>
        <br>
        <span style="font-size:0.6em; color:#f57418;">Please check report pending list.</span>
        <br>
        <br>
		<a href="/ReportList" onclick="window.open(this.href,'targetWindow','toolbar=no,location=no,status=no,menubar=no,location=no,scrollbars=yes,resizable=no,width=1000,height=800,left=450,top=100'); return false;">
		<button type="button" class="btn btn-info" style="font-size:1.2vw;">
  		Show List		
		</button>
		</a>
		</div>
        </div>
		</div>
			    
        </div>
      </div>
    </div>
    
    				<div class="card" id="network-sec">
						<div class="card-header">
						<div id="left-part">
							<a class="card-link" data-toggle="collapse" href="#collapse3">
								<i class="fa fa-exchange" aria-hidden="true"></i> Network
								<span id="error">(${NET_NUMBER} Device, out of link)</span>
							</a>
						</div>
						<div id="right-part">
						<a href="${baseURL}/Network" target="_blank"><button class="btn btn-primary"><i class="fa fa-external-link" aria-hidden="true"></i> View Full Network</button></a>
						</div>	
						</div>
						<div id="collapse3" class="collapse show" data-parent="#accordion">
							<div class="card-body">
						
						<c:choose>
						<c:when test="${network ne '0'}">
								<table style="width: 95%; margin: 2%; padding: 5%;"
									class="table table-hover" id="net-table" align="center">

									<tr class="table-danger">
										<th><b>IP</b></th>
										<th><b>LOCATION</b></th>
										<th><b>DEVICE</b></th>
										<th><b>TERMINAL</b></th>
										<th><b>LAST UPDATE</b></th>
										<th><b>LINK</b></th>
									</tr>
									<c:forEach begin="0" end="4" var="ns" items="${NET_STATUS}">	
									<tr>
										<td>${ns.ip}</td>
										<td>${ns.location}</td>
										<td>${ns.device}</td>
										<td>${ns.terminal}</td>								
										<td>${ns.update_time}</td>
										<td>
										<div id="net_color"></div>
										</td>
									</tr>
									</c:forEach>
									<tr><td colspan="6"><a href="/NetworkList" onclick="window.open(this.href,'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1000,height=800,left=450,top=100'); return false;" style="color:red;">
									Show More
									</a></td></tr>
								</table>
							</c:when>
						</c:choose>	
							
							</div>
						</div>
					</div>

					<div class="card" id="replication-sec">
						<div class="card-header">
						<div id="left-part">
							<a class="collapsed card-link" data-toggle="collapse" href="#collapse4"> <i class="fa fa-refresh" id="rotating" style="display: inline-block;" aria-hidden="true"></i>
								Replication<span id="error"></span>
							</a>
						</div>
						<div id="right-part">
						<a href="${baseURL}/ReplicLastCommit" onclick="window.open(this.href,'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1000,height=800,left=400,top=100'); return false;"><button class="btn btn-primary"><i class="fa fa-external-link" aria-hidden="true"></i> Last Commit</button></a>
						</div>	
						
						</div>
						<div id="collapse4" class="collapse show"
							data-parent="#accordion">
							<div class="card-body">
							
							<div id="head-row-align">
					        <div id="head-item1">
					        <div id="head-item10_1">
					        <i class="fa fa-level-up" aria-hidden="true"></i> Thread Up <span class="badge badge-danger ml-2">${rep_list_up_size}</span>
					        <br>
					        <br>
					       
					       	<c:choose>
							<c:when test="${rep_list_up_size gt 0}">
							<table style="width:95%; margin:2%; padding:5%;" class="table table-hover" id="replication-table" align="center">
						
							<c:forEach begin="0" end="4" var="rlu" items="${rep_up_list}">					
							<tr>
							<td style="text-align:left;color:#000; font-size:1em;">${rlu.rep_details}</td><td style="text-align:right;"><span style="background-color:#2f6e36; color:#fff; border-radius:50px; padding:5px; font-size:0.8em;">${rlu.rep_name}</span></td>
							</tr>
							</c:forEach>
							<tr><td colspan="6"><a href="/ThreadUpList" onclick="window.open(this.href,'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1000,height=800,left=450,top=100'); return false;" style="color:red;">
									Show More
							</a></td></tr>
							</table>
							</c:when>		
							</c:choose>
					       
					       
					       
					        </div>
					        </div>
					        <div id="head-item2">
					        <div id="head-item20_1">
					        <i class="fa fa-level-down" aria-hidden="true"></i> Thread Down <span class="badge badge-danger ml-2">${rep_list_down_size}</span>
					        <br>
					        <br>
					        
					        
					        <c:choose>
							<c:when test="${rep_list_down_size gt 0}">
							<table style="width:95%; margin:2%; padding:5%;" class="table table-hover" id="replication-table" align="center">
						
							<c:forEach begin="0" end="4" var="rld" items="${rep_down_list}">					
							<tr>
							<td style="text-align:left;color:#b82c2c; font-size:1.5em;">${rld.rep_details}</td><td style="text-align:right;"><span style="background-color:red; color:#fff; border-radius:50px; padding:5px; font-size:0.8em;">${rld.rep_name}</span></td>
							</tr>
							</c:forEach>
							<tr><td colspan="6"><a href="/ThreadDownList" onclick="window.open(this.href,'targetWindow','toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=1000,height=800,left=450,top=100'); return false;" style="color:red;">
									Show More
							</a></td></tr>
							</table>
							</c:when>
							<c:otherwise>
							<table style="width:95%; margin:2%; padding:5%;" class="table table-hover" id="replication-table" align="center">											
							<tr><td colspan="6"><span style="color:red; font-size:1.3em; color:#ffffff;">WoW!!! No Down</span></td>
							</tr>
							</table>
							</c:otherwise>		
							</c:choose>
					        
					
		        					        
					        </div>
					        </div>
							</div>
							
							</div>
						</div>
					</div>

					<div class="card" id="database-sec">
						<div class="card-header">
							<a class="collapsed card-link" data-toggle="collapse"
								href="#collapse5"> <i class="fa fa-database"
								aria-hidden="true"></i> Database
							</a>
						</div>
						<div id="collapse5" class="collapse show"
							data-parent="#accordion">
							<div class="card-body">
								<!-- <button type="button" class="btn btn-info">
  									Notifications <span class="badge badge-danger ml-2">4</span>
								</button> -->
								<div id="left-part1">
								<table class="table table-hover" id="deb-table" align="center">
								<tr><th>DB Name</th><th>Reserved</th><th>Data</th><th>Index</th><th>Unused</th></tr>
								<c:forEach var="dl" items="${deb_list}">
								<tr>
								<td style="background-color:#527ca1;color:#fff;">${dl.dbName}</td><td>${dl.dbReserved}</td><td>${dl.dbData}</td><td>${dl.dbIndex}</td><td>${dl.dbUnused}</td>
								</tr>																
								</c:forEach>
								</table>
								<br>
								</div>
								<div id="right-part1">
								<canvas id="myChart3" style="width:100%; height:450px; box-shadow:0px 0px 2px #ccc; padding:5px;"></canvas>
								</div>
								
								
							</div>
						</div>
					</div>

					<div class="card" id="kmts-sec">
						<div class="card-header">
							<a class="collapsed card-link" data-toggle="collapse"
								href="#collapse6"> <i class="fa fa-server"
								aria-hidden="true"></i> KMTS
							</a>
						</div>
						<div id="collapse6" class="collapse show"
							data-parent="#accordion">
							<div class="card-body">Content Here</div>
						</div>
					</div>

					<div class="card" id="mobilink-sec">
						<div class="card-header">
							<a class="collapsed card-link" data-toggle="collapse"
								href="#collapse7"> <i class="fa fa-sitemap"
								aria-hidden="true"></i> Mobilink
							</a>
						</div>
						<div id="collapse7" class="collapse show"
							data-parent="#accordion">
							<div class="card-body">Content Here</div>
						</div>
					</div>
    
  </div>
</div>



</div>
</div>
<script>
		var xValues = [ "Report", "Network", "Database", "Server", "Mobilink" ];
		var yValues = [ 5, 7, 1, 0, 1 ];
		var barColors = [ "#b91d47", "#00aba9", "#2b5797", "#92b327", "#91298e" ];

		new Chart("myChart1", {
			type : "doughnut",
			data : {
				labels : xValues,
				datasets : [ {
					backgroundColor : barColors,
					data : yValues
				} ]
			},
			options : {
				title : {
					display : true,
					text : "Problem & Anomaly Graph"
				}
			}
		});

		var xVal = [ "Day1", "Day2", "Day3", "Day4", "Day5", "Day6", "Day7" ];
		var yVal = [ 55, 49, 44, 25, 33, 56, 45, 0 ];
		var barCol = [ "#d60606", "#299139", "#532991", "#d69406", "#06d6c8",
				"#d606b7", "#cfd606" ];

		new Chart("myChart2", {
			type : "bar",
			data : {
				labels : xVal,
				datasets : [ {
					backgroundColor : barCol,
					data : yVal
				} ]
			},
			options : {
				title : {
					display : true,
					text : "Last 7 Days"
				}
			}
		});
		
		
		
		var xValues3 = ["KMTSN", "KMTSN_tdb_1", "KMTS_RS2_RSSD", "master","model","sybpcidb","sybsystemdb","sybsystemprocs","tempdb"];
		var yValues3 = [${KMTSN}, ${KMTSN_tdb_1}, ${KMTS_RS2_RSSD}, ${master}, ${model}, ${sybpcidb}, ${sybsystemdb}, ${sybsystemprocs}, ${tempdb}];
		var barColors = [
		  "#488580",
		  "#00aba9",
		  "#2b5797",
		  "#e8c3b9",
		  "#486885",
		  "#6b4885",
		  "#85487b",
		  "#854852",
		  "#758548"
		];

		new Chart("myChart3", {
		  type: "pie",
		  data: {
		    labels: xValues3,
		    datasets: [{
		      backgroundColor: barColors,
		      data: yValues3
		    }]
		  },
		  options: {
			responsive: true,
			legend: {
		            position: "right",
		            align: "middle"
		        },
			maintainAspectRatio: false,
		    title: {
		      position: "bottom",
		      display: true,
		      text: "DB SIZE INFORMATION"
		    }
		  }
		});
	</script>

</body>

</html>