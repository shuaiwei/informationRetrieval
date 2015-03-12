import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;

  public  class MyReducer
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

      String value = "< ";
      String valueUni = " ";
      for (Text val : values) {
          System.out.println(val.toString());

        String [] docIdNumPos = val.toString().split(":");

         System.out.println(docIdNumPos.length);
        String docIdNumStr = docIdNumPos[0];
        String docIdNumStrBi = docIdNumPos[0];

        String [] docIdNum = docIdNumStr.split(",");
        collectionSum +=  Integer.parseInt ( docIdNum[1] );
        value += val.toString() + "; ";  
        valueUni += docIdNumStrBi + " ";
      }

      value += ">";

      String sKey = key.toString();
      sKey += "," + Integer.toString(collectionSum);
      key.set(sKey);

      result.set(value);

      Text resultUni = new Text();
      resultUni.set(valueUni);

      String uniWord="uniWord";
      String biWord="biWord";
      String position="position";
      mos.write(uniWord, key, resultUni);
      mos.write(biWord, key, result);
      mos.write(position, key, resultUni);
    }

    protected void cleanup(Context context) throws IOException, InterruptedException {
        mos.close();
    }
  }
