import java.io.IOException;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;


public class test{
	public static void main(String[] args) throws IOException {
	         FileSystem hdfs =FileSystem.get(new Configuration());
	         Path homeDir=hdfs.getHomeDirectory();
	         //Print the home directory
	         System.out.println("Home folder -" +homeDir); 

	}
}