import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;

  public  class MyUniReducer3
         extends Reducer<Text,Text,Text,Text> {

    private Text value1 = new Text();
    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int df = 0;

      String value = "<";

      for (Text val : values) {

        String termTfDf = val.toString();
        value += termTfDf + ";";
          
      }

      value +=  ">";

      value1.set(value);
      
      context.write(key, value1);
    }
  }
