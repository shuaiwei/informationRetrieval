import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.conf.Configuration;

  public  class MyReducer2
         extends Reducer<Text,Text,Text,Text> {

    private Text result = new Text();
    public HashMap<String,String>  docMap = Driver2.docMap;
    public  HashMap<String,Integer>  stoppedStemmedWordMap = Driver2.stoppedStemmedWordMap;

    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      //key : term,collectionFrequency,docFrequency
      String[] docIDArry = key.toString().split(",");
      String docID = docIDArry[0];
      //query map
     
      String object1 = docMap.get(docID);

      if (object1 != null) {
        key.set(docID);
        result.set(docIDArry[1]);
        context.write(key, result);

      }

  }
}
