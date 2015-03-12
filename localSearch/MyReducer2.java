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
    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      String[] keyNumName = key.toString().split(",");
      String keyName = keyNumName[0];

      Configuration conf = context.getConfiguration();
      String term = conf.get("term");
      String[] multiTerm = term.split("\\s+");
      String result = "";

      if(multiTerm.length == 2){
        englishStemmer stemmer1 = new englishStemmer();
        stemmer1.setCurrent(multiTerm[0]);
        stemmer1.stem();
        result += stemmer1.getCurrent() + "-";
        stemmer1.setCurrent(multiTerm[1]);
        stemmer1.stem();

        result += stemmer1.getCurrent();

      }
      if(multiTerm.length == 1){

        englishStemmer stemmer2 = new englishStemmer();
        stemmer2.setCurrent(multiTerm[0]);
        stemmer2.stem();
        result += stemmer2.getCurrent();

      }

      //term = term.replaceAll("\\s+","-");
      if( keyName.equals(result) == true ) {

        for (Text val : values) {
          System.out.println(val.toString());
          context.write(key, val);
        }
             
      }
      
    }

  
  }
