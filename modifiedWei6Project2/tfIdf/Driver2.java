
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

public class Driver2{

	public static HashMap<String,String>  docMap = new HashMap<String,String>();
	public static HashMap<String,Integer>  stoppedStemmedWordMap = new HashMap<String,Integer>();

	public static void main(String[] args) throws Exception{

		BufferedReader in = new BufferedReader(new FileReader("./output/part-r-00000"));
		String line;
		
		while((line = in.readLine()) != null){
		    //System.out.println(line);
	        String[] docIDTermTF = line.split("\\s+");
	        String docID = docIDTermTF[0];
            String value1 = docMap.get(docID);
            if (value1 != null) {
              docMap.put(docID, docMap.get(docID) + docIDTermTF[1] + ";");
            }
            else{
              docMap.put(docID, docIDTermTF[1] + ";");
            }

		}
		in.close();
		
		// Iterator it = docMap.entrySet().iterator();
  //       while (it.hasNext()) {
  //         Map.Entry pair = (Map.Entry)it.next();
  //         System.out.println(pair.getKey() + " = " + pair.getValue());
  //         it.remove(); // avoids a ConcurrentModificationException
  //       }
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
		job.setJarByClass(Driver2.class);

		job.setMapperClass(MyMapper2.class);
		job.setReducerClass(MyReducer2.class);
		 
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		 
		TextInputFormat.addInputPath(job, new Path("../DocP2/secondOutput/part-r-00000"));
		TextOutputFormat.setOutputPath(job, new Path("./moldOutput"));
		job.waitForCompletion(true);
	}
}
