import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.conf.Configuration;

  public  class MyReducer
         extends Reducer<Text,Text,Text,Text> {

    private Text result = new Text();
    public HashMap<String,Integer>  stoppedStemmedWordMap = Driver.stoppedStemmedWordMap;

    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      //key : term,collectionFrequency,docFrequency
      String[] keyNumName = key.toString().split(",");
      String keyName = keyNumName[0];
      //query map
     
      Integer value = stoppedStemmedWordMap.get(keyName);
    
      if (value != null) {

        for (Text val : values) {
          String valStr = val.toString();
          valStr = valStr.substring(1,valStr.length()-2);
          String[] docTF= valStr.split(";");
          
          for(int i = 0; i < docTF.length; i++){
           
            String[] docIDTF = docTF[i].split(",");
            String docID = docIDTF[0];
            key.set(docID);
            result.set(keyName + "," + docIDTF[1] + "," + keyNumName[2]);
            context.write(key, result);
          }

        }

      }
    }

  
  }
