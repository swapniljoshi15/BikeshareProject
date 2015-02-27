package util;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.MongoClientURI;

public class MongodbConnection {

	public static MongoTemplate getConnection() {
		MongoClientURI uri=null;
		MongoClient mongoclient=null;
		MongoTemplate mongoConnection=null;
		try {
			uri = new MongoClientURI("mongodb://bikeshare:sithu13@ds051170.mongolab.com:51170/bikeshare");
			mongoclient = new MongoClient(uri);
			mongoConnection = new MongoTemplate(mongoclient,"bikeshare");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		return mongoConnection;
	}

}
