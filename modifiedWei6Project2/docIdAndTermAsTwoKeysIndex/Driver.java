
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

	private static final String FIRSTOUTPUT_PATH = "./firstOutput";
	private static final String SECONDOUTPUT_PATH = "./termIndexOutput";
	private static final String THIRDDOUTPUT_PATH = "./docIndexOutput";

	public int run(String[] args) throws Exception{		
		Configuration conf = new Configuration();
		Job job = new Job(conf);
		job.setJobName("Hadoop job");
		job.setJarByClass(Driver.class);

		job.setMapperClass(MyUniMapper.class);
		job.setReducerClass(MyUniReducer.class);

		job.setInputFormatClass(ZipFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		 
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		 
		ZipFileInputFormat.setInputPaths(job, new Path("./data/*.zip"));
		TextOutputFormat.setOutputPath(job, new Path(FIRSTOUTPUT_PATH));

		job.waitForCompletion(true);

		Configuration conf2 = new Configuration();
		Job job2 = new Job(conf2);
		FileSystem fs2 = FileSystem.get(conf2);
		fs2.delete( new Path(FIRSTOUTPUT_PATH + "/.*.crc"), true);
		job2.setJobName("Hadoop job");

		job2.setJarByClass(Driver.class);
		job2.setMapperClass(MyUniMapper2.class);
		job2.setReducerClass(MyUniReducer2.class);
		 
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		 
		TextInputFormat.addInputPath(job2, new Path(FIRSTOUTPUT_PATH + "/part-r-00000"));
		TextOutputFormat.setOutputPath(job2, new Path(SECONDOUTPUT_PATH));
		
		//get the first index
		String termAsKeyIndex = "termAsKeyIndex";
		//String termAsKeyOutputFile = "termAsKeyOutputFile";

		//MultipleOutputs.addNamedOutput(job2, termAsKeyOutputFile, TextOutputFormat.class, Text.class, Text.class);
   		MultipleOutputs.addNamedOutput(job2, termAsKeyIndex, TextOutputFormat.class, Text.class, Text.class); 
		job2.waitForCompletion(true);

		Configuration conf3 = new Configuration();
		Job job3 = new Job(conf3);
		FileSystem fs3 = FileSystem.get(conf3);
		fs3.delete( new Path(SECONDOUTPUT_PATH + "/.*.crc"), true);
		job3.setJobName("Hadoop job");

		job3.setJarByClass(Driver.class);
		job3.setMapperClass(MyUniMapper3.class);
		job3.setReducerClass(MyUniReducer3.class);
		 
		job3.setOutputKeyClass(Text.class);
		job3.setOutputValueClass(Text.class);
		//do not 
		TextInputFormat.addInputPath(job3, new Path(SECONDOUTPUT_PATH + "/part-r-00000"));
		TextOutputFormat.setOutputPath(job3, new Path(THIRDDOUTPUT_PATH));

		return job3.waitForCompletion(true) ? 0 : 1;	
	}
	public static void main(String[] args) throws Exception {
  		ToolRunner.run(new Configuration(), new Driver(), args);
	}
}
