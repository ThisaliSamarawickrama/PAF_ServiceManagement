package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Service {

	//A common method to connect to the DB 
		private Connection connect() {
			Connection con = null;
			
			try {
				 Class.forName("com.mysql.jdbc.Driver");
				 //Provide the correct details: DBServer/DBName, username, password 
				 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/service", "root", "root");
				//For testing          
				 System.out.print("Successfully connected");
				 
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return con; 
		}
		
		public String readService() {  
			String output = "";  
			
			try {  
				Connection con = connect();  
				if (con == null)  {   
					return "Error while connecting to the database for reading.";  
				} 

				// Prepare the html table to be displayed   
				output = "<table border='1'><tr><th>topic</th>"
						+ "<th>description</th>date<th></th>"
						+ "<th>Update</th><th>Remove</th></tr>";


				  String query = "select * from service";   
				  Statement stmt = con.createStatement();   
				  ResultSet rs = stmt.executeQuery(query); 

				  // iterate through the rows in the result set   
				  while (rs.next())   {  

					  	String notificationID = Integer.toString(rs.getInt("notificationID"));
						String topic = rs.getString("topic");
						String description = rs.getString("description");
						String date = rs.getString("date");
						
						
					  // Add into the html table    

					  output += "<tr><td><input id='hidNotificationIDUpdate' name='hidNotificationIDUpdate' type='hidden' value='" + notificationID + "'>" + topic + "</td>"; 

					  output += "<td>" + description + "</td>";
						output += "<td>" + date + "</td>";
						
						
					  
					// buttons     
					  output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
					  		+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-notificationID='"+ notificationID +"'>"+"</td></tr>";

					} 
				  
				  con.close(); 

				  // Complete the HTML tables
				  output += "</table>"; 
				}
				catch (Exception e) {  
					output = "Error while reading the service.";  
					System.err.println(e.getMessage()); 
				}

				return output;
			}
		
		//Insert notification
		public String insertService(String topic, String description, String date) {
			String output = "";

			try {
				Connection con = connect();  

				if (con == null) {
					return "Error while connecting to the database";
				}

				// create a prepared statement   
				String query = " insert into service (`notificationID`,`topic`,`description`,`date`)"+" values (?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values 
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, topic);
				preparedStmt.setString(3, description);
				preparedStmt.setString(4, date);
				
				
				//execute the statement   
				preparedStmt.execute();   
				con.close(); 

				//Create JSON Object to show successful msg.
				String newService = readService();
				output = "{\"status\":\"success\", \"data\": \"" + newService + "\"}";
			}
			catch (Exception e) {  
				//Create JSON Object to show Error msg.
				output = "{\"status\":\"error\", \"data\": \"Error while Inserting service.\"}";   
				System.err.println(e.getMessage());  
			} 

			 return output; 
		}
		
		//Update notification
		public String updateService(String notificationID, String topic, String description, String date)  {   
			String output = ""; 
		 
		  try   {   
			  Connection con = connect();
		 
			  if (con == null)    {
				  return "Error while connecting to the database for updating."; 
			  } 
		 
		   // create a prepared statement    
			   String query = "UPDATE service SET topic=?,description=?,date=?WHERE notificationID=?";
				 
		   PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		   // binding values    
		   preparedStmt.setString(1, topic);
			preparedStmt.setString(2,description);
			preparedStmt.setString(3, date);
			preparedStmt.setInt(5, Integer.parseInt(notificationID));
		   
		 
		   // execute the statement    
		   preparedStmt.execute();    
		   con.close(); 
		 
		   //create JSON object to show successful msg
		   String newService = readService();
		   output = "{\"status\":\"success\", \"data\": \"" + newService + "\"}";
		   }   catch (Exception e)   {    
			   output = "{\"status\":\"error\", \"data\": \"Error while Updating service Details.\"}";      
			   System.err.println(e.getMessage());   
		   } 
		 
		  return output;  
		  }
		
		public String deleteService(String notificationID) {  
			String output = ""; 
		 
		 try  {   
			 Connection con = connect();
		 
		  if (con == null)   {    
			  return "Error while connecting to the database for deleting.";   
		  } 
		 
		  // create a prepared statement   
		  String query = "DELETE FROM service WHERE notificationID=?"; 
		 
		  PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		  // binding values   
		  preparedStmt.setInt(1, Integer.parseInt(notificationID));       
		  // execute the statement   
		  preparedStmt.execute();   
		  con.close(); 
		 
		  //create JSON Object
		  String newService = readService();
		  output = "{\"status\":\"success\", \"data\": \"" + newService + "\"}";
		  }  catch (Exception e)  {  
			  //Create JSON object 
			  output = "{\"status\":\"error\", \"data\": \"Error while Deleting service.\"}";
			  System.err.println(e.getMessage());  
			  
		 } 
		 
		 return output; 
		 }
}
