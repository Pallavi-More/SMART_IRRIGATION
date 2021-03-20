import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import javax.xml.bind.JAXBException;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.Forecast;
import com.github.fedy2.weather.data.unit.DegreeUnit;


import com.mysql.jdbc.Connection;

public class CommunicationManager  {
public static String sData;
private int portNo = 2233;

	    ServerSocket serverSocket = null;
	    boolean serverStatus = true;
	    public static String datatosend=null;
	    private ServletContext context;
	    public void setServletContext(ServletContext context) throws Exception{
	    this.context=context; 
	    startCommunication();
	    
	    }
	   
	    
	    void startCommunication() throws Exception{

	        try {
	            serverSocket = new ServerSocket(portNo);
	        } catch (Exception e1) {
	            System.out.println(e1.getMessage());
	            return;
	        }

	        
	        new Thread(new Runnable() {
	            
	            @Override
	            public void run() {

	                System.out.println("Server started");

	                while (serverStatus) 
	                {
	                    try {

	                        System.out.println("Waiting for HW to connect.");
	                        Socket clientSocket = serverSocket.accept();
	                        System.out.println("HW connected");

	                        DataInputStream din = new DataInputStream(clientSocket.getInputStream());
	                        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

	                        byte [] data = new byte[15];
	                        System.out.println("Waiting to read data from HW");

	                        int bytesToRead = 15;
	                        int ret = 0;

	                        while(ret != 15)
	                        {
	                            ret += din.read(data, ret, bytesToRead-ret);
	                           // System.out.println("Read " + ret + " bytes");
	                        }
	                        
	                        sData= new String(data);
	                        System.out.println("New data from HW:" + sData);
	                        
	                        String output1[]=sData.split("#");
	                        System.out.println("Humidity:"+output1[0]);
	                        float humidity=Float.parseFloat(output1[0]);
	                        System.out.println(" Temperature:"+output1[1]);
	                        float temperature=Float.parseFloat(output1[1]);
	                        System.out.println(" Moisture:"+output1[2]);
	                        float moisture=Float.parseFloat(output1[2]);

	                        Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/SmartIrrigation", "root", "root123");
	            			int sid=0;
	            			int ffid=0;
	            			PreparedStatement ps = con.prepareStatement("insert into sense_data values(?,?,?,?,?,CURDATE(),CURTIME())");
	            			ResultSet rs = ps.executeQuery("SELECT MAX(sense_data_sid) AS sense_data_sid FROM sense_data");
	            			if(rs.next()){
	            				sid = rs.getInt("sense_data_sid");
	            				sid++;
	            				}
	            			ResultSet rs1 = ps.executeQuery("SELECT MAX(ffid) AS ffid FROM field");
	            			if(rs1.next()){
	            				ffid = rs1.getInt("ffid");
	            				//ffid++;
	            				}

	            			ps.setInt(1,sid);
	            			ps.setInt(2,ffid);
	            			ps.setFloat(3,temperature);
	            			ps.setFloat(4,humidity);
	            			ps.setFloat(5,moisture);
	            			
	            			int i1 = ps.executeUpdate();
	            			if (i1 > 0) {
	            				System.out.print("Data inserted successfully");
	            				
	            			} else {
	            				System.out.println("Data not inserted");
	            			}
	            			
	                        //send response..
	            			datatosend=WeatherForecast.getRainForcast();
	            			
	            			System.out.println(datatosend);
	                        dos.write(datatosend.getBytes());
	                        System.out.println("Written " + datatosend + " to HW");
	                        
	                                    			   
	                        Thread.sleep(2000);
	                       /* String from = USER_NAME;
            			    String pass = PASSWORD;
            			    String[] to = { RECIPIENT }; // list of recipient email addresses
            			    String subject = "Smart Irrigation Decision";
            			    String body;
            			    if(datatosend.equalsIgnoreCase("y"))
            			    {
            			     body = "Data from our irrigation System: Motor is ON";
            			     KeyOnMail.sendFromGMail(from, pass, to, subject, body);

            			    }
*/
	                        System.out.println("Closing Client Socket..");
	                        clientSocket.close();

	                    } catch (Exception e) {
	                        System.out.println(e.getMessage());
	                    }
	                }
	            }
	        }).start();    
	    }

	    public void stopCommunication() {
	        serverStatus = false;
	    }
}
