
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
	private static final String SECONDOUTPUT_PATH = "./secondOutput";

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
		FileSystem fs = FileSystem.get(conf2);
		fs.delete( new Path(FIRSTOUTPUT_PATH + "/part-r-00000.crc"), true);
		job2.setJobName("Hadoop job");

		job2.setJarByClass(Driver.class);
		job2.setMapperClass(MyUniMapper2.class);
		job2.setReducerClass(MyUniReducer2.class);
		 
		job2.setOutputKeyClass(Text.class);
		job2.setOutputValueClass(Text.class);
		 
		TextInputFormat.addInputPath(job2, new Path(FIRSTOUTPUT_PATH + "/part-r-00000"));
		TextOutputFormat.setOutputPath(job2, new Path(SECONDOUTPUT_PATH));
		
		return job2.waitForCompletion(true) ? 0 : 1;	
	}
	public static void main(String[] args) throws Exception {
  		ToolRunner.run(new Configuration(), new Driver(), args);
	}
}
