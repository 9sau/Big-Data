import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCount extends Configured implements Tool {

	static List<Data> list = new ArrayList<>();
	static Data prev;
	static boolean isDec,isChicago,isHackathon,isJava;

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new WordCount(), args);
		System.exit(res);
	}

	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), " wordcount ");
		// Set the jar to use, based on the class in use.

		job.setJarByClass(this.getClass());
		// Set the input and output paths for your application. You store your
		// input files in HDFS, and then pass the input and output paths as
		// command-line arguments at run time.

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		// Set the map class and reduce class for the job. In this case, use the
		// Map and Reduce inner classes defined in this class.

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		return job.waitForCompletion(true) ? 0 : 1;

	}

	public static class Map extends
			Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(LongWritable offset, Text lineText, Context context)
				throws IOException, InterruptedException {
			String line = lineText.toString();

			for (int i = 0; i < line.length(); i++) {

				if (!Character.isAlphabetic(line.charAt(i))) {
					line = line.replaceAll(line.charAt(i) + "", " ");
				}
			}
			StringTokenizer tokenizer = new StringTokenizer(line);

			while (tokenizer.hasMoreTokens()) {
				String temp = tokenizer.nextToken().trim().toLowerCase();
				word.set(temp);
				
				switch(temp)
				{
					case "world":
						isDec = true;
						context.write(word, one);
						break;
					case "hackathon":
						isHackathon = true;
						context.write(word, one);
						break;
					case "chicago":
						isChicago = true;
						context.write(word, one);
						break;
					case "java":
						isJava = true;
						context.write(word, one);
						
				}
				
				
				if(!isDec)
					context.write(new Text("world"), new IntWritable(0));
				if(!isJava)
					context.write(new Text("java"), new IntWritable(0));
				if(!isChicago)
					context.write(new Text("chicago"), new IntWritable(0));
				if(!isHackathon)
					context.write(new Text("hackathon"), new IntWritable(0));
			}

			
		}
	}

	public static class Reduce extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		@Override
		public void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {

			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}
}
