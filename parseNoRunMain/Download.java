import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A utility that downloads a file from a URL.
 * @author wei6
 *
 */
public class Download {
    private static final int BUFFER_SIZE = 4096;
 
    /**
     * Downloads a file from a URL
     * @param webDir 
     * @param zipFiles 
     * @param saveDir 
     * @throws IOException
     */
    public static void downloadFile(String webDir, ArrayList<String> zipfiles, String downLoadDirName)
            throws IOException {

         File theDir = new File(downLoadDirName);
         String downLoadDir = "./" + downLoadDirName + "/";
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

        for(int i = 0; i < zipfiles.size(); i++){
            URL url = new URL(webDir + zipfiles.get(i));
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();
     
            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = zipfiles.get(i);
                System.out.println("fileName = " + fileName);
     
                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = downLoadDir + fileName;
                System.out.println(saveFilePath);
                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);
     
                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
     
                outputStream.close();
                inputStream.close();
     
                System.out.println("File downloaded");
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }
            //httpConn.disconnect();
        }
    }
}