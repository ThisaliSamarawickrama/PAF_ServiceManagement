<%@page import="model.Approvement"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Service Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="components/jquery-3.2.1.min.js"></script>
<script src="components/main.js"></script>
</head>
<body>
<div class="container"> 
		<div class="row">  
		
			<div class="col-8">       
				<h1 class="m-3">Service Management</h1>        
				
				<form id="formService" name="formService" method="post" action="Service.jsp">  
					Topic:  
					<input id="topic" name="topic" type="text" class="form-control form-control-sm">  
					
					<br> 
					Description:  
					<input id="description" name="description" type="text" class="form-control form-control-sm">  
					
					<br>
					 Date:  
					 <input id="date" name="date" type="text" class="form-control form-control-sm">  
					 
					 <br>  
			
					 
					 <br>  
					 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">  
					 <input type="hidden" id="hidNotificationIDSave" name="hidNotificationIDSave" value=""> 
					 
				</form> 
				
				<div id="alertSuccess" class="alert alert-success"></div>  
				<div id="alertError" class="alert alert-danger"></div> 
				
				<br>  
				<div id="divItemsGrid">   
					<%
   					Service appObj = new Service();
   									out.print(appObj.readService());
   					%>  
					
				</div> 
				  
 			</div>
 		 
 		</div>    
 		
 
	</div> 

</body>

</html>