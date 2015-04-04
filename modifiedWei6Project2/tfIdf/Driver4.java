
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.fs.FileSystem;

import java.util.Scanner;
import java.util.InputMismatchException;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.HashMap;
import java.util.Map;

public class Driver4 extends Configured implements Tool {
	public static HashMap<String,Integer>  rankResultMap = new HashMap<String,Integer>();
	public int run(String[] args) throws Exception{

        Configuration conf2 = new Configuration();

        BufferedReader in = new BufferedReader(new FileReader("./rankResult.txt"));
		String line;
		
		while((line = in.readLine()) != null){
		    //System.out.println(line);
	        String[] docIDValue = line.split("\\s+");
	        String docID = docIDValue[0];

            Integer value1 = rankResultMap.get(docID);
            if (value1 != null) {
              rankResultMap.put(docID, -1); // no executing 
            }
            else{
              rankResultMap.put(docID, 1);
            }

		}
		in.close();

		Job job2 = new Job(conf2);
		FileSystem fs = FileSystem.get(conf2);

		fs.delete( new Path("./.*.crc"), true);
		// if(type.equals("1") == true){
		// 	fs.copyFromLocalFile(new Path("../UniP1/output/part-r-00000"), 
		// 		new Path("./uniSearch"));
		// }
		// else{
		// 	fs.copyFromLocalFile(new Path("../BiP1/output/part-r-00000"), 
		//  		new Path("./biSearch"));
		// }
		job2.setJobName("Hadoop job2");
		job2.setJarByClass(Driver4.class);
		job2.setMapperClass(MyMapper4.class);
		job2.setReducerClass(MyReducer4.class);

		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		//if(type.equals("1") == true){ 
		TextInputFormat.addInputPath(job2, new Path("../contentRetrieval/output/part-r-00000"));
		//}
		// else {
		// 	TextInputFormat.addInputPath(job2, new Path("./biSearch"));
		// }
		TextOutputFormat.setOutputPath(job2, new Path("./contentOutput"));

		return job2.waitForCompletion(true) ? 0 : 1;

	}

	public static void main(String[] args) throws Exception {
  		ToolRunner.run(new Configuration(), new Driver4(), args);
	}
}
