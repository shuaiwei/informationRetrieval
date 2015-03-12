import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;

  public  class MyReducer1
         extends Reducer<Text,Text,Text,Text> {
      MultipleOutputs<Text, Text> mos;

    public void setup(Context context) {
        mos = new MultipleOutputs<Text, Text>(context);
    }      

  
    private Text result = new Text();
    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int docSum = 0;

      String position = "";
      for (Text val : values) {
        //split into two words;
        String [] numPos = val.toString().split(",");
        docSum +=  Integer.parseInt ( numPos[0] );
        position += numPos[1] + "\\";

      }
      //position = position.substring(0, position.length()-1);
      String key1 = key.toString();

      //docID,docFreq:<position>
      result.set(key.toString().replaceAll("[A-Za-z,-]","") + "," +  Integer.toString(docSum) + ":" + "<" + position + ">");
  
      //term all in []
      String sKey = key1.replaceAll("[0-9,]","");

      key.set(sKey);

      String firstOutput ="firstMapReduceOutput";
      mos.write(firstOutput, key, result);
      
    }

  }
