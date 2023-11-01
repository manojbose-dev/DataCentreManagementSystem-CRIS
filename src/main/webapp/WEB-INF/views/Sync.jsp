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
<div id="preloader"><div class="load"></div><span style="border:1px solid #fff;padding:5px; color:#fff; margin-top:21%;">-Process running- Please wait...</span></div> 

<%-- <c:import url="${baseURL}/WEB-INF/views/header.jsp" /> --%>
<div id="content-sec">



  <div class="container">
 
 
  <div id="accordion">
  
    <div class="card">
      <div class="card-header">
        <a class="card-link" data-toggle="collapse" href="#collapseOne">
         <i class="fa fa-file-text" aria-hidden="true"></i> Sync Anomaly
        </a>
      </div>
      <div id="collapseOne" class="collapse show" data-parent="#accordion">
        <div class="card-body" style="text-align:center; padding-top:0px;">   
			<br>
				<table style="width:40%; margin-top:0px; padding:1%; border:0px solid #000; border-radius:5px; box-shadow:0px 0px 2px #ccc;" id="sync-table" align="center">

							<tr style="background-color: #728f22;color: #ffffff;border-radius: 25px 25px 0px 0px;width: 100%;font-weight:bold; text-align:center; font-size:1vw;"><td colspan="2">
							<h5>Synchronize Terminal</h5>
							</td></tr>
							<tr id="tr-id">
							<td style="text-align:right;"><b>Enter Terminal ID</b></td>
							<td>
							<input type="text" id="tid" class="form-control">
							</td>
							</tr>
							
							<tr>
							<td colspan="2"><button id="sync" class="btn btn-primary">Start Sync</button></td>
							</tr>
							
				</table>		

        <br/>
        <span style="background-color: #d69320;color: #ffffff;padding: 12px; padding-left:10%;padding-right:10%;border-radius: 25px 25px 25px 25px;width: 25%; font-weight:bold; margin-left: 2%;">Synchronization Log</span>
        <textarea rows="22" id="sync-rep" cols="200" align="center" class="form-control" style="margin-top:-10px;"></textarea>
        
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
			
			/* Run Sync */
			$("#preloader").hide();
			$('#sync').click(function(){
				
				var t=document.getElementById("tid").value;
							
				if(t)
				{
					
					$("#preloader").show(); 
					
						$.ajax({
						    url: '${baseURL}/SyncTerm?ter='+t,
						    type: 'POST',
						    success: function(data) {
						    	$("#sync-rep").val(data);
						    	$("#preloader").hide();
						    	swal("Sync Terminal", "Process completed!!!.");
						    	
						    }
						});
				}
				else
					{
						alert("Empty Terminal Id");
					}  
							
	});
	
 } )

);



     

</script>
</body>

</html>