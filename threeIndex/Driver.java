
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.io.NullWritable;

public class Driver{
	public static void main(String[] args) throws Exception{
		
		Configuration conf = new Configuration();
		Job job = new Job(conf);
		job.setJobName("Hadoop job");
		job.setJarByClass(Driver.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		job.setCombinerClass(MyCombiner.class);

		job.setInputFormatClass(ZipFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		 
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		 
		ZipFileInputFormat.setInputPaths(job, new Path("./data/*.zip"));
		TextOutputFormat.setOutputPath(job, new Path("./output"));

		String uniWord="uniWord";
		String biWord="biWord";
		String position="position";

		MultipleOutputs.addNamedOutput(job, uniWord, TextOutputFormat.class, Text.class, Text.class);
   		MultipleOutputs.addNamedOutput(job, biWord, TextOutputFormat.class, Text.class, Text.class); 
		MultipleOutputs.addNamedOutput(job, position, TextOutputFormat.class, Text.class, Text.class); 

		job.waitForCompletion(true);
	}
}
