import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.io.NullWritable;

  public  class MyUniCombiner
         extends Reducer<Text,Text,Text,Text> {
  
    private Text result = new Text();
    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int docSum = 0;

      String position = "\\";
      for (Text val : values) {
        //split into two words;
        String [] numPos = val.toString().split(",");
        docSum +=  Integer.parseInt ( numPos[0] );
        position += numPos[1] + "\\";

      }
      String key1 = key.toString();
      //docID,docFreq:<position>
      result.set(key.toString().replaceAll("[A-Za-z,]", "") + "," +  Integer.toString(docSum)
        + ":" + "<" + position + ">");
      
      //term
      String sKey = key1.replaceAll("[0-9,]","");

      key.set(sKey);
      context.write(key, result);
      
    }

  }
