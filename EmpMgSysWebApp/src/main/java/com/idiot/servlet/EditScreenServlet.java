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

//used For Edit

@WebServlet("/editscreen")
public class EditScreenServlet extends HttpServlet {
final static  String QUERY = "SELECT EMPLOYEENAME,ADDRESS,SALARY FROM EMPLOYEE where EMPLOYEEID=?";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//get printWriter
		PrintWriter pw = res.getWriter();
		
		// set contain type
		res.setContentType("text/html");
		
		// get id of record 
		int id = Integer.parseInt(req.getParameter("id"));

		// load jdbc driver
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		}catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
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

		
		//genrate the connection
		try (
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","kunal","kunal");
				PreparedStatement ps = con.prepareStatement(QUERY);){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			pw.println("<form action='editurl?id="+id+"' method='post'>");
			pw.println("<table align='center'>");
			pw.println("<tr>");
			pw.println("<td>Employee Name</td>");
			pw.println("<td><input type='text' name='EmployeeName' value='"+rs.getString(1)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Employee Address</td>");
			pw.println("<td><input type='text' name='EmployeeAddress' value='"+rs.getString(2)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Emloyee Salary</td>");
			pw.println("<td><input type='text' name='EmployeeSalary' value='"+rs.getFloat(3)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><input type='submit' values='Edit'></td>");
			pw.println("<td><input type='reset' values='cancle'></td>");
			pw.println("</tr>");
			pw.println("</table>");
			pw.println("</form>");
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
