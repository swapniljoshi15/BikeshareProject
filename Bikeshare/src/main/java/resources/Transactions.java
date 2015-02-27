package resources;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import util.CustomDateSerializer;

import javax.validation.constraints.NotNull;

/**
 * Created by gaurav on 12/6/14.
 */

@Document(collection = "Transactions")
public class Transactions {

    @NotNull(message = "Please enter 'transaction_Id'.")
    private int transaction_id;

    private DateTime date_of_booking;

    @NotNull(message = "Please enter 'User_Id'.")
    private int user_id;

    @NotEmpty(message = "Please enter 'Booked_Bike_Id'.")
    private String booked_bike_id;

    @NotNull(message = "Please enter 'Location'.")
    private int location_id;

    @NotEmpty(message = "Please enter 'From_Hour'.")
    private int from_hour;

    @NotEmpty(message = "Please enter 'To_Hour'.")
    private int to_hour;

    @NotEmpty
    private String booking_status;
    
    @NotEmpty
    private boolean active_booking; 
    
    @NotEmpty
    private boolean credit_used;
    
   	@NotEmpty
    private int credited_amount;
   	
   	@NotEmpty
    private boolean freeRide;
   	
	@NotEmpty
    private String username;
   
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isFreeRide() {
		return freeRide;
	}

	public void setFreeRide(boolean freeRide) {
		this.freeRide = freeRide;
	}

	public boolean isCredit_used() {
		return credit_used;
	}

	public void setCredit_used(boolean credit_used) {
		this.credit_used = credit_used;
	}

	public int getCredited_amount() {
		return credited_amount;
	}

	public void setCredited_amount(int credited_amount) {
		this.credited_amount = credited_amount;
	}    

    public boolean isActive_booking() {
		return active_booking;
	}

	public void setActive_booking(boolean active_booking) {
		this.active_booking = active_booking;
	}

	@NotEmpty(message = "Please enter 'User_Payment'.")
    private String user_payment;

    @NotEmpty
	private String bikename;
	
	@NotEmpty
	private String price;
	
	@NotEmpty
	private String bikeStatus;
	
	@NotEmpty
	private String biketype;
	
	@NotEmpty
	private String location_name;
	
	@NotEmpty
	private String city;
	
	@NotEmpty 
	private String state;
	
	@NotEmpty 
	private int zipcode;
	
    
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	//@JsonSerialize(using = CustomDateSerializer.class)
    private DateTime created_date;

    //@JsonSerialize(using = CustomDateSerializer.class)
    private DateTime updated_date;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getFrom_hour() {
        return from_hour;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void setFrom_hour(int from_hour) {
        this.from_hour = from_hour;

    }

    public int getTo_hour() {
        return to_hour;
    }

    public void setTo_hour(int to_hour) {
        this.to_hour = to_hour;
    }

    public String getUser_payment() {
        return user_payment;
    }

    public void setUser_payment(String user_payment) {
        this.user_payment = user_payment;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public DateTime getDate_of_booking() {
        return date_of_booking;
    }

    public void setDate_of_booking(DateTime date_of_booking) {
        this.date_of_booking = date_of_booking;
    }

    public String getBooked_bike_id() {
        return booked_bike_id;
    }

    public void setBooked_bike_id(String booked_bike_id) {
        this.booked_bike_id = booked_bike_id;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public DateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(DateTime created_date) {
        this.created_date = created_date;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public DateTime getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(DateTime updated_date) {
        this.updated_date = updated_date;
    }
    
    public String getBikename() {
		return bikename;
	}

	public void setBikename(String bikename) {
		this.bikename = bikename;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	
	public String getBiketype() {
		return biketype;
	}

	public void setBiketype(String biketype) {
		this.biketype = biketype;
	}
      
	public String getBikeStatus() {
		return bikeStatus;
	}

	public void setBikeStatus(String bikeStatus) {
		this.bikeStatus = bikeStatus;
	}
	
	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	
}
