
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


public class Driver extends Configured implements Tool {

	//private static final String OUTPUT_PATH = "./intermediate_output";

	public int run(String[] args) throws Exception{

		 Scanner scanner = new Scanner(System.in);
        int typeInt;
        String term;
        while(true){
            System.out.println("Enter search type, 1:uniword, 2:biword");
            System.out.format(">");

            while(true){
            	try{
	                typeInt = scanner.nextInt();
	                scanner.nextLine(); 
	                if(typeInt == 1){
	                    System.out.println("You have select uniword search way!");
	                    break;
	                }
	                else if (typeInt == 2){
	                    System.out.println("You have select biword search way!");
	                    break;
	                }
	                else{
	                    System.out.println("Input error");
	                    System.out.println("Enter search type, 1:uniword, 2:biword");
	                    System.out.format(">");
	                }
	            }catch (InputMismatchException exception) { 
	                System.out.println("Integers only, please.");
	                System.out.println("Enter search type, 1:uniword, 2:biword");
	                System.out.format(">"); 
	                scanner.nextLine();
            	} 

            }

            if(typeInt == 1){
                while(true){
                    System.out.println("Now enter one word:");
                    System.out.format(">");
                    term = scanner.nextLine();
                    String [] termSplit = term.split("\\s+");
                    if(termSplit.length != 1  ){
                        System.out.println("Input error, please enter just one word");     
                    }
                    else{
                        System.out.format("You have entered:%s\n",term);

                        Configuration conf2 = new Configuration();
						conf2.set("term", term);

						Job job2 = new Job(conf2);
						FileSystem fs = FileSystem.get(conf2);

						fs.delete( new Path("./.*.crc"), true);

						fs.copyFromLocalFile(new Path("../UniP1/output/part-r-00000"), 
							new Path("./uniSearch"));

						// fs.copyFromLocalFile(new Path("../BiP1/output/part-r-00000"), 
						// 	new Path("./biSearch"));
						job2.setJobName("Hadoop job2");
						job2.setJarByClass(Driver.class);
						job2.setMapperClass(MyMapper2.class);
						job2.setReducerClass(MyReducer2.class);

						job2.setOutputKeyClass(Text.class);
						job2.setOutputValueClass(Text.class);
						 
						TextInputFormat.addInputPath(job2, new Path("./uniSearch"));
						
						TextOutputFormat.setOutputPath(job2, new Path("./output"));

						job2.waitForCompletion(true);

						fs.delete( new Path("./output"), true);

                        break;
                    }
                }
            }
            if(typeInt == 2){
                while(true){
                    System.out.println("Now enter two words:");
                    System.out.format(">");
                    term = scanner.nextLine();
                    String [] termSplit = term.split("\\s+");
                    if(termSplit.length != 2 ){
                        System.out.println("Input error, please enter two words seperated by blank space");     
                    }
                    else{
                        System.out.format("You have entered:%s\n",term);
                           Configuration conf2 = new Configuration();
						conf2.set("term", term);

						Job job2 = new Job(conf2);
						FileSystem fs = FileSystem.get(conf2);

						fs.delete( new Path("./.*.crc"), true);

						// fs.copyFromLocalFile(new Path("../UniP1/output/part-r-00000"), 
						// 	new Path("./uniSearch"));

						 fs.copyFromLocalFile(new Path("../BiP1/output/part-r-00000"), 
						 	new Path("./biSearch"));
						job2.setJobName("Hadoop job2");
						job2.setJarByClass(Driver.class);
						job2.setMapperClass(MyMapper2.class);
						job2.setReducerClass(MyReducer2.class);

						job2.setOutputKeyClass(Text.class);
						job2.setOutputValueClass(Text.class);
						 
						TextInputFormat.addInputPath(job2, new Path("./biSearch"));
						
						TextOutputFormat.setOutputPath(job2, new Path("./output"));

						job2.waitForCompletion(true);

						fs.delete( new Path("./output"), true);

                        break;     
                    }
                }
            }  
        }

	}
	public static void main(String[] args) throws Exception {
  		ToolRunner.run(new Configuration(), new Driver(), args);
	}
}
