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
      
<div class="container">

<%-- <c:import url="${baseURL}/WEB-INF/views/header.jsp" /> --%>
<div id="content-sec">

  <div class="container">
 <div id="preloader"><div class="load"></div><span style="border:1px solid #fff;padding:5px; color:#fff; margin-top:21%; font-size:1em;">-Process running- Please wait...</span></div> 
 
  <div id="accordion">
  
    <div class="card">
      <div class="card-header">
        <a class="card-link" data-toggle="collapse" href="#collapseOne">
         <i class="fa fa-file-text" aria-hidden="true"></i> Report Generation
        </a>
      </div>
      <div id="collapseOne" class="collapse show" data-parent="#accordion">
        <div class="card-body" style="text-align:center; padding-top:10px;">   
		<br>
				<table style="width:50%; margin:auto; margin-top:0px; padding:10px; border:0px solid #000; border-radius:5px; box-shadow:0px 0px 2px #ccc;" class="table-borderless">

							<tr style="background-color: #728f22;color: #ffffff;border-radius: 25px 25px 0px 0px;width: 100%;font-weight:bold; text-align:center; font-size:1vw;">
							<td colspan="2"><h5>Generate Report</h5></td>
							</tr>
							<tr>
							<td style="text-align:right;"><b>Report Type</b></td>
							<td><select id="rtype" class="form-control" style="max-width:200px">
							<option></option>
							<option value="D">NS Daily</option>
							<option value="P">NS Periodic</option>
							<option value="M">NS Monthly</option>
							<option value="DJ">EW Daily</option>
							<option value="PJ">EW Periodic</option>
							<option value="MJ">EW Monthly</option>
							<option value="DA">NS Daily All</option>
							<option value="PA">NS Periodic All</option>
							<option value="MA">NS Monthly All</option>
							</select></td>
							</tr>
							
							<tr id="tr-loc">
							<td style="text-align:right;"><b>Location</b></td>
							<td>
							<select id="loc" class="form-control" style="max-width:200px">
							<option></option>
							<option value="KBEL">KBEL</option>
							<option value="KCEN">KCEN  </option>
							<option value="KCWC">KCWC</option>
							<option value="KDMI">KDMI</option>
							<option value="KESP">KESP</option>   
							<option value="KGPK">KGPK</option>   
							<option value="KGTN">KGTN</option>   
							<option value="KJPK">KJPK</option>   
							<option value="KKHG">KKHG</option>   
							<option value="KKNZ">KKNZ</option>   
							<option value="KKVS">KKVS</option>   
							<option value="KMDI">KMDI</option>   
							<option value="KMHR">KMHR </option>   
							<option value="KMSN">KMSN</option>   
							<option value="KMUK">KMUK</option>   
							<option value="KNBN">KNBN</option>   
							<option value="KNOA">KNOA</option>   
							<option value="KNTJ">KNTJ</option>   
							<option value="KPSK">KPSK</option>   
							<option value="KRSB">KRSB</option>   
							<option value="KRSD">KRSD</option>   
							<option value="KSHO">KSHO</option>
							<option value="KSHY">KSHY</option>
							<option value="KSKD">KSKD</option>
							</select>
							<select id="jms-loc" class="form-control" style="max-width:200px">
							<option></option>
							<option value="SVSA">SVSA</option>
							<option value="KESA">KESA</option>
							<option value="CPSA">CPSA</option>
							<option value="CCSC">CCSC</option>
							<option value="BCSD">BCSD</option>   
							<option value="SSSA">SSSA</option>   
							<option value="PBGB">PBGB</option>   
							<option value="KBAR">KBAR</option>
							<option value="KDSW">KDSW</option> 
							</select>
							</td>
							</tr>
							
														
							<tr id="tr-dt">
							<td style="text-align:right;"><b>Date</b></td>
							<td><input type="date" id="dt" class="form-control" style="max-width:200px"/></td>
							</tr>
							<tr>
							<td colspan="2"><button id="gen" class="btn btn-primary">GENERATE</button></td>
							</tr>
							
				</table>		
		<br/>
        <span style="background-color: #d69320;color: #ffffff;padding: 12px; padding-left:10%;padding-right:10%;border-radius: 25px 25px 25px 25px;width: 25%; font-weight:bold; margin-left: 2%;">Report Log</span>
        <textarea rows="22" id="rep-log" cols="200" align="center" class="form-control" style="margin-top:-10px; width:95%; margin-left:2%;"></textarea>	     
        </div>
      </div>
    </div>
    


</div>

<div id="feedback"></div>
</div>
</div>

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