import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class PageRankDriverIterative extends Configured implements Tool {

	static int count = 0;
	static boolean success;
	public int run(String[] args) throws Exception {
		if (args.length != 2) {
			System.err
					.println("Usage: PageRankDriverIterative <input path> <outputpath>");
			System.exit(-1);
		}
		
		do {
			Job job = Job.getInstance(getConf(), " PageRank Iterative ");
			job.setJarByClass(PageRankDriverIterative.class);
			job.setJobName("PageRankIterative");
			job.setMapperClass(PageRankMapperIterative.class);
			job.setReducerClass(PageRankReducerIterative.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);

			if (count == 0) {
				FileInputFormat.addInputPath(job, new Path(args[0]));
				FileOutputFormat.setOutputPath(job, new Path(args[1] + count));
			}

			else {
				FileInputFormat.addInputPath(job, new Path(args[1]+(count-1)));
				FileOutputFormat.setOutputPath(job, new Path(args[1] + count));

			}
			System.out.println("Iteration : " + (count+1));
			count++;
			success = job.waitForCompletion(true);

		}

		while (count < 3);
		return success ? 0 : 1;

	}

		

	public static void main(String[] args) throws Exception {
		PageRankDriverIterative driver = new PageRankDriverIterative();
		int exitCode = ToolRunner.run(driver, args);
		System.exit(exitCode);
	}
}
