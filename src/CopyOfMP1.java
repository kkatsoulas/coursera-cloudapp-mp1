import java.io.*;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CopyOfMP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public CopyOfMP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    
    public static ArrayList<String> readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();

        return lines;
    }
    
    public String[] process() throws Exception {
        String[] ret = new String[20];
        StringBuilder builder = new StringBuilder();
        String s2 = "";
        
        ArrayList<String> lines = readLines(this.inputFileName);

        String[] Initial_lines = lines.toArray(new String[lines.size()]);
        List<String> Processing_lines = new ArrayList<>();
        
        		
        myHashMap<Object, Integer> c = new myHashMap<Object, Integer>();
        
		Integer[] Specific_Indexes = getIndexes();
		
		int count_football = 0;
		
		for (Integer i : Specific_Indexes){
			Processing_lines.add(Initial_lines[i]);
			
			StringTokenizer st = new StringTokenizer(Initial_lines[i], delimiters);
        	while (st.hasMoreTokens()) {
        		s2 = "";
        		s2 = (((String)st.nextElement()).trim()).toLowerCase();
        		if(!Arrays.asList(stopWordsArray).contains(s2)){
        			c.add(s2);
        			if(s2 == "football"){ count_football++; }
        		}
    		}
		}
		/*
		for (String s : Initial_lines)
        {
        	StringTokenizer st = new StringTokenizer(s, delimiters);
        	while (st.hasMoreTokens()) {
        		s2 = "";
        		s2 = (((String)st.nextElement()).trim()).toLowerCase();
        		if(!Arrays.asList(stopWordsArray).contains(s2)){
        			c.add(s2);
        		}
    		}
        }
        **/		 
		Map<String, Integer> sortedMap2 = myHashMap.sortByValue(c);

        //System.out.println("Number of distinct objects: " + c.size());

        //System.out.println("Frequency of different objects: ");

        Integer i = 0;
        
        for (Entry<String, Integer> entry : sortedMap2.entrySet() ) {
        	
        	if(i < 20){
        		//System.out.println(entry.getKey() + " - " + entry.getValue());
        		ret[i] = entry.getKey();
        	}
        	else break;
        	i++;
        }
        
        List<String> Processing_Words = new ArrayList<>();
        
        for(Entry<Object, Integer> entry : c.entrySet()){
        	
        	Processing_Words.add(entry.getKey() + "_" + entry.getValue());
        }
        
        


        return ret;
    }
    
    public static void PrintOut( Iterator<String> it, String file) throws UnsupportedEncodingException, FileNotFoundException{
    	
    	Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        	try {
        		while (it.hasNext()) {
        			String UTF8Str = new String(it.next().toString().getBytes("UTF8"));
        			out.write(UTF8Str);
            		out.write(System.lineSeparator());
        		}
        		
        	} catch (IOException e) {
				e.printStackTrace();
			} finally {
        	    try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "input.txt";
            CopyOfMP1 mp = new CopyOfMP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}


 class myHashMap<K, V>  extends HashMap<K, V>  {
	 
	public static Map sortByValue(Map unsortedMap) {
		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
	
    // Counts unique objects
    public void add(K o) {
        int count = this.containsKey(o) ? ((Integer)this.get(o)).intValue() + 1 : 1;
        super.put(o, (V) new Integer(count));
    }
}
 
class ValueComparator implements Comparator {
 
	Map map;
 
	public ValueComparator(Map map) {
		this.map = map;
	}
 
	/*public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		return valueB.compareTo(valueA);
	}*/
	public int compare(Object keyA, Object keyB) {
		 Integer valueA = (Integer) map.get(keyA);
		Integer valueB = (Integer) map.get(keyB);
		if(valueA.intValue() > valueB.intValue()){return -1;}
		else if(valueA.intValue() == valueB.intValue()){
			int lex = keyA.toString().compareTo(keyB.toString()); 
			if(lex > 0) return 1;else return -1;}
		else{return 1;}
	}
}


