
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MyC45Prediction 
{
	
	public static String predictWater(String arffPath) throws Exception
	{		
		DataSource source = new DataSource("F:\\water.arff");
		Instances traindata = source.getDataSet(); //read datset
		traindata.setClassIndex(traindata.numAttributes()-1);//create the classes of each attributes

		NaiveBayes j48 = new NaiveBayes(); 
		
		//J48 j48 = new J48();
		j48.buildClassifier(traindata);
		/**
		 * load test data
		 */
		//DataSource source2 = new DataSource("F:\\water-unknown.arff");
		DataSource source2 = new DataSource(arffPath);
		Instances testdata = source2.getDataSet();
		testdata.setClassIndex(testdata.numAttributes()-1);
		/**
		 * make prediction
		 */
		String predString = "3";
		for (int j=0;j<testdata.numInstances();j++)
		{
			double actualClass = testdata.instance(j).classValue();
			String actual = testdata.classAttribute().value((int) actualClass);
			Instance newInst = testdata.instance(j);
			double preNB = j48.classifyInstance(newInst);
			
			predString = testdata.classAttribute().value((int) preNB);
			System.out.println(actual+","+predString);
			System.out.println("Predection Rating: "+predString);
			return predString;
		}
		return predString;
	}	
}
