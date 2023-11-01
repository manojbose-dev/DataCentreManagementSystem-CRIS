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
	  	<script src="${baseURL}/js/jquery.js"></script>
	 	<link rel="stylesheet" href="${baseURL}/fontawesome470/css/font-awesome.min.css">
		 <link rel="stylesheet" href="${baseURL}/bootstrap400/css/bootstrap.min.css">
		 <script src="${baseURL}/bootstrap400/js/bootstrap.min.js"></script>
		 <!-- <script src="/js/popper.min.js"></script> -->
    	<link rel="stylesheet" href="${baseURL}/css/style1.css" />
      	<script src="${baseURL}/js/chart.js"></script>
      	<script src="${baseURL}/js/CustomAlert.js"></script>    

</head>
<style>
ul li a{color:#fff; text-decoration:none;}
.fa-file-pdf-o{color:red;}
</style>
<body>
      
<%-- <c:import url="${baseURL}/WEB-INF/views/header.jsp" /> --%>
<div id="content-sec">
 <div id="preloader"><div class="load"></div><span style="border:1px solid #fff;padding:5px; color:#fff; margin-top:21%; font-size:1em;">-Process running- Please wait...</span></div> 
 
  <div id="accordion">
  
    <div class="card">
      <div class="card-header">
        <a class="card-link" data-toggle="collapse" href="#collapseOne">
         <i class="fa fa-file-text" aria-hidden="true"></i> Report Pending List
        </a>
      </div>
      <div id="collapseOne" class="collapse show" data-parent="#accordion">
        <div class="card-body" style="text-align:center; padding-top:10px;">   
		<br>
		<c:choose>
						<c:when test="${rep_list_up_size ne '0'}">
								<table style="width: 95%; margin: 2%; padding: 5%;"
									class="table table-hover" id="net-table" align="center">

									<tr class="table-danger">
										<th><b>REPLICATION DETAILS</b></th>
										<th><b>THREAD NAME</b></th>
									</tr>
									<c:forEach var="rlu" items="${rep_up_list}">					
									<tr>
									<td style="text-align:left;color:#000; font-size:1em;">${rlu.rep_details}</td><td style="text-align:right;"><span style="background-color:#2f6e36; color:#fff; border-radius:50px; padding:5px; font-size:0.8em;">${rlu.rep_name}</span></td>
									</tr>
									</c:forEach>
								</table>
							</c:when>
							<c:otherwise>
							<table style="width:95%; margin:2%; padding:5%;" class="table table-hover" id="sync-table" align="center">
							<tr class="table-success">
							<th><b>${ListSize}</b></th>
							</tr>			
							</table>	
							</c:otherwise>
		 </c:choose>	
        </div>
      </div>
    </div>
    


</div>

<div id="feedback"></div>

</div>


<script>

$(document).ready(
		$( function() {	
			
			$("#preloader").hide();
			$("#jms-loc").hide();
            
			$('#rtype').change(function(){
				
				if($("#rtype").val() === "DA" || $("#rtype").val() === "PA" || $("#rtype").val() === "MA")
					{
						$("#tr-loc").hide();
						$("#tr-dt").hide();
					}
				else if($("#rtype").val() === "DJ" || $("#rtype").val() === "PJ" || $("#rtype").val() === "MJ")
					{
							$("#jms-loc").show();
							$("#loc").hide();
					}
				else
					{
						$("#tr-loc").show();
						$("#tr-dt").show();
						$("#loc").show();
						$("#jms-loc").hide();
					}
			});
			
			
			/* Run report */
			
			$('#gen').click(function(){
				
				var l=document.getElementById("loc").value;
				var d=document.getElementById("dt").value;
				var r=document.getElementById("rtype").value;
												
				if(r === "D")
				{
					if(l && d)
					{
						$("#preloader").show();
						$.ajax({
						    url: '${baseURL}/runDailyRep?mydate='+d+'&myloc='+l,
						    type: 'POST',
						    success: function(data) {
						    	$("#rep-log").val(data);
						    	$("#preloader").hide();
						    	swal("Daily Report", "Process completed for location "+l+"\n Please check log...");
						    	
							    
						    }
						});
					}
					else
					{
						$("#preloader").hide();
				    	swal("Error!!", "Missing Parameter");
					}
					
				}
			
				if(r === "P")
				{
					if(l && d)
					{
						$("#preloader").show();
						$.ajax({
						    url: '${baseURL}/runPerRep?mydate='+d+'&myloc='+l,
						    type: 'POST',
						    success: function(data) {
						    	$("#rep-log").val(data);
						    	$("#preloader").hide();
						    	swal("Periodic Report", "Process completed for location "+l+"\n Please check log...");
							    
						    }
						});
					}
					else
					{
						$("#preloader").hide();
				    	swal("Error!!", "Missing Parameter");
					}
				}
			
				if(r === "M")
				{
					if(l && d)
					{
						$("#preloader").show();
						$.ajax({
						    url: '${baseURL}/runMonthlyRep?mydate='+d+'&myloc='+l,
						    type: 'POST',
						    success: function(data) {
						    	$("#rep-log").val(data);
						    	$("#preloader").hide();
						    	swal("Monthly Generation", "Process completed for location "+l+"\n Please check log...");
														    
						    }
						});
					
					}
					else
					{
						$("#preloader").hide();
				    	swal("Error!!", "Missing Parameter");
					}
				}
						
				if(r === "DA")
				{
					$("#preloader").show();
						$.ajax({
						    url: '${baseURL}/runCronDailyReport',
						    type: 'POST',
						    success: function(data) {
						    	$("#rep-log").val(data);
						    	$("#preloader").hide();
						    	swal("Daily Report", "Process completed. \n Please check log...");
							    
						    }
						});
					
				}
			
				if(r === "PA")
				{
					$("#preloader").show();
					$.ajax({
					    url: '${baseURL}/runCronPeriodicReport',
					    type: 'POST',
					    success: function(data) {
					    	$("#rep-log").val(data);
					    	$("#preloader").hide();
					       	swal("Periodic Report", "Process completed. \n Please check log...");
						    
					    }
					});
				}
			
				if(r === "MA")
				{
					$("#preloader").show();
					$.ajax({
					    url: '${baseURL}/runCronMonthlyReport',
					    type: 'POST',
					    success: function(data) {
					    	$("#rep-log").val(data);
					    	$("#preloader").hide();
					    	swal("Monthly Report", "Process completed. \n Please check log...");
										    
					    }
					});
				}
			
			
				if(r === "DJ")
				{
					if(l && d)
					{
						$("#preloader").show();
						$.ajax({
						    url: '${baseURL}/runDailyJMS?mydate='+d+'&myloc='+l,
						    type: 'POST',
						    success: function(data) {
						    	$("#rep-log").val(data);
						    	$("#preloader").hide();
						    	swal("Daily Report", "Process completed for location "+l+"\n Please check log...");
							    
						    }
						});
					}
					else
					{
						$("#preloader").hide();
				    	swal("Error!!", "Missing Parameter");
					}
				}
			
				if(r === "PJ")
				{
					if(l && d)
					{
						$("#preloader").show();
						$.ajax({
						    url: '${baseURL}/runPeriodicJMS?mydate='+d+'&myloc='+l,
						    type: 'POST',
						    success: function(data) {
						    	$("#rep-log").val(data);
						    	$("#preloader").hide();
						    	swal("Periodic Report", "Process completed for location "+l+"\n Please check log...");
							    
						    }
						});
					}
					else
					{
						$("#preloader").hide();
				    	swal("Error!!", "Missing Parameter");
					}
					
				}
			
				if(r === "MJ")
				{
					if(l && d)
					{
						$("#preloader").show();
						$.ajax({
						    url: '${baseURL}/runMonthlyJMS?mydate='+d+'&myloc='+l,
						    type: 'POST',
						    success: function(data) {
						    	$("#rep-log").val(data);
						    	$("#preloader").hide();
						    	swal("Monthly Report", "Process completed for location "+l+"\n Please check log...");
							    
						    }
						});
					}
					else
					{
						$("#preloader").hide();
				    	swal("Error!!", "Missing Parameter");
					}
				}
				
							
	});
	
			
			
 } )

);



     

</script>

</body>

</html>