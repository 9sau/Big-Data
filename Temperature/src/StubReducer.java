import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StubReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {

  @Override
  public void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {

    /*
     * TODO implement
     */

	  int maxValue = Integer.MIN_VALUE;
	  for (IntWritable value : values) {
	  maxValue = Math.max(maxValue, value.get());
	  }
	  
	  context.write(key, new DoubleWritable(maxValue));

  }
}