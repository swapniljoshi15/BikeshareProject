package bikeshareimpl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import resources.LocationEntity;
import resources.Transactions;
import util.MongodbConnection;
import bikeshareinterfaces.LocationEntityInterface;

public class LocationEntityOperations implements LocationEntityInterface {

	private MongoTemplate mongoTemplate = MongodbConnection.getConnection();
	
	@Override
	public LocationEntity getLocation(int location_id) {
		
		return mongoTemplate.findOne(new Query((Criteria.where("location_id").is(location_id))), LocationEntity.class, "locationEntity");
	}	
	
	@Override
	public void addLocation(LocationEntity location) {
		
		mongoTemplate.insert(location);
	}
	
	public List<LocationEntity> getLocations()
	
	{
		Query query = new Query((Criteria.where("locaiton_id").ne(-1)));
    	query.with(new Sort(Sort.Direction.ASC, "location_id"));
		
		return mongoTemplate.find(query, LocationEntity.class, "locationEntity");
	}
	

}
