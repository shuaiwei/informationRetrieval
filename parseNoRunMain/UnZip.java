import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.ArrayList;

/**
 * 
 * 
 * @author wei6
 *
 */
public class UnZip {
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String downLoadDirName, ArrayList<String> zipFiles, String unZipDirName) throws IOException {
        String downLoadDir = "./" + downLoadDirName + "/";
        String unZipDir = "./" + unZipDirName + "/";
        File theUnZipDir = new File(unZipDirName);
        // if the directory does not exist, create it
        if (!theUnZipDir.exists()) {
            boolean result = false;

            try{
                theUnZipDir.mkdir();
                result = true;
             } catch(SecurityException se){
                //handle it
             }        
             if(result) {    
               System.out.println("DIR Unzip created in current Dir");  
             }
        }


        for(int i = 0; i < zipFiles.size(); i++){
        //for(int i = 0; i < 2; i++){

            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(downLoadDir+zipFiles.get(i)));
            ZipEntry entry = zipIn.getNextEntry();
            // iterates over entries in the zip file
            while (entry != null) {
                String filePath = unZipDir + entry.getName();
                if (!entry.isDirectory()) {
                    // if the entry is a file, extracts it
                    extractFile(zipIn, filePath);
                } else {
                    // if the entry is a directory, make the directory
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
        }
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
}