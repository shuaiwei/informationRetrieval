import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;
import java.util.ArrayList;
import java.util.List;

  public  class MyUniReducer2
         extends Reducer<Text,Text,Text,Text> {
    
    private Text word = new Text();
    private Text value1 = new Text();

    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {

      Iterator<Text> it = values.iterator();
      List<String> cache = new ArrayList<String>();

      for(Text val : values) {
        cache.add(val.toString());
      }
      
      int df = cache.size();

      for(int i=0; i < cache.size(); i++) {

        String[] docIDTf = cache.get(i).split(",");
        
        word.set(docIDTf[0]);

        value1.set(key.toString() + "," + docIDTf[1] + "," + Integer.toString(df));

        context.write(word, value1);  

      }
      
    }
  }
