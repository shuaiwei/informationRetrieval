import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;

  public  class MyUniReducer
         extends Reducer<Text,Text,Text,Text> {

    private Text result = new Text();
    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int collectionSum = 0;
      int docSum = 0;
      String value = "<";
      for (Text val : values) {

        String [] docIdNumPos = val.toString().split(":");
        String docIdNumStr = docIdNumPos[0];
        String [] docIdNum = docIdNumStr.split(",");
        collectionSum +=  Integer.parseInt ( docIdNum[1] );
        docSum += 1;  // doc frequency
        value += docIdNumStr + ";";
          
      }

      value += ">";

      String sKey = key.toString();
      sKey += "," + Integer.toString(collectionSum) + "," + Integer.toString(docSum);
      key.set(sKey);

      result.set(value);
      
      context.write(key, result);
    }
  }
