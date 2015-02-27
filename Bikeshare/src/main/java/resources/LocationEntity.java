package resources;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class LocationEntity {
	
	@NotEmpty
	private int location_id;
	
	@NotEmpty
	private String location_name;
		
	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	@NotEmpty (message = "Required Field. Please provide the latitude.")
	private String latitude;
	
	@NotEmpty (message = "Required Field. Please provide the longitude.")
	private String longitude;
	
	@NotEmpty (message = "Required Field. Please provide the city.")
	private String city;
	
	@NotEmpty (message = "Required Field. Please provide the state.")
	private String state;
	
	@NotEmpty (message = "Required Field. Please provide the zipcode.")
	private int zipcode;
	
	private int location_capacity;

	public int getLocation_id() {
		return location_id;
	}

	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

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

	public int getLocation_capacity() {
		return location_capacity;
	}

	public void setLocation_capacity(int location_capacity) {
		this.location_capacity = location_capacity;
	}
	
	
	
}