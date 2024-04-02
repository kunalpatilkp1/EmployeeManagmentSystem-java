package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Used For Register Employee

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	final static  String QUERY = "INSERT INTO Employee(EmployeeID,EmployeeName,Address,Salary) VALUES(?,?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//get printWriter
		PrintWriter pw = res.getWriter();
		
		// set contain type
		res.setContentType("text/html");        
        
		//Get the Employee Information
		String EmployeeID= req.getParameter("EmpID");
		String EmployeeName= req.getParameter("EmpName");
		String Address= req.getParameter("EmpAdd");
		float Salary= Float.parseFloat(req.getParameter("EmpSal"));

		
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
			ps.setString(1, EmployeeID);
			ps.setString(2, EmployeeName);
			ps.setString(3, Address);
			ps.setFloat(4, Salary);
			int count = ps.executeUpdate();
			if(count==1) {
				pw.println("<h2> Record Is Registered Sucsessfully </h2>");
			}else {
				pw.println("<h2> Record Is Not Registered </h2>");
			}
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
