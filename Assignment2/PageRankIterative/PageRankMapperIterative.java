import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PageRankMapperIterative extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		/*
		 * TODO implement
		 */

		String line = value.toString();
		String data[] = line.trim().split("\\s+");
		for (int i = 0; i < data.length - 2; i++) {
			double temp = Double.parseDouble(data[data.length - 1].trim())/ (data.length - 2);
			context.write(new Text(data[i + 1].trim()),new Text(String.valueOf(temp)));
			
			// try the below line instead of for loop
			context.write(new Text(data[0]), new Text(data[i + 1]));
		}

//		for (int i = 0; i < data.length - 2; i++)
	//		context.write(new Text(data[0]), new Text(data[i + 1]));
	}

}
