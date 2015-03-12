import java.io.IOException;
import java.io.File;
import java.util.ArrayList;


public class ProjectOneMain{
	public static void main(String[] args){
		String webDir = "http://people.cs.clemson.edu/~luofeng/course/2015spring/881/Porject/Reuters/";
        
		ArrayList<String> zipFiles = getAllZipFileNames.getNames(webDir);
		String downLoadDirName = "Download";
       
        // try {
        //     Download.downloadFile(webDir, zipFiles, downLoadDirName);
        // } catch (IOException ex) {
        //     ex.printStackTrace();
        // }

        String unZipDirName = "Unzip";

        UnZip unzipper = new UnZip();
        try {
            unzipper.unzip(downLoadDirName, zipFiles, unZipDirName);
        } catch (Exception ex) {
            // some errors occurred
            ex.printStackTrace();
        }

        // ParseXMLFile parser = new ParseXMLFile();
        // parser.parseXmlFile(unZipDirName);


	}
}