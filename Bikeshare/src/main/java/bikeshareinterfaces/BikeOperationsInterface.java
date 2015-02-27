package bikeshareinterfaces;

import java.util.Date;
import java.util.List;

import resources.Bike;

public interface BikeOperationsInterface {
	
	public void addBike(Bike bike);
	public Bike getBike(String bike_id);
	public List<Bike> getAllBikes();
	public void updateBikeStatusToReserved(String bike_id);
	public void updateBikeStatusToAvailable(String bike_id);
	
}