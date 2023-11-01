<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<c:set var="baseURL" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div id="header-sec">
<div id="header-child">
<table align="left">
<tr><td><img src="${baseURL}/images/report1.png" id="report" style="height:3.8vw; width:4vw;" /></td></tr>
</table>
<span id="report-text" class="animate-charcter">DATA CENTRE SYSTEM CHECK</span>
<table align="right">
<tr><td><img src="${baseURL}/images/cris.png" id="cris" style="height:3.5vw; width:5vw;" /></td></tr>
</table>
</div>
</div>
<div id="tabs-sec" class="sticky">

<nav class="navbar navbar-expand-sm justify-content-center">
				<ul class="navbar-nav">
					<li class="nav-item active" id="das"><a class="nav-link"><i
							class="fa fa-tachometer" aria-hidden="true"></i> Dashboard</a></li>
					<li class="nav-item" id="rep"><a class="nav-link"><i
							class="fa fa-file-text" aria-hidden="true"></i> Report</a></li>
					<li class="nav-item" id="net"><a class="nav-link"><i
							class="fa fa-exchange" aria-hidden="true"></i> Network</a></li>
					<li class="nav-item" id="replica"><a class="nav-link"><i class="fa fa-refresh" id="rotating" style="display: inline-block;" aria-hidden="true"></i>
							Replication</a></li>
					<li class="nav-item" id="data"><a class="nav-link"><i
							class="fa fa-database" aria-hidden="true"></i> Database</a></li>
					<li class="nav-item" id="kmts"><a class="nav-link"><i
							class="fa fa-server" aria-hidden="true"></i> KMTS</a></li>
					<li class="nav-item" id="mob"><a class="nav-link"><i
							class="fa fa-sitemap" aria-hidden="true"></i> Mobilink</a></li>

				</ul>
			</nav>
</div>
</body>
</html>