import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class ParseXMLFile {

 	static ArrayList<String> listFilesForFolder( File folder) {
	    ArrayList<String> ret = new ArrayList<String>();
	    for ( File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	if( (fileEntry.getName().matches("(.*).xml") ) ){
	            	ret.add( fileEntry.getName() );
	        	}
	        }
	    }
	    return ret;
	}

	static void stemAndWrite(StringBuilder page, String fileName){
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
	    //System.out.println(oneString);
	    String parseDirName = "ParsedFiles";
	    File theDir = new File(parseDirName);
        String parseDir = "./" + parseDirName + "/";
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try{
                theDir.mkdir();
                result = true;
             } catch(SecurityException se){
                //handle it
             }        
             if(result) {    
               System.out.println("DIR Download created in current Dir");  
             }
        }
     
	    try {
			 
			File file = new File(parseDir + fileName.replace(".xml",""));
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();

			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(oneString);
			bw.close();
  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void parse(File folder, String file){
		try{
			File fXmlFile = new File(folder + "/" + file);
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

			stemAndWrite(page,file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  	public static void parseXmlFile(String unZipDirName) {
  		
  		File folder = new File("./" + unZipDirName);
  		ArrayList<String> fileNames = listFilesForFolder(folder);
		
 		for(String file: fileNames){	
			parse(folder, file);
	    } 
		 
	}
	 
}