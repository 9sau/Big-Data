import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankReducerIterative extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		/*
		 * TODO implement
		 */

		double sum = 0;
		String str = "";
		for (Text val : values) {
			if (!val.toString().matches("[a-zA-Z]+") && !val.toString().isEmpty()) {
				double temp = Double.parseDouble(val.toString());
				sum += temp;
			} else
				str += val.toString() + " ";
		}
		context.write(key, new Text(str + " " + String.valueOf(sum)));

	}
}