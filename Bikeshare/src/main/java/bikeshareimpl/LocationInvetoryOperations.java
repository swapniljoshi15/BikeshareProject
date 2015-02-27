
package bikeshareimpl;

import java.util.*;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import controller.BikeShareController;
import resources.LocationInventory;
import util.MongodbConnection;
import bikeshareinterfaces.LocationInventoryInterface;
import bikeshareinterfaces.BikeOperationsInterface;
import resources.Bike;
import DTO.BookingDTO;


public class LocationInvetoryOperations implements LocationInventoryInterface 
{
	public static MongoTemplate mongoTemplate = MongodbConnection.getConnection();
	
/*********************************************************************************************************/
	//To get bike inventory for an hour at a particular location
	public String[] getInvForAnHour(int location_id, int hour)  

	{
		String [] returnNullIfNotFound = null;
		
		LocationInventory loc_inv = new LocationInventory();

	    Query query = new Query();
	  
	    query.addCriteria(Criteria.where("location_id").is(location_id));
	    
	    loc_inv = mongoTemplate.findOne(query, LocationInventory.class,"Location_Inventory");
	    
	    if (loc_inv == null)
	    {
	    	return returnNullIfNotFound;
	    	
	    }
	    
	    switch (hour)
    	{ 		
    	    case 0:   return loc_inv.gethour_0();  	
	    	case 1:   return loc_inv.gethour_1();  	
	    	case 2:   return loc_inv.gethour_2();  	
	    	case 3:   return loc_inv.gethour_3();  	
	    	case 4:   return loc_inv.gethour_4();  	
	    	case 5:   return loc_inv.gethour_5();  	
	    	case 6:   return loc_inv.gethour_6();  	
	    	case 7:   return loc_inv.gethour_7();  
	    	case 8:   return loc_inv.gethour_8();  	
	    	case 9:   return loc_inv.gethour_9();  	
	    	case 10:  return loc_inv.gethour_10();  
	    	case 11:  return loc_inv.gethour_11();  
	    	case 12:  return loc_inv.gethour_12(); 
	    	case 13:  return loc_inv.gethour_13();  
	    	case 14:  return loc_inv.gethour_14();  
	    	case 15:  return loc_inv.gethour_15(); 
	    	case 16:  return loc_inv.gethour_16();  
	    	case 17:  return loc_inv.gethour_17();  
	    	case 18:  return loc_inv.gethour_18(); 
	    	case 19:  return loc_inv.gethour_19();  
	    	case 20:  return loc_inv.gethour_20();  
	    	case 21:  return loc_inv.gethour_21();  
	    	case 22:  return loc_inv.gethour_22();  
	    	case 23:  return loc_inv.gethour_23();  
	    	default:  return returnNullIfNotFound;
	    	
    	}
    	
	} 
/*********************************************************************************************************/

	public ArrayList<String[]> getInvForSeveralHoursAtOneLocation(int location_id, int fromHour, int toHour )
	{
		
		ArrayList<String[]> hourWiseInventoryForLocation = new ArrayList<String[]>();
		
		for (int loop = fromHour; loop < toHour; loop++ )
		{
			int hour = loop;
			hourWiseInventoryForLocation.add(getInvForAnHour(location_id, hour));
		}
		
		return hourWiseInventoryForLocation;
	}
			
/*********************************************************************************************************/	
	//To get bike inventory at a location for several hours with array of bike ids that are present across provided hours
	public String[] getInvForSeveralHoursAtOneLocationWithOnlyAvailableBikes(int location_id, int fromHour, int toHour)
	{
		String [] tempArrayOfBikesPresentAtGivenHhours;
		String [] arrayOfBikesPresentAtGivenHhours;
		//ArrayList<String[]> bikesPresentAtAllGivenHours;
		ArrayList<String[]> hourWiseInventoryForLocation = new ArrayList<String[]>();

		String [] returnNullIfNotPresent = null;

		hourWiseInventoryForLocation = getInvForSeveralHoursAtOneLocation(location_id, fromHour, toHour);
	
		List<List<String>> lists = new ArrayList<List<String>>();
		for(int i=0; i<hourWiseInventoryForLocation.size();i++)

		{
			lists.add(new ArrayList<String>(Arrays.asList(hourWiseInventoryForLocation.get(i))));
		}
		
		List<String> bikesPresentAtAllGivenHours = new ArrayList<String>();
		bikesPresentAtAllGivenHours.addAll(lists.get(0));
		for (ListIterator<List<String>> iter = lists.listIterator(1); iter.hasNext(); ) {
			bikesPresentAtAllGivenHours.retainAll(iter.next());
		}

		int size = bikesPresentAtAllGivenHours.size();
		tempArrayOfBikesPresentAtGivenHhours = new String[size];
		int j=0;
		for (int i = 0; i<size; i++)
		{
			if(bikesPresentAtAllGivenHours.get(i).contains(BikeShareController.globalReservationIndicator) == false )
			{
				tempArrayOfBikesPresentAtGivenHhours[j] = bikesPresentAtAllGivenHours.get(i);
				j++;
			}
		}

		size = j; 
		arrayOfBikesPresentAtGivenHhours = new String[size];	
		for (int i = 0; i<size; i++)
		{
			arrayOfBikesPresentAtGivenHhours[i]= tempArrayOfBikesPresentAtGivenHhours[i];
		}


		if (arrayOfBikesPresentAtGivenHhours.length==0)
		{
			return returnNullIfNotPresent; //Returns null if no bike present at all provided hours
		}
		else
		{
			return arrayOfBikesPresentAtGivenHhours;
		}

	}
	    

	/*********************************************************************************************************/
	
	public Bike[] getAvailableBikes(int location_id, int fromHour, int toHour)
	
	{
		BikeOperationsInterface bikes = new BikeOperationsImpl();
				
		String [] fetchedArrayOfAvailableBikes;
				
		Bike[] arrayofAvailableBikes;
		
		fetchedArrayOfAvailableBikes = getInvForSeveralHoursAtOneLocationWithOnlyAvailableBikes(location_id, fromHour, toHour);
		
		if(fetchedArrayOfAvailableBikes == null)
		{
			return null;
			
		}
				
		arrayofAvailableBikes = new Bike[fetchedArrayOfAvailableBikes.length];
		
		for(int i = 0; i< fetchedArrayOfAvailableBikes.length; i++ )
		{
			arrayofAvailableBikes[i] = bikes.getBike(fetchedArrayOfAvailableBikes[i]);
		}
		
		return arrayofAvailableBikes;
	}
	
	/*********************************************************************************************************/
	   
	//To update location inventory for several hours - Same function will be used for bookings and cancellations
	// For Booking : bikeId = Reserved Bike ID     and    reservationIndicator = reservationIndicatorString like "BIKE-RESERVED" 
	// For Cancellation : bikeID = reservationIndicatorString     and    reservationIndicator = bikeID for which booking has been cancelled
	
	public BookingDTO updateInvForReservation(int location_id, int fromHour, int toHour,  String bikeID)
	
	{
		     
	    BookingDTO booking = new BookingDTO(); 
		
		LocationInventory loc_inv = null;
	
		Query query = new Query();
	    
	    query.addCriteria(Criteria.where("location_id").is(location_id));
	    loc_inv = mongoTemplate.findOne(query, LocationInventory.class,"Location_Inventory");
	    
	    if (loc_inv != null)
	    {	    
	       booking.setReserve_success(true);
	       booking.setLocation_id(location_id);
	       booking.setFromHour(fromHour);
	       booking.setToHour(toHour);
	       booking.setBike_id(bikeID);
	       booking.setCancel_success(false);
	       booking.setErrorMessage(null);
	       
	       LocationInventory updated_loc_inv = updateInventory(loc_inv, fromHour, toHour, bikeID, BikeShareController.globalReservationIndicator +" - " + bikeID ) ;//new LocationInventory();
		    
		    Update update = getUpdate(updated_loc_inv, fromHour, toHour);
		         
		    mongoTemplate.updateFirst(query, update, LocationInventory.class, "Location_Inventory");
	     
	    }
	       
	    else
	    {
	    	
	    	booking.setReserve_success(false);
		    booking.setLocation_id(location_id);
		    booking.setFromHour(fromHour);
		    booking.setToHour(toHour);
		    booking.setBike_id(bikeID);
		    booking.setCancel_success(false);
		    booking.setErrorMessage("Record not found !");
	    }
	   
	    return booking;
	    
	}
	    
/*********************************************************************************************************/
	
public BookingDTO updateInvForCancellation(int location_id, int fromHour, int toHour,  String bikeID)
	
	{
		  
	BookingDTO booking = new BookingDTO(); 
	
	LocationInventory loc_inv = null;

	Query query = new Query();
    
    query.addCriteria(Criteria.where("location_id").is(location_id));
    loc_inv = mongoTemplate.findOne(query, LocationInventory.class,"Location_Inventory");
    
    if (loc_inv != null)
    {	    
       booking.setReserve_success(false);
       booking.setLocation_id(location_id);
       booking.setFromHour(fromHour);
       booking.setToHour(toHour);
       booking.setBike_id(bikeID);
       booking.setCancel_success(true);
       booking.setErrorMessage(null);
       
       LocationInventory updated_loc_inv = updateInventory(loc_inv, fromHour, toHour, BikeShareController.globalReservationIndicator +" - " + bikeID, bikeID ) ;//new LocationInventory();
	    
	    Update update = getUpdate(updated_loc_inv, fromHour, toHour);
	         
	    mongoTemplate.updateFirst(query, update, LocationInventory.class, "Location_Inventory");
     
    }
       
    else
    {
    	
    	booking.setCancel_success(false);
    	booking.setLocation_id(location_id);
	    booking.setFromHour(fromHour);
	    booking.setToHour(toHour);
	    booking.setBike_id(bikeID);
	    booking.setReserve_success(false);
	    booking.setErrorMessage("Record not found !");
    }
   
    return booking;
		    
	}
	
/*********************************************************************************************************/	
	
public static void loadDatabase()
{

LocationInventory loc = new LocationInventory();

int location_id = 95110;


 for (int i = 0; i<5; i++)

	{
		loc.setLocation_id(location_id + i);
		
		String [] hour = null;
		
		String [] hour1 = {"900", "901", "902", "903"};
		String [] hour2 = {"904", "905", "906"};
		String [] hour3 = {"907", "908", "909"};
		String [] hour4 = {"910"};
		String [] hour5 = {""};
		
		if((location_id + i) == 95110) { hour = hour1;}
		if((location_id + i) == 95111) { hour = hour2;}
		if((location_id + i) == 95112) { hour = hour3;}
		if((location_id + i) == 95113) { hour = hour4;}
		if((location_id + i) == 95114) { hour = hour5;}
		
			loc.sethour_0(hour);
			loc.sethour_1(hour);
			loc.sethour_2(hour);
			loc.sethour_3(hour);
			loc.sethour_4(hour);
			loc.sethour_5(hour);
			loc.sethour_6(hour);
			loc.sethour_7(hour);
			loc.sethour_8(hour);
			loc.sethour_9(hour);
			loc.sethour_10(hour);
			loc.sethour_11(hour);
			loc.sethour_12(hour);
			loc.sethour_13(hour);
			loc.sethour_14(hour);
			loc.sethour_15(hour);
			loc.sethour_16(hour);
			loc.sethour_17(hour);
			loc.sethour_18(hour);
			loc.sethour_19(hour);
			loc.sethour_20(hour);
			loc.sethour_21(hour);
			loc.sethour_22(hour);
			loc.sethour_23(hour);
			
			mongoTemplate.save(loc, "Location_Inventory");	
			
			
	}
		
}


/*********************************************************************************************************/	
	//Method to select inventories to update
	public LocationInventory updateInventory(LocationInventory loc_inv, int fromHour, int toHour ,String bikeID, String reservationIndicator)
	    
	{
		String tempInv[] = null;
	    
		for (int loop = fromHour; loop < toHour; loop++ )
		
		switch (loop)
     	{ 		
     		case 0:   
     			tempInv = null;
     			tempInv = loc_inv.gethour_0();
     			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
     			loc_inv.sethour_0(tempInv);
     		break;
 	    	
     	    case 1:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_1();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_1(tempInv);
	   		break;
 	   		
     	    case 2:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_2();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_2(tempInv);
	   		break;
     		   		
     	    case 3:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_3();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_3(tempInv);
	   	    break;
	   	    
     	    case 4:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_4();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_4(tempInv);
	   	    break;
	   	    
     	    case 5:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_5();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_5(tempInv);
	   	    break;
	   	    
     	    case 6:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_6();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_6(tempInv);
	   	    break;
	   	    
     	    case 7:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_7();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_7(tempInv);
	   	    break;
	   	    
     	    case 8:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_8();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_8(tempInv);
	   	    break;
	   	    
     	    case 9:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_9();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_9(tempInv);
	   	    break;
	   	    
     	    case 10:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_10();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_10(tempInv);
	   	    break;
	   	    
     	    case 11:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_11();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_11(tempInv);
	   	    break;
	   	    
     	    case 12:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_12();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_12(tempInv);
	   	    break;
	   	    
     	    case 13:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_13();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_13(tempInv);
	   	    break;
	   	    
     	    case 14:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_14();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_14(tempInv);
	   	    break;
	   	    
     	    case 15:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_15();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_15(tempInv);
	   	    break;
	   	    
     	    case 16:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_16();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_16(tempInv);
	   	    break;
	   	    
     	    case 17:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_17();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_17(tempInv);
	   	    break;
	   	    
     	    case 18:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_18();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_18(tempInv);
	   	    break;
	   	    
     	    case 19:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_19();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_19(tempInv);
	   	    break;
	   	    
     	    case 20:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_20();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_20(tempInv);
	   	    break;
	   	    
     	    case 21:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_21();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_21(tempInv);
	   	    break;
	   	    
     	    case 22:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_22();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_22(tempInv);
	   	    break;
     	 
     	    case 23:   
	   			tempInv = null;
	   			tempInv = loc_inv.gethour_23();
	   			for(int i = 0; i<tempInv.length; i++)
     			{
     				if(tempInv[i].equalsIgnoreCase(bikeID))
     				{
     					tempInv[i] = tempInv[i].replace(bikeID, reservationIndicator);
     					break;
     				}
     			}
	   			loc_inv.sethour_23(tempInv);
	   	    break;
	   	}
	
		return loc_inv;
		
	}

/*********************************************************************************************************/
	private Update getUpdate(LocationInventory updated_loc_inv, int fromHour, int toHour)
	{
		Update update = new Update();
		
		for (int loop = fromHour; loop < toHour; loop++ )
			
			switch (loop)
	    { 		
			case 0 : update.set("hour_0", updated_loc_inv.gethour_0()); 	break;
			case 1 : update.set("hour_1", updated_loc_inv.gethour_1()); 	break;
			case 2 : update.set("hour_2", updated_loc_inv.gethour_2()); 	break;
			case 3 : update.set("hour_3", updated_loc_inv.gethour_3()); 	break;
			case 4 : update.set("hour_4", updated_loc_inv.gethour_4()); 	break;
			case 5 : update.set("hour_5", updated_loc_inv.gethour_5()); 	break;
			case 6 : update.set("hour_6", updated_loc_inv.gethour_6()); 	break;
			case 7 : update.set("hour_7", updated_loc_inv.gethour_7()); 	break;
			case 8 : update.set("hour_8", updated_loc_inv.gethour_8()); 	break;
			case 9 : update.set("hour_9", updated_loc_inv.gethour_9()); 	break;
			case 10 : update.set("hour_10", updated_loc_inv.gethour_10());  break;
			case 11 : update.set("hour_11", updated_loc_inv.gethour_11());  break;
			case 12 : update.set("hour_12", updated_loc_inv.gethour_12());  break;
			case 13 : update.set("hour_13", updated_loc_inv.gethour_13());  break;
			case 14 : update.set("hour_14", updated_loc_inv.gethour_14());  break; 
			case 15 : update.set("hour_15", updated_loc_inv.gethour_15());  break;
			case 16 : update.set("hour_16", updated_loc_inv.gethour_16());  break;
			case 17 : update.set("hour_17", updated_loc_inv.gethour_17());  break;
			case 18 : update.set("hour_18", updated_loc_inv.gethour_18());  break; 
			case 19 : update.set("hour_19", updated_loc_inv.gethour_19());  break;
			case 20 : update.set("hour_20", updated_loc_inv.gethour_20());  break;
			case 21 : update.set("hour_21", updated_loc_inv.gethour_21());  break;
			case 22 : update.set("hour_22", updated_loc_inv.gethour_22());  break;
			case 23 : update.set("hour_23", updated_loc_inv.gethour_23());  break;
		}
		return update;
		
	}
	
/*********************************************************************************************************/	
	
}
