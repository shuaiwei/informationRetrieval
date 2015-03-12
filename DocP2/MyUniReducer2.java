import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;

  public  class MyUniReducer2
         extends Reducer<Text,Text,Text,Text> {
    MultipleOutputs<Text, Text> mos;

    public void setup(Context context) {
      mos = new MultipleOutputs<Text, Text>(context);
    }      

    private Text result = new Text();
    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int collectionSum = 0;
      int docSum = 0;
      String value = "<";
      double mold = 0.0;
      for (Text val : values) {

        String [] docIdNumPos = val.toString().split(":");
        String docIdNumStr = docIdNumPos[0];
        String [] docIdNum = docIdNumStr.split(",");
        collectionSum +=  Integer.parseInt ( docIdNum[1] );
        docSum += 1;  // doc frequency
        value += docIdNumStr + ";";
        //compute mold
        mold += Integer.parseInt ( docIdNum[1] ) * Integer.parseInt ( docIdNum[1] );
      }
      mold = Math.sqrt(mold);

      value += ">";

      String sKey = key.toString();
      // sKey += "," + Integer.toString(collectionSum) + "," + 
      //         Integer.toString(docSum) + "," + String.valueOf(mold);
      sKey += "," + String.valueOf(mold);
      key.set(sKey);

      result.set(value);
      
      context.write(key, result);
    }
  }
