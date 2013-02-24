import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class TextCat {

	/**
	 * @param args
	 */
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		List<Document> trainingList = new ArrayList<Document>();
		trainingList.add(new Document(1, "Chinese Beijing Chinese", "C"));
		trainingList.add(new Document(1, "Chinese Chinese Shanghai", "C"));
		trainingList.add(new Document(1, "Chinese Macao", "C"));
		trainingList.add(new Document(1, "Tokyo Japan Chinese", "J"));
			
		navieBayes nb = new navieBayes(trainingList);
		System.out.println(nb);
		//String testText="Chinese Chinese Chinese Tokyo Japan";
		String testText="Chinese Japan Chinese Tokyo Japan";
		String [] testWords = testText.split(" ");
		
		
		nb.testSet(testWords);
		System.out.println(nb.getTestClProblsMap());
		Map<String, Double> testResultMap = nb.getSortedTestClProbsMap();
		for (String key: testResultMap.keySet()) {
			System.out.println("Class="+key+" ==> "+testResultMap.get(key));
		}
		
	
	
	}



}
