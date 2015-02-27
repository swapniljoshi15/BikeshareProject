package bikeshareimpl;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import controller.BikeShareController;
import bikeshareinterfaces.BikeOperationsInterface;
import resources.Bike;
import util.BikeShareUtil;
import util.MongodbConnection;

public class BikeOperationsImpl implements BikeOperationsInterface{
	
	private MongoTemplate mongoTemplate = MongodbConnection.getConnection();
	
	public void addBike(Bike bike){
		//bike.setBike_id(BikeShareUtil.generateRandomBikeId());
		mongoTemplate.insert(bike);
	}
	
	public Bike getBike(String bike_id){
	    return mongoTemplate.findOne(new Query((Criteria.where("bike_id").is(bike_id))), Bike.class);		
	}
	
	public List<Bike> getAllBikes(){
		return mongoTemplate.findAll(Bike.class);
	}
	
	public void updateBikeStatusToReserved(String bike_id){
		
		Update updateReserveStatus = new Update();
		updateReserveStatus.set("status", BikeShareController.globalReservationIndicator);
		mongoTemplate.updateFirst(new Query(Criteria.where("bike_id").is(bike_id)), updateReserveStatus, Bike.class);
				
	}
	
	public void updateBikeStatusToAvailable(String bike_id){
		
		Update updateAvailableStatus = new Update();
		updateAvailableStatus.set("status", BikeShareController.globalAvailableIndicator);
		mongoTemplate.updateFirst(new Query(Criteria.where("bike_id").is(bike_id)), updateAvailableStatus, Bike.class);
				
	}
	
}