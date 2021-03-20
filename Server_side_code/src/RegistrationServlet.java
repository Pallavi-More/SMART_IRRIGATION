

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.io.*;  
import java.sql.*;
/**
 * Servlet implementation class RegistrationServlet
 */
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;



import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class MyTempServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		ObjectOutputStream out = new ObjectOutputStream(
				response.getOutputStream());
		Enumeration paramNames = request.getParameterNames();
		String params[] = new String[8];
		int i = 0;

		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			System.out.println(paramName);
			String[] paramValues = request.getParameterValues(paramName);
			params[i] = paramValues[0];

			System.out.println(params[i]);
			i++;

		}

		try {
			String uname = params[0];
			String ucontact = params[1]; 
			String uemail = params[2];
            String uaddress=params[3];
            String ustate=params[4];
            String username=params[5];
            String password=params[6];
            String cpassword=params[7];
			Class.forName("com.mysql.jdbc.Driver");
			// Connection con = (Connection)
			// DriverManager.getConnection("jdbc:mysql://192.168.1.66:3306/dbname?"+"user=root&password=root");
			Connection con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SmartIrrigation", "root", "root123");
			int uid=0;
			PreparedStatement ps = con
					.prepareStatement("insert into user values(?,?,?,?,?,?,?,?,?)");
			ResultSet rs = ps.executeQuery("SELECT MAX(uid) AS uid FROM user");
			if(rs.next()){
				uid = rs.getInt("uid");
				uid++;
				}
			
			ps.setInt(1, uid);
			ps.setString(2, uname);
			ps.setString(3,ucontact);
			ps.setString(4,uemail);
			ps.setString(5,uaddress);
			ps.setString(6,ustate);
			ps.setString(7,username);
			ps.setString(8,password);
			ps.setString(9,cpassword);

			int i1 = ps.executeUpdate();
			if (i1 > 0) {
				System.out.print("You are successfully registered...");
				out.writeObject("Registration done successfully.");
			} else {
				out.writeObject("Not registered.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * if(params[0].equals("vaibhav")) {
		 * 
		 * if(params[1].equals("vaibhav@gmail.com")) {
		 * out.writeObject("success"); } else {
		 * out.writeObject("wrong password"); } } else {
		 * out.writeObject("wrong username"); }
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		// response.setContentType("text/html");
		// PrintWriter out = response.getWriter();

		// String n=request.getParameter("name");
		// String p=request.getParameter("userPass");
		// String e=request.getParameter("email");
		// String c=request.getParameter("id");
		// ObjectOutputStream out = new
		// ObjectOutputStream(response.getOutputStream());
		/*
		 * try{
		 * 
		 * Enumeration paramNames = request.getParameterNames(); String
		 * params[]= new String[2]; int i=0; while(paramNames.hasMoreElements())
		 * { String paramName = (String)paramNames.nextElement();
		 * 
		 * System.out.println(paramName ); String[] paramValues =
		 * request.getParameterValues(paramName); params[i] = paramValues[0];
		 * 
		 * System.out.println(params[i]); i++;
		 * 
		 * }
		 * 
		 * String n=request.getParameter(params[0]); String
		 * e=request.getParameter(params[1]);
		 * 
		 * Class.forName("com.mysql.jdbc.Driver"); //Connection con =
		 * (Connection)
		 * DriverManager.getConnection("jdbc:mysql://192.168.1.66:3306/dbname?"
		 * +"user=root&password=root"); Connection con=(Connection)
		 * DriverManager
		 * .getConnection("jdbc:mysql://192.168.1.66:3306/user","root","root");
		 * 
		 * PreparedStatement
		 * ps=con.prepareStatement("insert into users values(?,?,?)");
		 * ps.setString(1,"4"); ps.setString(2,n); //ps.setString(2,p);
		 * ps.setString(3,e); //ps.setString(4,c);
		 * 
		 * int i1=ps.executeUpdate(); if(i1>0)
		 * System.out.print("You are successfully registered...");
		 * 
		 * 
		 * }catch (Exception e2) {System.out.println(e2);}
		 * 
		 * out.close();
		 */
	}

}
