package bikeshareinterfaces;

import java.text.ParseException;
import java.util.*;

import DTO.BookingDTO;
import resources.Bike;
import resources.LocationInventory;

public interface LocationInventoryInterface {
	
	public String[] getInvForAnHour(int location_id, int hour ) throws ParseException;
	
	//public Map<Integer, String[]> getInvForAnHourAtVariousLoc(int[] arrayOfLocation_id, int hour );
	
	public ArrayList<String[]> getInvForSeveralHoursAtOneLocation(int location_id, int fromHour, int toHour );
			
	public String[] getInvForSeveralHoursAtOneLocationWithOnlyAvailableBikes(int location_id, int fromHour, int toHour);
	
	public BookingDTO updateInvForReservation(int location_id, int fromhour, int toHour, String bikeID);
	
	public BookingDTO updateInvForCancellation(int location_id, int fromHour, int toHour, String bikeID);
	
	public Bike[] getAvailableBikes(int location_id, int fromHour, int toHour);
	//public Bike[] getAvailableBikes(int location_id, int fromHour, int toHour);
	
	
}
