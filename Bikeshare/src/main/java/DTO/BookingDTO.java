package DTO;

import org.hibernate.validator.constraints.NotEmpty;

public class BookingDTO {

	private int location_id;
	private int fromHour;
	private int toHour;
	private String bike_id;
	private int transaction_id;
	private String boooking_status;
	private boolean reserve_success;
	private boolean cancel_success;
	private String errorMessage;
	private String successMessage;
	private int user_id;
	private boolean freeRide;
	
	
	public int getLocation_id() {
		return location_id;
	}
	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}
	public int getFromHour() {
		return fromHour;
	}
	public void setFromHour(int fromHour) {
		this.fromHour = fromHour;
	}
	public int getToHour() {
		return toHour;
	}
	public void setToHour(int toHour) {
		this.toHour = toHour;
	}
	public String getBike_id() {
		return bike_id;
	}
	public void setBike_id(String bike_id) {
		this.bike_id = bike_id;
	}
	public int getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getBoooking_status() {
		return boooking_status;
	}
	public void setBoooking_status(String boooking_status) {
		this.boooking_status = boooking_status;
	}
	
	
	public boolean isReserve_success() {
		return reserve_success;
	}
	public void setReserve_success(boolean reserve_success) {
		this.reserve_success = reserve_success;
	}
	public boolean isCancel_success() {
		return cancel_success;
	}
	public void setCancel_success(boolean cancel_success) {
		this.cancel_success = cancel_success;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public boolean isFreeRide() {
		return freeRide;
	}
	public void setFreeRide(boolean freeRide) {
		this.freeRide = freeRide;
	}
	
	
}
