package piggyhome;

import java.io.IOException;
import java.util.Properties;

import org.apache.pig.ExecType;
import org.apache.pig.PigServer;

public class PigTest {
	
//	in = LOAD '/Users/joeyfaherty/git/hadoop-home-toots/piggyhome/src/test/resources/input/lowerCasetest.txt' 
//			USING PigStorage(',') AS (blah: chararray, id:long);
//			DUMP in;
	
	public static void main(String[] args) {

	    try {
	        Properties props = new Properties();
	        props.setProperty("fs.default.name", "hdfs://127.0.0.1:8020");
	        PigServer pigServer = new PigServer(ExecType.MAPREDUCE,props);

	        runIdQuery(pigServer, "/Users/joeyfaherty/git/hadoop-home-toots/piggyhome/src/test/resources/input/lowerCasetest.txt");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}

	public static void runIdQuery(PigServer pigServer, String inputFile) throws IOException {

	    pigServer.registerQuery("A = load '" + inputFile + "' using PigStorage(',');");
	    pigServer.registerQuery("B = foreach A generate $1 as id;");
	    pigServer.store("B", "id.out");
	}

}
