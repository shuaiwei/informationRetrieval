import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.conf.Configuration;

  public  class MyReducer3
         extends Reducer<Text,Text,Text,Text> {

    private Text result = new Text();
    public  HashMap<String,Integer>  stoppedStemmedWordMap = Driver3.stoppedStemmedWordMap;

    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      //key : term,collectionFrequency,docFrequency
      String[] termTFDocFArry = key.toString().split(",");
      String term = termTFDocFArry[0];
      //query map
     
      Integer object1 = stoppedStemmedWordMap.get(term);

      if (object1 != null) {
        key.set(term);
        result.set(termTFDocFArry[2]);
        context.write(key, result);

      }

  }
}
