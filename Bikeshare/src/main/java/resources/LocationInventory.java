package resources;

import java.io.Serializable;
import java.lang.String;

import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import org.springframework.data.mongodb.core.mapping.Document;

//import org.springframework.data.annotation

@Document(collection = "Locations")
@JsonInclude(Include.NON_EMPTY)
public class LocationInventory implements Serializable{
	
	@NotEmpty
	int location_id;
	
	
	String[] hour_0;
	
	String[] hour_1;
	
	String[] hour_2;
	
	String[] hour_3;
	
	String[] hour_4;
	
	String[] hour_5;
	
	String[] hour_6;
	
	String[] hour_7;
	
	String[] hour_8;
	
	String[] hour_9;
	
	String[] hour_10;
	
	String[] hour_11;
	
	String[] hour_12;
	
	String[] hour_13;
	
	String[] hour_14;
	
	String[] hour_15;
	
	String[] hour_16;
	
	String[] hour_17;
	
	String[] hour_18;
	
	String[] hour_19;
	
	String[] hour_20;
	
	String[] hour_21;
	
	String[] hour_22;
	
	String[] hour_23;
	
	public int getLocation_id() { return location_id; }

	public void setLocation_id(int location_id) { this.location_id = location_id; }

		
	/* Getters and Setters for Hours */
	
	public String[] gethour_0 () {	return hour_0 ;  }
	
	public void sethour_0(String[] hour_0) { this.hour_0 = hour_0; }
	
	public String[] gethour_1 () {	return hour_1; }
	
	public void sethour_1(String[] hour_1) { this.hour_1 = hour_1; }
	
	public String[] gethour_2 (){	return hour_2; }
	
	public void sethour_2(String[] hour_2) { this.hour_2 = hour_2; }
	
	public String[] gethour_3 () {	return hour_3; }
	
	public void sethour_3(String[] hour_3) { this.hour_3 = hour_3; }
	
	public String[] gethour_4 () {	return hour_4; }
	
	public void sethour_4(String[] hour_4) { this.hour_4 = hour_4; }
	
	public String[] gethour_5 () {	return hour_5; }
	
	public void sethour_5(String[] hour_5) { this.hour_5 = hour_5; }
	
	public String[] gethour_6 () {	return hour_6; }
	
	public void sethour_6(String[] hour_6) { this.hour_6 = hour_6; }
	
	public String[] gethour_7 () {	return hour_7; }
	
	public void sethour_7(String[] hour_7) { this.hour_7 = hour_7; }
	
	public String[] gethour_8 () {	return hour_8; }
	
	public void sethour_8(String[] hour_8) { this.hour_8 = hour_8; }
	
	public String[] gethour_9 (){	return hour_9; }
	
	public void sethour_9(String[] hour_9) { this.hour_9 = hour_9; }
	
	public String[] gethour_10() { return hour_10; }
	
	public void sethour_10(String[] hour_10) { this.hour_10 = hour_10; }
	
	public String[] gethour_11() { return hour_11; }
	
	public void sethour_11(String[] hour_11) { this.hour_11 = hour_11; }
	
	public String[] gethour_12() { return hour_12; }
	
	public void sethour_12(String[] hour_12) { this.hour_12 = hour_12; }
	
	public String[] gethour_13() { return hour_13; }
	
	public void sethour_13(String[] hour_13) { this.hour_13 = hour_13; }
	
	public String[] gethour_14() { return hour_14; }
	
	public void sethour_14(String[] hour_14) { this.hour_14 = hour_14; }
	
	public String[] gethour_15() { return hour_15; }
	
	public void sethour_15(String[] hour_15) { this.hour_15 = hour_15; }
	
	public String[] gethour_16() { return hour_16; }
	
	public void sethour_16(String[] hour_16) { this.hour_16 = hour_16; }
	
	public String[] gethour_17() { return hour_17; }
	
	public void sethour_17(String[] hour_17) { this.hour_17 = hour_17; }
	
	public String[] gethour_18() { return hour_18; }
	
	public void sethour_18(String[] hour_18) { this.hour_18 = hour_18; }
	
	public String[] gethour_19() { return hour_19; }
	
	public void sethour_19(String[] hour_19) { this.hour_19 = hour_19; }
	
	public String[] gethour_20() { return hour_20; }
	
	public void sethour_20(String[] hour_20) { this.hour_20 = hour_20; }
	
	public String[] gethour_21() { return hour_21; }
	
	public void sethour_21(String[] hour_21) { this.hour_21 = hour_21; }
	
	public String[] gethour_22() { return hour_22; }
	
	public void sethour_22(String[] hour_22) { this.hour_22 = hour_22; }
	
	public String[] gethour_23() { return hour_23; }
	
	public void sethour_23(String[] hour_23) { this.hour_23 = hour_23; }
	
}
	
