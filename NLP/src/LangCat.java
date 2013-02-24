import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class LangCat {

	/**
	 * @param args
	 */
	
	

	public static void main(String[] args) throws IOException  {
		// TODO Auto-generated method stub
		
		List<Document> trainingList = new ArrayList<Document>();
		BufferedReader in = new BufferedReader(new FileReader("data/language_training.txt"));
		String ln="";
		String regex="\"([^\"]+)\", \"([^\"]+)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher m;
		String cat, txt;
		while ((ln = in.readLine()) != null) {
			//System.out.println(ln);
			m = pattern.matcher(ln);
			while (m.find()) {
				cat=m.group(1);
				txt=m.group(2);

				trainingList.add(new Document(1, txt, cat));
			}
		}
		
	
			
		navieBayes nb = new navieBayes(trainingList);

		System.out.println(nb);
		//String testText="Chinese Chinese Chinese Tokyo Japan";
		String testText="There is a saying that I learned back in college, information is power. A client can become a faithful customer when they are informed of their benefits. I love it when clients have email, tweet, or text because it gives you several avenues to transmit information to them. Also, I send out a book with tips and practical uses. ";
	//	//String testText="Hay un dicho que aprendí en la universidad, la información es poder. Un cliente puede convertirse en un cliente fiel cuando se les informa de sus beneficios. Me encanta cuando los clientes tienen Tweet correo electrónico, o el texto porque le da varias vías para transmitir la información a ellos. Además, me envía un libro con consejos y usos prácticos.";
	//String testText="Il ya un dicton que j'ai appris de retour au collège, l'information c'est le pouvoir. Un client peut devenir un client fidèle quand ils sont informés de leurs avantages. J'adore quand les clients ont courriel, tweet, ou du texte, car il vous donne plusieurs pistes pour leur transmettre des informations. Aussi, je vous envoie un livre avec des conseils et des utilisations pratiques.";
		//String testText=" 때마침 이 앞을 지나던 승합차량이 통학버스 뒤에 정차하는 순간 통학버스가 뒤로 밀려 그 사이에 있던 7명 중 박 군이 차량 사이에 끼여 숨지고 다른 어린이들은 다쳤다. ";
		String [] testWords = testText.split(" ");
		
		
		nb.testSet(testWords);
		System.out.println(nb.getTestClProblsMap());
		Map<String, Double> testResultMap = nb.getSortedTestClProbsMap();
		for (String key: testResultMap.keySet()) {
			System.out.println("Class="+key+" ==> "+testResultMap.get(key));
		}
		
	
	
	}



}
