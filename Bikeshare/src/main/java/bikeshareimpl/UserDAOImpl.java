package bikeshareimpl;

import java.util.List;



import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import resources.User;
import util.MongodbConnection;


public class UserDAOImpl {

	private MongoTemplate mongoTemplate = MongodbConnection.getConnection();
	
	public void saveObject(User user) {
		mongoTemplate.insert(user);
	}

	public User getObject(String id) {
		return mongoTemplate.findOne(new Query((Criteria.where("_id").is(Integer.parseInt(id)))), User.class);
	}
	
	public User getUserBasedOnEmail(String email) {
		return mongoTemplate.findOne(new Query((Criteria.where("email").is(email.toString()))), User.class);
	}

	public List<User> getAllObjects() {
		 return mongoTemplate.findAll(User.class);
	}

	public void removeObject(String id) {
		mongoTemplate.remove(new Query((Criteria.where("_id").is(id))), User.class);
		
	}

	public void updateObject(User user) {
		Query query = new Query((Criteria.where("_id").is(user.getUser_id())));
		mongoTemplate.updateFirst(query, Update.update("email", user.getEmail()),"User");  
		mongoTemplate.updateFirst(query, Update.update("phone", user.getPhone()),"User");  
		mongoTemplate.updateFirst(query, Update.update("address", user.getAddress()),"User");  
		mongoTemplate.updateFirst(query, Update.update("zipcode", user.getZipcode()),"User");  
		mongoTemplate.updateFirst(query, Update.update("name", user.getName()),"User"); 
		mongoTemplate.updateFirst(query, Update.update("credits_earned", user.getCredits_earned()),"User");
		
	}

}
