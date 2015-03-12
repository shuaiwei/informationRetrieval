import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;

  public  class MyReducer2
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

      String value = "< ";  //for position
      String valueUni = " ";
      String valueBi = " ";

      String sKey = key.toString();
      String[] uniOrBi = sKey.split("-");

      for (Text val : values) {
        String [] docIdNumPos = val.toString().split(":");

        String docIdNumStr = docIdNumPos[0];

        String [] docIdNum = docIdNumStr.split(",");

        collectionSum +=  Integer.parseInt ( docIdNum[1] );

        value += val.toString() + "; ";  
        valueUni += docIdNumStr + "; ";
        valueBi += docIdNumStr + "; ";
      
      }

      value += ">";

      sKey = key.toString().replaceAll("-", " ");
      
      sKey += "," + Integer.toString(collectionSum);
      key.set(sKey);

      result.set(value);

      Text resultUni = new Text();
      resultUni.set(valueUni);

      Text resultBi = new Text();
      resultBi.set(valueBi);

      String uniWord="uniWord";
      String biWord="biWord";
      String position="position";


      if(uniOrBi.length == 1){
          mos.write(uniWord, key, resultUni);
          mos.write(position, key, result);
      }
      else 
      mos.write(biWord, key, resultBi);
      
    }

    protected void cleanup(Context context) throws IOException, InterruptedException {
        mos.close();
    }
  }
