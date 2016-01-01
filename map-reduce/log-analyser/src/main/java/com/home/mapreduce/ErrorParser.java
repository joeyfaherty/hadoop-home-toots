package com.home.mapreduce;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ErrorParser {

	// Mapper
	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, LongWritable> {

		private final static String SEARCH_STRING = "ERROR";
		private final static LongWritable one = new LongWritable(1);
		private Text word = new Text();

		/**
		 * mapper outputs the current line of the file being processed if
		 * the line being processed contains your search string
		 */
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			String [] lines = value.toString().split("\\r?\\n");
			for (String string : lines) {
				if (string.contains(SEARCH_STRING)) {
					word.set(string);
					context.write(word, one);
				}
			}
		}
	}
	/**
	 * Reducer adds a timestamp to each record.
	 * @author joeyfaherty
	 *
	 */
	// Reducer
	public static class IntSumReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
		
		private LongWritable result = new LongWritable();

		public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
			
			long timestamp = System.currentTimeMillis();

			result.set(timestamp);
			context.write(key, result);
		}
	}

	// Driver
	public static void main(String[] args) throws Exception {
		
		if (args.length != 2) {
			System.err.println("Usage: [input] [output]");
			return;
		}

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "log-analyzer");
		job.setJarByClass(ErrorParser.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}