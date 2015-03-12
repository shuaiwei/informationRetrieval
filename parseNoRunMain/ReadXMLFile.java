import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class ReadXMLFile {
 
  public static void main(String argv[]) {

    try {
 
		File fXmlFile = new File("./file.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
	 
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

		String[] parts = page.toString().toLowerCase().split("[\\W]");
		
		englishStemmer stemmer = new englishStemmer();
		
		String oneString = "";

	    for(String s : parts) {
	       if(s != null && s.length() > 0) {
	       		stemmer.setCurrent(s);
	       	    //System.out.println( s);
	       	    if(stemmer.stem()){
	          		oneString += stemmer.getCurrent() + " ";
	       	    }
	       }
	    }
	    System.out.println(oneString);
	
    } catch (Exception e) {
		e.printStackTrace();
    }
  }
 
}