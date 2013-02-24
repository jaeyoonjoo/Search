import java.util.*;

public class navieBayes {
	private Map<String, Integer> wordMap = new HashMap<String, Integer>();
	private Map<String, Double> wordPMap = new HashMap<String, Double>();
	private int V;
	private int Docs;
	private Map<String, ClassAttributes> classMap = new HashMap<String, ClassAttributes>();
	private String [] testWords;
	private Map< String, Double> testClProbsMap = new HashMap<String, Double>();
	
    private ValueComparator bvc =  new ValueComparator(testClProbsMap);
    private Map<String,Double> sortedTestClProbsMap = new TreeMap<String, Double>(bvc);

	
	public HashMap<String, Double> getTestClProblsMap() {
		return (HashMap<String, Double>)testClProbsMap;
	}

	
	
	public Map<String, ClassAttributes> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<String, ClassAttributes> classMap) {
		this.classMap = classMap;
	}
	public Map<String, Integer> getWordMap() {
		return wordMap;
	}
	public void setWordMap(Map<String, Integer> wordMap) {
		this.wordMap = wordMap;
	}
	public Map<String, Double> getWordPMap() {
		return wordPMap;
	}
	public void setWordPMap(Map<String, Double> wordPMap) {
		this.wordPMap = wordPMap;
	}
	@Override
	public String toString() {
		return "navieBayes [wordMap=" + wordMap + ", wordPMap=" + wordPMap
				+ ", V=" + V + ", classMap=" + classMap +", Docs="+Docs+"]";
	}
	public int getV() {
		return V;
	}
	
	public void setV(int v) {
		V = v;
	}
	public List<Document> getTextList() {
		return textList;
	}
	public void setTextList(List<Document> textList) {
		this.textList = textList;
	}
	

	private List<Document> textList = new ArrayList<Document>();

	
	public navieBayes(List<Document> textList) {
		this.textList = textList;
		this.builMaps();
		V=calV();
		Docs = textList.size();
		calPriors();
		wordProbabilities();
		
	}

	private void builMaps() {
		

		String key="";
		ClassAttributes ca;
		for (int i=0; i<textList.size(); i++) {
			
			String cl="";
			// populate class count 
			cl= textList.get(i).getCl();
			if (classMap.containsKey(cl) && classMap.get(cl).getDocClassCount() > 0) {
				ca = classMap.get(cl);

				ca.setDocClassCount(ca.getDocClassCount()+1);
			}
			else {
				ca = new ClassAttributes();
				ca.setDocClassCount(1);
			}
			classMap.put(cl, ca);

			

			String [] words = textList.get(i).getText().split(" ");
			for (int j=0; j<words.length; j++) {
				
				//populate class count for each word
				int count=0;
				cl= textList.get(i).getCl();
				ca= classMap.get(cl);
				if (ca.getWordClassCount() > 0) {
					ca= classMap.get(cl);
					ca.setWordClassCount(classMap.get(cl).getWordClassCount()+1);
				}
				else {
					
					ca.setWordClassCount(1);
				}
				classMap.put(cl, ca);

				
				//populate wordMap
				count=0;
				key=words[j]+"|"+textList.get(i).getCl();
				if (wordMap.containsKey(key)) {
					wordMap.put(key,wordMap.get(key).intValue()+1);
				}
				else wordMap.put(key,new Integer(1));			
			}

		}

		return;
	}
	

	private void wordProbabilities() {
		for (String key: wordMap.keySet()) {
			String cl = key.split("\\|")[1];
			int count = wordMap.get(key);
			int wordCount = classMap.get(cl).getWordClassCount();
			double prob = ((double)count+1)/(wordCount+V);
			wordPMap.put(key, prob);
		}
		
	}
	
	private int calV() {
		Map<String, Integer> vMap = new HashMap<String, Integer>();
		for (String key: wordMap.keySet()) {
			String word = key.split("\\|")[0];
			vMap.put(word, 1);	
		}
	
		return vMap.size();
	}
	
	private void calPriors() {
		
		for (String key: classMap.keySet()) {
			ClassAttributes ca = classMap.get(key);
			
			ca.setPriors((double)ca.docClassCount/Docs);
	
			classMap.put(key, ca);
		}
		
	}
	
	public void testSet(String [] testWords) {
		this.testWords = testWords;
		calTestProb();
	}
	

	
	private void calTestProb() {
		
		for (String cl: classMap.keySet()) {	

			double testClProb=0.0;
			for (int i=0; i<testWords.length; i++) {
				String testWord=testWords[i]+"|"+cl;
				
				double testWordProb=0.0;
				if (wordPMap.containsKey(testWord)){
					testWordProb=Math.log(wordPMap.get(testWord));
					
				}
				else {
					testWordProb=Math.log(1.0/(double)(classMap.get(cl).getWordClassCount()+V));
					
				}
				testClProb+=testWordProb;
			}
			
			testClProbsMap.put(cl, Math.log(classMap.get(cl).getPriors())+testClProb);
				
		}
		//System.out.println(testClProbsMap);
		sortedTestClProbsMap.putAll(testClProbsMap);
	}

	public Map<String, Double> getSortedTestClProbsMap() {
		return sortedTestClProbsMap;
	}
	
	
	class ClassAttributes {
		
		int docClassCount;
		int wordClassCount;
		double priors;

		public ClassAttributes() {
			this.docClassCount=0;
			this.priors=0.0;
			this.wordClassCount=0;
		}
		
		public int getDocClassCount() {
			return docClassCount;
		}

		public void setDocClassCount(int docClassCount) {
			this.docClassCount = docClassCount;
		}

		public int getWordClassCount() {
			return wordClassCount;
		}

		public void setWordClassCount(int wordCount) {
			this.wordClassCount = wordCount;
		}
		
		public double getPriors() {
			return priors;
		}

		public void setPriors(double priors) {

			this.priors = priors;
		}

		@Override
		public String toString() {
			return "ClassAttributes [docClassCount=" + docClassCount + ", wordClassCount="
					+ wordClassCount + ", priors=" + priors + "]";
		}
		
	}
	
	class ValueComparator implements Comparator<Object> {

		  Map<String, Double> base;
		  public ValueComparator(Map<String, Double> base) {
		      this.base = base;
		  }

		  public int compare(Object a, Object b) {

		    if((Double)base.get(a) < (Double)base.get(b)) {
		      return 1;
		    } else if((Double)base.get(a) == (Double)base.get(b)) {
		      return 0;
		    } else {
		      return -1;
		    }
		  }
		}

	
}
