import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import java.util.StringTokenizer;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.BytesWritable;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import java.io.StringReader;

public  class MyMapper1
	extends Mapper<Text, BytesWritable, Text, Text> {
	//private final static IntWritable one = new IntWritable( 1 );
	private Text word = new Text();
		private Text word2 = new Text();

	private Text value1 = new Text();
	private Text value2 = new Text();

	public void map( Text key, BytesWritable value, Context context )
	    throws IOException, InterruptedException {
	    try{

		    String filename = key.toString();
		     if ( filename.endsWith(".xml") == false )
			 return;
		    
		    String content = new String( value.getBytes(), "UTF-8" );

		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse( new InputSource( new StringReader(content) ) );

			doc.getDocumentElement().normalize();
		  
			NodeList nList = doc.getElementsByTagName("text");
		   	
		   	StringBuilder page = new StringBuilder(1024);

			for (int temp = 0; temp < nList.getLength(); temp++) {
		 
				Node nNode = nList.item(temp);
		  
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					//get content between <p> and </p>
					NodeList givenNames = eElement.getElementsByTagName("p");
		 			for (int i = 0; i < givenNames.getLength(); i++) {
		 				page.append(givenNames.item(i).getTextContent());
					}
				}
			}

			content = page.toString();
		    content = content.replaceAll( "[^A-Za-z \n]", " " ).toLowerCase();

		    content = Stopwords.removeStemmedStopWords(content);

		    //StringTokenizer itr = new StringTokenizer(content);
		    englishStemmer stemmer = new englishStemmer();

		    String[] array = content.split("\\s+");

		    for(int i = 0; i < array.length; i++){
		    	stemmer.setCurrent(array[i]);
		    	String stemmedWord = stemmer.getCurrent();
		    	//decided whether it is stop words after stemmered. 
		    	if(Stopwords.isStemmedStopword(stemmedWord) == true){
		    		array[i] = null;
		    	}
		    }

		    StringBuffer midResult = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				if(array[i] != null){
			   		midResult.append( array[i] );
			   		midResult.append(" ");
				}
			}

			content = midResult.toString();
			StringTokenizer itr = new StringTokenizer(content);

		    int position = 1;
	        String biWord = "";


		    while (itr.hasMoreTokens()) {
          		stemmer.setCurrent(itr.nextToken());
          		if(stemmer.stem()){
          			if( biWord.equals("") == true ){
          				String stemStr1 = stemmer.getCurrent();

          				biWord = stemStr1;
          				word.set( biWord + "," +  key.toString().replace("newsML.xml","") );
	            		value1.set("1" + "," + Integer.toString(position));
	            		context.write(word, value1);
	            		position = position + 1;

          			}
          			else {
          				String stemStr = stemmer.getCurrent();
	            		word.set( stemStr + "," +  key.toString().replace("newsML.xml","") );
	            		value1.set("1" + "," + Integer.toString(position));
	            		context.write(word, value1);

	            		biWord += "-" + stemStr;
	            		word2.set( biWord + "," +  key.toString().replace("newsML.xml","") );
	            		value2.set("1" + "," + Integer.toString(position));
	            		context.write(word2, value2);

	            		biWord = stemStr;
	            		position = position + 1;
	            	}
          		}
        	}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
