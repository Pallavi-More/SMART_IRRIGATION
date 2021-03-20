

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.mysql.jdbc.Connection;

/**
 * Servlet implementation class CottonServlet
 */
@WebServlet("/EventServlet")
public class EventServlet extends HttpServlet {
	 public static String Event_decision="";
	private static final long serialVersionUID = 1L;
      
    /**
     * @return 
     * @see HttpServlet#HttpServlet()
     */
    public EventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		ArrayList<String> al=new ArrayList<String>();
		ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());
		Enumeration paramNames = request.getParameterNames();
		String params[] = new String[4];
		int i = 0;
		//String type = null;

		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			System.out.println(paramName);
			String[] paramValues = request.getParameterValues(paramName);
			params[i] = paramValues[0];

			System.out.println(params[i]);
			i++;

		
		}

		try {
			String event_type = params[0];
			Class.forName("com.mysql.jdbc.Driver");
    		Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartIrrigation", "root", "root123");
    		int ffid=1;
    		int id=0;
    		PreparedStatement ps = con.prepareStatement("insert into event values(?,?,CURRENT_TIMESTAMP,?)");
    		ResultSet rs = ps.executeQuery("SELECT MAX(event_id) AS event_id FROM event");
     				while(rs.next())
    	           {
    	        	   id =rs.getInt("event_id");
    	        	   id++;
    	           }
     				ResultSet rs1 = ps.executeQuery("SELECT MAX(ffid) as ffid FROM field");
     				if(rs1.next()){
     					ffid=rs1.getInt("ffid");
     					//field_fid++;
     				}

     				ps.setInt(1,id);
     				ps.setInt(2,ffid);
     				ps.setString(3,event_type);
     				int i1 = ps.executeUpdate();
     				if (i1 > 0) {
     					System.out.print("Event inserted in Sussessfully...");
     					if(event_type.equalsIgnoreCase("on"))
     					{
     						Event_decision="Y";
     					}
     					else
     					{
     						Event_decision="N";
     					}
     					out.writeObject("Event inserted successfully.");
     				} else {
     					out.writeObject("Event not inserted");
     				}
     			} catch (Exception e) {
     				e.printStackTrace();
     			}

     				/*type=rs.getString(event_type);
    	           PrintWriter pw=response.getWriter();
    	           pw.write(type);
    	           System.out.println("On/Off:"+type);
    	           
    	          
    	       }catch(Exception e){

    	           System.out.println(e);

    	       }*/
}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}


