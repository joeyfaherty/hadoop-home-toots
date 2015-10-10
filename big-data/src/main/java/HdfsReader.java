import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HdfsReader {
	
	//TODO: Javadocs, list recursively all subdirectories and details.
	
	public static void main(String[] args) throws IOException {
		
		//set configuration object to point at remote hdfs instance
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://127.0.0.1:8020/");
		
        FileSystem fs = FileSystem.get(conf);
        //fs.createNewFile(new Path("/user/root/test"));

        /**
         * Iterate through hdfs root and list directories and size
         */
        FileStatus[] status = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : status) {
        	Path dirPath = fileStatus.getPath();
        	ContentSummary summary = fs.getContentSummary(dirPath);
        	
        	System.out.println(fileStatus.getPath());
        	System.out.println(summary.getSpaceConsumed() + " Bytes");
        	System.out.println("Number of subdirectories: " + summary.getDirectoryCount());
        	System.out.println("-----");
		}
	}
	
}
