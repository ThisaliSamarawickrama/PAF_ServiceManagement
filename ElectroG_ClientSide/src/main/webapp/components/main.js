$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(_event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateServiceForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidNotificationIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "ServiceAPI",
		type : t,
		data : $("#formService").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onServiceSaveComplete(response.responseText, status);
		}
	});
}); 

function onServiceSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidNotificationIDSave").val("");
	$("#formService")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidNotificationIDSave").val($(this).closest("tr").find('#hidNotificationIDUpdate').val());     
	$("#topic").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#description").val($(this).closest("tr").find('td:eq(0)').text());         
	$("#date").val($(this).closest("tr").find('td:eq(2)').text());      
	

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "ServiceAPI",
		type : "DELETE",
		data : "mcode=" + $(this).data("mcode"),
		dataType : "text",
		complete : function(response, status)
		{
			onServiceDeletedComplete(response.responseText, status);
		}
	});
});

function onServiceDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validateServiceForm() {  
	// app_Details  
	if ($("#topic").val().trim() == "")  {   
		return "Insert area.";  
		
	}
	
	// app_status  
	if ($("#description").val().trim() == "")  {   
		return "Insert description.";  
		
	} 
	
	 
	 // Email 
	if ($("#date").val().trim() == "")  {   
		return "Insert date.";  
		
	} 
	 
	 return true; 
	 
}