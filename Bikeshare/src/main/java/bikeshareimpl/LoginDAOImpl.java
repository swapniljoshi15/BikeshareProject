package bikeshareimpl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import resources.Login;
import resources.User;
import util.BikeShareUtil;
import util.MongodbConnection;

public class LoginDAOImpl{

	private MongoTemplate mongoTemplate = MongodbConnection.getConnection();
	
	public void saveObject(Login login) {
		mongoTemplate.insert(login);
	}

	public Login getObject(String id) {
		return mongoTemplate.findOne(new Query((Criteria.where("_id").is(Integer.parseInt(id)))), Login.class);
	}
	
	public Login getUserBasedOnUsername(String username) {
		return mongoTemplate.findOne(new Query((Criteria.where("username").is(username))), Login.class);
	}

	public List<Login> getAllObjects() {
		 return mongoTemplate.findAll(Login.class);
	}

	public void removeObject(String id) {
		mongoTemplate.remove(new Query((Criteria.where("_id").is(id))), User.class);
		
	}

	public void updateObject(Login login) {
		Query query = new Query((Criteria.where("_id").is(login.getUser_id())));
		//Updated by Viresh//mongoTemplate.updateFirst(query, Update.update("password", login.getPassword()),"login");
		mongoTemplate.updateFirst(query, Update.update("sessionId", login.getSessionId()),"login");
	}
	
	public Login getObject(String username, String password) {
		Criteria usernameQuery = new Criteria().where("username").is(username);
		Criteria passwordQuery = new Criteria().where("password").is(BikeShareUtil.passwordEncrypter(password));
		Criteria loginSuccess = new Criteria().andOperator(usernameQuery,passwordQuery);
		return mongoTemplate.findOne(new Query(loginSuccess), Login.class);
	}

	public Login getObjectOnSession(String username, String sessionid) {
		Criteria usernameQuery = new Criteria().where("username").is(username);
		Criteria passwordQuery = new Criteria().where("sessionId").is(sessionid);
		Criteria loginSuccess = new Criteria().andOperator(usernameQuery,passwordQuery);
		return mongoTemplate.findOne(new Query(loginSuccess), Login.class);
	}
	
}
