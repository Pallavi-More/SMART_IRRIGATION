

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class HistoryServlet
 */
@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	
}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		ArrayList<History> al=new ArrayList();
		ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
		Enumeration paramNames = request.getParameterNames();
		String params[] = new String[4];
		int i = 0;
		String value=null;

		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			System.out.println(paramName);
			String[] paramValues = request.getParameterValues(paramName);
			params[i] = paramValues[0];

			System.out.println(params[i]);
			i++;

		
		}

		try {
			String date = params[0];
    		Class.forName("com.mysql.jdbc.Driver");
    		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartIrrigation", "root", "root123");
    		PreparedStatement ps = con.prepareStatement("select sense_data_temperature,sense_data_humidity,sense_data_moisture from sense_data where sense_data_date=?");
    		ps.setString(1, date);
    		
    	     History m;
    	           //ResultSet rs=d.Select(sql);
     		ResultSet rs = ps.executeQuery();

    	           while(rs.next())
    	           {

    	        	   float temp =rs.getInt("sense_data_temperature");
    	    			float humi =rs.getInt("sense_data_humidity");
    	    			float moisture =rs.getInt("sense_data_moisture");
    	    			m=new History(temp,humi,moisture);
    	    			al.add(m);
    	               
    	           }
    	           String a=al.toString();
    	           //JSONArray arr=new JSONArray();
    	           PrintWriter pw=response.getWriter();
    	           pw.write(a);
    	           System.out.println("data send is:"+a);
    	           //response.getWriter().write(json);
    	           //System.out.println("---------------------------------------->"+json);
    	           //JSONArray json=new JSONArray();
        			//json.addAll(al);
        			//out.write(json);
    	          
    	       }catch(Exception e){

    	           System.out.println(e);

    	       }
    		/*if(rs.next())
    		{
    			float temp =rs.getInt("sense_data_temperature");
    			float humi =rs.getInt("sense_data_humidity");
    			float moisture =rs.getInt("sense_data_moisture");
               value=temp+"#"+humi+"#"+moisture;
                }
    		if(rs.next()){
    			//System.out.print("You are successfully registered...");
				System.out.println("value send is:"+value);
				out.writeObject(value);
			} else {
				out.writeObject("Info not exist.");
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}*/
}
}
