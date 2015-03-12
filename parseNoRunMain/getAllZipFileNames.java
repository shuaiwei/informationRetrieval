import java.net.URL;
import java.io.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class getAllZipFileNames{
  
     public static ArrayList<String> getNames(String urlString){
        ArrayList<String> zipFiles = new ArrayList<String>();
        try{
            URL url = new URL(urlString);
            InputStream is = null;
            try {

                is = url.openStream();
                byte[] buffer = new byte[1024];
                int bytesRead = -1;
                StringBuilder page = new StringBuilder(1024);
                while ((bytesRead = is.read(buffer)) != -1) {
                    page.append(new String(buffer, 0, bytesRead));
                }

                int startIndex = 0;
                while(true) {
                    int rightIndex = page.indexOf(".zip", startIndex) + 4;
                    int leftIndex = rightIndex - 12;
                    zipFiles.add(page.substring(leftIndex,rightIndex));
                    startIndex = rightIndex;
                    if(page.substring(leftIndex,rightIndex).compareTo("19970819.zip") ==0 )
                        break;
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
        catch(MalformedURLException ex){
            //do exception handling here
        }
        return zipFiles;
    }
}