public class AlgoMain {

	public static String Algo() {



		// TODO Auto-generated method stub
		String data=CommunicationManager.sData;
		//= new String(data);
        //System.out.println("New data from HW:" + sData);
        
        String output1[]=data.split("#");
        System.out.println("Humidity:"+output1[0]);
        float humidity=Float.parseFloat(output1[0]);
        System.out.println(" Temperature:"+output1[1]);
        float temperature=Float.parseFloat(output1[1]);
        System.out.println(" Moisture:"+output1[2]);
        float moisture=Float.parseFloat(output1[2]);
        
		String fPath = ARFFCreator.generateARFFFile("sugarcane",9,"red",temperature,humidity,moisture);
		String suggestion=null;
		try {
			suggestion = MyC45Prediction.predictWater(fPath);
			System.out.println("suggestion:" + suggestion );		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return suggestion;
			}
		
	}

