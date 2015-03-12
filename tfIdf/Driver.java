
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

public class Driver extends Configured implements Tool {

	//private static final String OUTPUT_PATH = "./intermediate_output";
	public static HashMap<String,Integer>  stoppedStemmedWordMap = new HashMap<String,Integer>();

	public int run(String[] args) throws Exception{


        Configuration conf2 = new Configuration();

  //   	BufferedReader in1 = new BufferedReader(new FileReader("./typeInt"));
		// String type = in1.readLine();
		// in1.close();
		BufferedReader in2 = new BufferedReader(new FileReader("./term"));
		String term = in2.readLine();
		in2.close();
		term = term.toLowerCase();
		term = Stopwords.removeStopWords(term);
      	//can not have System.out.println
		String[] multiTerm = term.split("\\s+");
		englishStemmer stemmer2 = new englishStemmer();
		for(int i = 0; i < multiTerm.length; i++){
			stemmer2.setCurrent(multiTerm[i]);
			stemmer2.stem();
			String stoppedStemmedWord = stemmer2.getCurrent();
			Integer value = stoppedStemmedWordMap.get(stoppedStemmedWord);
			if (value != null) {
			  stoppedStemmedWordMap.put(stoppedStemmedWord, stoppedStemmedWordMap.get(stoppedStemmedWord) + 1);
			}
			else{
			  stoppedStemmedWordMap.put(stoppedStemmedWord,1);
			}
		}

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
		job2.setJarByClass(Driver.class);
		job2.setMapperClass(MyMapper.class);
		job2.setReducerClass(MyReducer.class);

		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		//if(type.equals("1") == true){ 
		TextInputFormat.addInputPath(job2, new Path("../UniP1/output/part-r-00000"));
		//}
		// else {
		// 	TextInputFormat.addInputPath(job2, new Path("./biSearch"));
		// }
		TextOutputFormat.setOutputPath(job2, new Path("./output"));

		return job2.waitForCompletion(true) ? 0 : 1;

	}

	public static void main(String[] args) throws Exception {
  		ToolRunner.run(new Configuration(), new Driver(), args);
	}
}
