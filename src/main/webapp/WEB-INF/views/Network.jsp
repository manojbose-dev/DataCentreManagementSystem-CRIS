<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
		 <script src="${baseURL}/js/popper.min.js"></script>
    	<link rel="stylesheet" href="${baseURL}/css/style1.css" /> 
      	<script src="${baseURL}/js/CustomAlert.js"></script>

</head>
<style>

/* Color change code */
@keyframes changeColor {
0% {background-color:#113b6b;}
50% {background-color:#367336;}
100% {background-color:#113b6b;}
}
#node {
	animation-duration: 2s;
	animation-name: changeColor;
	animation-iteration-count: infinite;
	animation-direction: alternate;
}

/* Color change code */
@keyframes changeColorZero {
0% {background-color:#113b6b;}
100% {background-color:#c70606;}
}
#nodeZero {
	animation-duration: 2s;
	animation-name: changeColorZero;
	animation-iteration-count: initial;
	animation-direction: normal;
}

</style>
<body>   
	<div class="container">
		<div id="content-sec">



			<div class="container">


				<div id="accordion">
				
					<div class="card">
						<div class="card-header">
							<a class="card-link" data-toggle="collapse" href="#collapseOne">
								<i class="fa fa-exchange" aria-hidden="true"></i> Metro Network Information
							</a>
						</div>
						<div id="collapseOne" class="collapse show"
							data-parent="#accordion">
							<div class="card-body" style="margin:auto;">
													
							<c:forEach var="ans" items="${ALL_NET_STATUS}">
							<c:choose>
							<c:when test="${ans.status eq 0}">							
								<a href="#" onClick="detail('${ans.location}','${ans.device}','${ans.terminal}','${ans.ip}','In Link','${ans.update_time}')"><span id="node">${ans.terminal}</span></a>
							</c:when>
							<c:otherwise>
								<a href="#" onClick="detail('${ans.location}','${ans.device}','${ans.terminal}','${ans.ip}','No Link','${ans.update_time}')"><span id="nodeZero">${ans.terminal}</span></a>
							</c:otherwise>
							</c:choose>						
							</c:forEach>
							
							</div>
						</div>
					</div>

				</div>
			</div>



		</div>
	</div>
	<script>

function detail(l,d,t,ip,ln,up)
{
	//alert(l+" "+d+" "+t+" "+ip+" "+ln+" "+up);
	//document.getElementById("loc").innerHTML=l;
	swal("Network Info", "Location: "+l+"\n Device: "+d+"\n Terminal: "+t+"\n IP Address: "+ip+"\n Status: "+ln+"\n Update Time: "+up);

}

</script>
</body>

</html>