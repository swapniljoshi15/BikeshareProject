package resources;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Bike")
public class Bike {
	
	
	private String bike_id;
	
	@NotEmpty(message = "Please enter 'Bike Name'.")
	private String bikename;
	
	@NotEmpty(message = "Please enter 'Bike Price'.")
	private String price;
	
	@NotEmpty(message = "Please enter 'Bike Status'.")
	private String status;
	
	@NotEmpty(message = "Please enter 'Bike Type'.")
	private String type;
	
	
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

	public String getBike_id() {
		return bike_id;
	}

	public void setBike_id(String bike_id) {
		this.bike_id = bike_id;
	}	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}