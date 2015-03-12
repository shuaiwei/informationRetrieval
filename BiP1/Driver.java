
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

		// job.setMapperClass(MyUniMapper.class);
		// job.setReducerClass(MyUniReducer.class);
		// job.setCombinerClass(MyUniCombiner.class);

		job.setMapperClass(MyBiMapper.class);
		job.setReducerClass(MyBiReducer.class);
		job.setCombinerClass(MyBiCombiner.class);


		// job.setMapperClass(MyPosMapper.class);
		// job.setReducerClass(MyPosReducer.class);
		// job.setCombinerClass(MyPosCombiner.class);

		job.setInputFormatClass(ZipFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		 
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		 
		ZipFileInputFormat.setInputPaths(job, new Path("./data/*.zip"));
		TextOutputFormat.setOutputPath(job, new Path("./output"));

		job.waitForCompletion(true);
	}
}
