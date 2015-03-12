
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.io.NullWritable;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.io.FileReader;
import java.util.Iterator;

public class Driver3{

	public static HashMap<String,Integer>  stoppedStemmedWordMap = new HashMap<String,Integer>();

	public static void main(String[] args) throws Exception{

	
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


		Configuration conf = new Configuration();
		Job job = new Job(conf);
		job.setJobName("Hadoop job");
		job.setJarByClass(Driver3.class);

		job.setMapperClass(MyMapper3.class);
		job.setReducerClass(MyReducer3.class);
		 
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		 
		TextInputFormat.addInputPath(job, new Path("../UniP1/output/part-r-00000"));
		TextOutputFormat.setOutputPath(job, new Path("./queryIDFOutput"));
		job.waitForCompletion(true);
	}
}
