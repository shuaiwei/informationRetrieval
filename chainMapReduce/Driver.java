
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


public class Driver extends Configured implements Tool {

	private static final String OUTPUT_PATH = "./intermediate_output";

	public int run(String[] args) throws Exception{
		/*
   		 * Job 1
   		 */
		Configuration conf1 = new Configuration();
		Job job1 = new Job(conf1);
		job1.setJobName("Hadoop job1");
		job1.setJarByClass(Driver.class);
		job1.setMapperClass(MyMapper1.class);
		job1.setReducerClass(MyReducer1.class);

		job1.setInputFormatClass(ZipFileInputFormat.class);
		job1.setOutputFormatClass(TextOutputFormat.class);
		 
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);
		 
		ZipFileInputFormat.setInputPaths(job1, new Path("./data/*.zip"));
		TextOutputFormat.setOutputPath(job1, new Path(OUTPUT_PATH));

	    String firstOutput ="firstMapReduceOutput";


		MultipleOutputs.addNamedOutput(job1, firstOutput, TextOutputFormat.class, Text.class, Text.class);
   		
		job1.waitForCompletion(true);

		 /*
   		  * Job 2
          */
		

		Configuration conf2 = new Configuration();

		

		Job job2 = new Job(conf2);
		FileSystem fs = FileSystem.get(conf2);
		fs.delete( new Path(OUTPUT_PATH + "/." + firstOutput + "-r-00000.crc"), true);
		job2.setJobName("Hadoop job2");
		job2.setJarByClass(Driver.class);
		job2.setMapperClass(MyMapper2.class);
		job2.setReducerClass(MyReducer2.class);

		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		 
		TextInputFormat.addInputPath(job2, new Path(OUTPUT_PATH + "/" + firstOutput + "-r-00000"));
		
		TextOutputFormat.setOutputPath(job2, new Path("./output"));

		String uniWord="uniWord";
		String biWord="biWord";
		String position="position";

		MultipleOutputs.addNamedOutput(job2, uniWord, TextOutputFormat.class, Text.class, Text.class);
   		MultipleOutputs.addNamedOutput(job2, biWord, TextOutputFormat.class, Text.class, Text.class); 
		MultipleOutputs.addNamedOutput(job2, position, TextOutputFormat.class, Text.class, Text.class); 

  		return job2.waitForCompletion(true) ? 0 : 1;
	}
	public static void main(String[] args) throws Exception {
  		ToolRunner.run(new Configuration(), new Driver(), args);
	}
}
