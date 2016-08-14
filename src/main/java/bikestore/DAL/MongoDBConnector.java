package bikestore.DAL;

import org.apache.log4j.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MongoDBConnector {
	private static final Logger logger = Logger
			.getLogger(MongoDBConnector.class.getName());

	public static final String DB_NAME = "db_bikestore";

	private static final String host = "localhost";
	private static final int port = 27017;

	private static MongoClient mongo = null;

	public static MongoClient getMongo() {
		if (mongo == null) {
			try {
				// mongo = new MongoClient("127.0.0.1", 27017);
				// Mongo mongo = new Mongo(new
				// MongoURI("mongodb://localhost/mjormIsFun"));
				// mongodb://db1.example.net,db2.example.net:2500/?replicaSet=test
				// mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]

				mongo = new MongoClient(host, port);
				logger.debug("New Mongo created with [" + host + "] and ["
						+ port + "]");

			} catch (MongoException e) {
				logger.error(e.getMessage());
			}
		}
		return mongo;
	}
}
