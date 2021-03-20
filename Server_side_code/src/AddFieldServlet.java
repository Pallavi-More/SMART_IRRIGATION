

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class AddFieldServlet
 */
@WebServlet("/AddFieldServlet")
public class AddFieldServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddFieldServlet() {
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
	ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
	Enumeration paramNames = request.getParameterNames();
	String params[] = new String[4];
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
		String crop_name = params[0];
		String soil_type = params[1];
		String plant_date=params[2];
		String device_code=params[3];
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartIrrigation", "root", "root123");
		int ffid=0;
		int field_fid = 1;

		PreparedStatement ps = con.prepareStatement("insert into field values(?,?,?,?,?,?)");
		ResultSet rs = ps.executeQuery("SELECT MAX(ffid) AS ffid FROM field");
		if(rs.next()){
			ffid = rs.getInt("ffid");
			ffid++;
			}
		ResultSet rs1 = ps.executeQuery("SELECT MAX(id) AS field_fid FROM farm");
		if(rs1.next()){
			field_fid=rs1.getInt("field_fid");
			//field_fid++;
		}

		ps.setInt(1,ffid);
		ps.setString(2,crop_name);
		ps.setString(3,soil_type);
		ps.setString(4,plant_date);
		ps.setString(5,device_code);
		ps.setInt(6, field_fid);
				 

		int i1 = ps.executeUpdate();
		if (i1 > 0) {
			System.out.print("Field Details inserted Successfully...");
			out.writeObject("Field details insertion done successfully.");
		} else {
			out.writeObject("Not inserted.");
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
