package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//		pw.println("<a href='NavBar.html'> Home  </a>");


//used For Showing Employee List

@WebServlet("/EmployeeList")
public class EmpListServlet extends HttpServlet {
final static  String QUERY = "SELECT EMPLOYEEID,EMPLOYEENAME,ADDRESS,SALARY FROM EMPLOYEE";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//get printWriter
		PrintWriter pw = res.getWriter();
		
		// set contain type
		res.setContentType("text/html");
		

		// load jdbc driver
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		}catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		
		//genrate the connection
		try (
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","kunal","kunal");
				PreparedStatement ps = con.prepareStatement(QUERY);){
			ResultSet rs = ps.executeQuery();
			

			//for nav bar
			
			pw.println("<!DOCTYPE html>");
			pw.println("<html lang=\"en\">");
			pw.println("<head>");
			pw.println("<meta charset=\"UTF-8\">");
			pw.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">");
			pw.println("</head>");
			pw.println("<body>");
			pw.println("<nav class=\"navbar navbar-default\">");
			pw.println("<div class=\"container-fluid\">");
			pw.println("<div class=\"navbar-header\"> ");
			pw.println("<a class=\"navbar-brand\" href=\"#\" >Employee Information</a>");
			pw.println("</div>");
			pw.println("<ul class=\"nav navbar-nav\"> ");
			pw.println("<li ><a href=\"NavBar.html\">Home</a></li>");
			pw.println("<li><a href=\"home.html\">Register</a></li>");
			pw.println("<li><a href=\"EmployeeList\">Employee List</a></li>");
			pw.println("</ul>");
			pw.println(" </div> ");
			pw.println("</nav>");
			pw.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script>");
			pw.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\"></script> ");
			pw.println("</body>");
			pw.println("</html>");
			
			//to run the query
			
			pw.println("<table border='1' align='center' class=\"bg-danger text-white\" table-size=\"1500px\">");
			pw.println("<h1 class=\"bg-danger text-white text-center\">Employee List</h1>");
			pw.println("<br>");
			pw.println("<br>");
			pw.println("<tr>");
			pw.println("<th>Employee ID</th>");
			pw.println("<th>Employee Name</th>");
			pw.println("<th>Address   .</th>");
			pw.println("<th>Salary   .</th>");
			pw.println("<th>Edit   .</th>");
			pw.println("<th>Delete   .</th>");
			pw.println("</tr>");
			while(rs.next()) {
				pw.println("<tr>");
				pw.println("<td>"+rs.getString(1)+"</td>");
				pw.println("<td>"+rs.getString(2)+"</td>");
				pw.println("<td>"+rs.getString(3)+"</td>");
				pw.println("<td>"+rs.getFloat(4)+"</td>");
				pw.println("<td><a href='editscreen?id="+rs.getString(1)+"'>Edit</a></td>");
				pw.println("<td><a href='deleteurl?id="+rs.getString(1)+"'>Delete</a></td>");
				pw.println("</tr>");
			}
			pw.println("</table>");
		}catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h1>"+ se.getMessage()+"</h2>");
		}catch(Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+ e.getMessage()+"</h2>");
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}

}
